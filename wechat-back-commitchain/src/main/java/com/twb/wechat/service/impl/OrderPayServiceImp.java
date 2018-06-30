package com.twb.wechat.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.SendResult;
import com.twb.commondata.data.CommitChainRespMqData;
import com.twb.commondata.data.CommitchainMqData;
import com.twb.commondata.utils.CommonConstants;
import com.twb.wechat.entity.OrderPay;
import com.twb.wechat.repository.OrderPayRepository;
import com.twb.wechat.service.MqProductService;
import com.twb.wechat.service.OrderPayService;

@Service
public class OrderPayServiceImp implements OrderPayService
{

	private static final Logger logger = LoggerFactory.getLogger(OrderPayServiceImp.class);

	@Autowired
	OrderPayRepository orderPayRepository;

	@Autowired
	private MqProductService mqProductServiceImp;


	@Value("${WECHAT_TAG}")
	private String businessTag;

	@Override
	public OrderPay hanlderOrderPay(Map xmlMap) throws Exception
	{
		String out_trade_no = (String) xmlMap.get("out_trade_no");
		String total_feeStr = (String) xmlMap.get("total_fee");
		String time_end = (String) xmlMap.get("time_end");
		OrderPay op = orderPayRepository.getOrderPayByTradeno(out_trade_no);
		if(op==null)
		{
			logger.error("数据库未找到此条记录:"+out_trade_no);
			return null;
		}
		BigDecimal bdop = new BigDecimal(op.getTotalFee());
		
		BigDecimal bdxml = new BigDecimal(total_feeStr);
		if (bdop.compareTo(bdxml) != 0)
		{
			logger.error("数据库记录金额，与回调金额不一致。");
			return null;
		}

		if (OrderPay.STATE_PREPAY.equals(op.getState()))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
			op.setState(OrderPay.STATE_PAY);
			op.setPayTime(sdf.parse(time_end));

			orderPayRepository.save(op);
			return op;
		}
		else
		{
			return null;
		}

	}

	@Override
	public void sendMq(OrderPay op) throws Exception
	{
		String amountStr = op.getTotalFee();
		BigDecimal amountBD = new BigDecimal(amountStr);
		BigDecimal amount100 = new BigDecimal("100");

		CommitchainMqData cmd = new CommitchainMqData();
		cmd.setAmountcurrency("CNY");
		cmd.setAmountissuer("jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or");
		cmd.setAmountvalue(amountBD.divide(amount100).doubleValue());
		cmd.setBusinessid(this.getClass().getSimpleName() + op.getId());
		cmd.setBusinessTag(businessTag);
		cmd.setBusinessTopic(CommonConstants.COMMITCHAIN_TOPIC);
		cmd.setCounterparty(op.getJingtumaddress());
		Map memos = new HashMap();
		memos.put("order", op.getOutTradeNo());
		cmd.setMemos(memos);

		SendResult sr = mqProductServiceImp.sendCommitChainMQ(cmd);

		if (sr != null)
		{
			logger.info("MQ发送成功," + sr.getMessageId());
			op.setState(OrderPay.STATE_PAY_COMMITCHAIN);
			op.setMessageId(sr.getMessageId());
			op.setMessageDate(new Date());
			op.setCommitchainMsg("MQ发送成功");
			orderPayRepository.save(op);
		}
		else
		{
			logger.info("MQ发送失败");
			op.setCommitchainMsg("MQ发送失败");
			op.setState(OrderPay.STATE_PAY_COMMITCHAIN);
			op.setMessageDate(new Date());
			orderPayRepository.save(op);
		}

	}

	@Override
	public void doingCcrmd(CommitChainRespMqData ccrmd)
	{

		String businessid = ccrmd.getBusinessid();

		String idStr = businessid.replace(OrderPayServiceImp.class.getSimpleName(), "");
		int id = Integer.parseInt(idStr);
		OrderPay orderPay = orderPayRepository.findOne(id);
		if (OrderPay.STATE_PAY_COMMITCHAIN.equals(orderPay.getState()))
		{
			String checkFlag = ccrmd.getCheckFlag();
			Date date = ccrmd.getCheckchainDate();
			if (CommitChainRespMqData.CHECK_FLAG_SUCCESS.equals(checkFlag))
			{
				String hash = ccrmd.getCommitchainHash();
				orderPay.setState(OrderPay.STATE_PAY_COMMITCHAIN_SUCCESS);
				orderPay.setCommitchainHash(hash);
				orderPay.setMessageCompleteDate(date);
				orderPayRepository.save(orderPay);
			}
			else
			{
				orderPay.setState(OrderPay.STATE_PAY_COMMITCHAIN_FAIL);
				orderPay.setMessageCompleteDate(date);
				orderPayRepository.save(orderPay);
			}

		}

	}

}
