package com.twb.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.twb.commondata.data.CommitchainMqData;
import com.twb.commondata.data.DistributeMqData;
import com.twb.entity.Withdrawal;
import com.twb.entity.WithdrawalCommitchain;
import com.twb.entity.WithdrawalDeallog;
import com.twb.repository.WithdrawalCommitchainRepository;
import com.twb.repository.WithdrawalDeallogRepository;
import com.twb.repository.WithdrawalRepository;
import com.twb.service.MqProductService;
import com.twb.service.WithdrawalService;
import com.twb.utils.TransferUtil;

@Service
public class WithdrawalServiceImp implements WithdrawalService
{

	private static final Logger logger = LoggerFactory.getLogger(WithdrawalServiceImp.class);

	@Autowired
	private WithdrawalRepository withdrawalRepository;

	@Autowired
	private WithdrawalCommitchainRepository withdrawalCommitchainRepository;

	@Autowired
	private WithdrawalDeallogRepository withdrawalDeallogRepository;

	@Autowired
	private MqProductService mqProductServiceImp;

	@Value("${cny_min}")
	private String cny_min;

	@Value("${cny_max}")
	private String cny_max;

	@Value("${cny_issuer}")
	private String cny_issuer;

	@Value("${wxorder_pre}")
	private String wxorder_pre;

	@Value("${withdrawal_key}")
	private String withdrawal_key;
	


	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 加上时间

	public List<Withdrawal> getTodoWithdrawal() throws Exception
	{
		List<Withdrawal> list = withdrawalRepository.getAllWithdrawalByState(Withdrawal.STATE_WITHDRAWAL_TODO);
		if (list == null)
		{
			list = new ArrayList();
		}
		return list;
	}

	private String checkWithdrawal(Withdrawal withdrawal)
	{
		String currency = withdrawal.getAmountcurrency();
		String issuer = withdrawal.getAmountissuer();
		String value = withdrawal.getAmountvalue();

		String memos = withdrawal.getMemos();

		BigDecimal bd = new BigDecimal(value);
		BigDecimal bdMin = new BigDecimal(cny_min);
		BigDecimal bdMax = new BigDecimal(cny_max);

		StringBuffer errSb = new StringBuffer();
		if ("CNY".equals(currency) && cny_issuer.equals(issuer))
		{

			if (bd.compareTo(bdMin) < 0)
			{
				errSb.append("转入CNY小于最小提取金额：" + cny_min + "元");
			}

			if (bd.compareTo(bdMax) > 0)
			{
				errSb.append("转入CNY大于最大提取金额：" + cny_max + "元");
			}

			if (StringUtils.isEmpty(memos) || memos.length() > 32)
			{
				errSb.append("转账时候请输入正确的备注");
			}

		}
		else
		{
			errSb.append("转入不是CNY,暂不支持非CNY提取");
		}

		return errSb.toString();

	}

	private WithdrawalCommitchain saveWithdrawBack(Withdrawal withdrawal, String reason, String state)
	{
		WithdrawalCommitchain wb = new WithdrawalCommitchain();
		wb.setAmountcurrency(withdrawal.getAmountcurrency());
		wb.setAmountissuer(withdrawal.getAmountissuer());
		wb.setAmountvalue(withdrawal.getAmountvalue());
		wb.setCounterparty(withdrawal.getCounterparty());
		wb.setDate(new Date());
		wb.setHash(withdrawal.getHash());
		wb.setWithdrawalId(withdrawal.getId());
		wb.setReason(reason);
		wb.setCommitchainState(WithdrawalCommitchain.COMMITCHAIN_STATE_TODO);
		withdrawalCommitchainRepository.save(wb);
		withdrawal.setState(state);
		withdrawalRepository.save(withdrawal);
		return wb;
	}

	@Transactional(rollbackFor = Exception.class)
	public WithdrawalCommitchain doingWithdrawal(Withdrawal withdrawal)
	{
		String msg = checkWithdrawal(withdrawal);
		if (!StringUtils.isEmpty(msg))
		{
			logger.info("验证失败," + msg + ",hash:" + withdrawal.getHash() + ",放入回退表");
			return saveWithdrawBack(withdrawal, msg, Withdrawal.STATE_WITHDRAWAL_BACK);
		}

		String openid = getOpenId(withdrawal);
		String amount = withdrawal.getAmountvalue();

		String partner_trade_no = wxorder_pre + withdrawal.getId();

		Map map = new HashMap();
		try
		{
			logger.info("开始转账1：订单" + partner_trade_no + ",openid：" + openid + "转账金额:" + amount);
			map = TransferUtil.transfer(partner_trade_no, openid, amount, "红包" + withdrawal.getHash());
			logger.info("转账结果1：" + map);
			String return_code = (String) map.get("return_code");

			if ("SUCCESS".equals(return_code))
			{
				String result_code = (String) map.get("result_code");
				if ("SUCCESS".equals(result_code))
				{
					return successSave(withdrawal, map);
				}
				else
				{
					logger.info("开始转账2：订单" + partner_trade_no + ",openid：" + openid + "转账金额:" + amount);
					// 注意：当状态为FAIL时，存在业务结果未明确的情况，所以如果状态FAIL，请务必再请求一次查询接口[请务必关注错误代码（err_code字段），通过查询查询接口确认此次付款的结果。]，以确认此次付款的结果。
					map = TransferUtil.transfer(partner_trade_no, openid, amount, "红包" + withdrawal.getHash());
					logger.info("转账结果2：" + map);
					String return_code2 = (String) map.get("return_code");
					if ("SUCCESS".equals(return_code2))
					{
						String result_code2 = (String) map.get("result_code");
						if ("SUCCESS".equals(result_code2))
						{
							return successSave(withdrawal, map);
						}
						else
						{
							return failSave(withdrawal, map);
						}
					}
					else
					{
						return saveWithdrawBack(withdrawal, "转账通讯失败", Withdrawal.STATE_WITHDRAWAL_FAIL_BACK);
					}
				}
			}
			else
			{
				return saveWithdrawBack(withdrawal, "转账通讯失败", Withdrawal.STATE_WITHDRAWAL_FAIL_BACK);
			}

		}
		catch (Exception e)
		{

			logger.error(withdrawal.getId() + "," + withdrawal.getHash() + ",error..", e);
			return saveWithdrawBack(withdrawal, "转账异常：" + e.toString(), Withdrawal.STATE_WITHDRAWAL_EXCEPTION);
		}

	}

	private WithdrawalCommitchain failSave(Withdrawal withdrawal, Map map)
	{
		String openid = getOpenId(withdrawal);
		String backAmount = withdrawal.getAmountvalue();
		String partner_trade_no = wxorder_pre + withdrawal.getId();

		String err_code_des = (String) map.get("err_code_des");
		String return_msg = (String) map.get("return_msg");
		String msg = "";
		if (!StringUtils.isEmpty(err_code_des))
		{
			msg = err_code_des;
		}
		else
		{
			msg = return_msg;
		}
		if (!StringUtils.isEmpty(msg) && msg.contains("openid"))
		{
			msg = "转账时请输入正确的备注";
		}

		WithdrawalDeallog wd = new WithdrawalDeallog();
		wd.setAmount(backAmount);
		wd.setOrderId(partner_trade_no);
		wd.setPayeeAccount(openid);
		wd.setReturnCode(WithdrawalDeallog.RETURNCODE_FAIL);
		wd.setReturnMsg(msg);
		wd.setTime(new Date());
		wd.setWithdrawalId(withdrawal.getId());
		withdrawalDeallogRepository.save(wd);

		WithdrawalCommitchain wb = new WithdrawalCommitchain();
		wb.setAmountcurrency(withdrawal.getAmountcurrency());
		wb.setAmountissuer(withdrawal.getAmountissuer());
		wb.setAmountvalue(withdrawal.getAmountvalue());
		wb.setCounterparty(withdrawal.getCounterparty());
		wb.setDate(new Date());
		wb.setHash(withdrawal.getHash());
		wb.setWithdrawalId(withdrawal.getId());
		wb.setReason(msg);
		wb.setCommitchainState(WithdrawalCommitchain.COMMITCHAIN_STATE_TODO);
		withdrawalCommitchainRepository.save(wb);

		withdrawal.setState(Withdrawal.STATE_WITHDRAWAL_FAIL_BACK);
		withdrawalRepository.save(withdrawal);
		return wb;

	}

	private String getOpenId(Withdrawal withdrawal)
	{
		return withdrawal.getMemos().trim().replaceFirst(withdrawal_key, "");
	}

	private WithdrawalCommitchain successSave(Withdrawal withdrawal, Map map)
	{
		String openid = getOpenId(withdrawal);
		String backAmount = withdrawal.getAmountvalue();
		String partner_trade_no = wxorder_pre + withdrawal.getId();
		String payment_no = (String) map.get("payment_no");
		String payment_time = (String) map.get("payment_time");
		Date date = new Date();
		if (!StringUtils.isEmpty(payment_time))
		{
			// 必须捕获异常
			try
			{
				date = SDF.parse(payment_time);
			}
			catch (Exception px)
			{
				px.printStackTrace();
				date = new Date();
			}
		}

		WithdrawalDeallog wd = new WithdrawalDeallog();
		wd.setAmount(backAmount);
		wd.setOrderId(partner_trade_no);
		wd.setOrderWxid(payment_no);
		wd.setPayDate(date);
		wd.setPayeeAccount(openid);
		wd.setReturnCode(WithdrawalDeallog.RETURNCODE_SUCCESS);
		wd.setReturnMsg((String) map.get("return_msg"));
		wd.setTime(new Date());
		wd.setWithdrawalId(withdrawal.getId());
		withdrawalDeallogRepository.save(wd);
		withdrawal.setState(Withdrawal.STATE_WITHDRAWAL_SUCCESS);
		withdrawalRepository.save(withdrawal);

		WithdrawalCommitchain wb = new WithdrawalCommitchain();
		wb.setAmountcurrency("SWT");
		wb.setAmountissuer("");
		wb.setAmountvalue("0.000010");
		wb.setCounterparty(withdrawal.getCounterparty());
		wb.setDate(new Date());
		wb.setHash(withdrawal.getHash());
		wb.setWithdrawalId(withdrawal.getId());
		wb.setReason("于" + payment_time + "提取成功，微信支付金额：" + backAmount + "，微信订单号：" + payment_no);
		wb.setCommitchainState(WithdrawalCommitchain.COMMITCHAIN_STATE_TODO);
		withdrawalCommitchainRepository.save(wb);
		return wb;
	}

	@Transactional(rollbackFor = Exception.class)
	public Withdrawal saveWithdrawal(DistributeMqData dmd, Message message) throws Exception
	{
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setAmountcurrency(dmd.getAmountcurrency());
		withdrawal.setAmountissuer(dmd.getAmountissuer());
		withdrawal.setAmountvalue(dmd.getAmountvalue());
		withdrawal.setBussinessid(dmd.getId());
		withdrawal.setCounterparty(dmd.getCounterparty());
		withdrawal.setDate(dmd.getDate());
		withdrawal.setFee(dmd.getFee());
		withdrawal.setHash(dmd.getHash());
		withdrawal.setMemos(dmd.getMemos());
		withdrawal.setMessageDate(new Date());
		withdrawal.setMessageId(message.getMsgID());
		withdrawal.setMessageTopic(message.getTopic());
		withdrawal.setMessageTag(message.getTag());
		withdrawal.setState(Withdrawal.STATE_WITHDRAWAL_TODO);
		withdrawalRepository.save(withdrawal);
		return withdrawal;
	}

	@Transactional(rollbackFor = Exception.class)
	public void withdrawalCommitChain(WithdrawalCommitchain wc) throws Exception
	{
		CommitchainMqData cmd = new CommitchainMqData();
		cmd.setAmountcurrency(wc.getAmountcurrency());
		cmd.setAmountissuer(wc.getAmountissuer());
		cmd.setAmountvalue(new BigDecimal(wc.getAmountvalue()).doubleValue());
		cmd.setBusinessid(wc.getClass().getName() + wc.getId());
		cmd.setBusinessTag("");// 无需反馈
		cmd.setBusinessTopic("");// 无需反馈
		cmd.setCounterparty(wc.getCounterparty());
		Map memos = new HashMap();
		memos.put("prehash", wc.getHash());
		memos.put("msg", wc.getReason());
		cmd.setMemos(memos);
		SendResult sr = mqProductServiceImp.sendCommitChainMQ(cmd);
		if(sr!=null)
		{
			wc.setCommitchainData(new Date());
			wc.setCommitchainMessageId(sr.getMessageId());
			wc.setCommitchainState(WithdrawalCommitchain.COMMITCHAIN_STATE_SUCCESS);
		}
		else
		{
			wc.setCommitchainState(WithdrawalCommitchain.COMMITCHAIN_STATE_FAIL);
			wc.setCommitchainData(new Date());
		}
		withdrawalCommitchainRepository.save(wc);
	}

	@Override
	public List<WithdrawalCommitchain> getTodoWithdrawalCommitchain() throws Exception
	{
		List<WithdrawalCommitchain> list = withdrawalCommitchainRepository.getWithdrawalCommitchainByState(WithdrawalCommitchain.COMMITCHAIN_STATE_TODO);
		if (list == null)
		{
			list = new ArrayList();
		}
		return list;
	}

}
