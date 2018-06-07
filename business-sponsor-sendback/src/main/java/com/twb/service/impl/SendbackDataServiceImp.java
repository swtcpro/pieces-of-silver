package com.twb.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.commondata.data.CommitchainMqData;
import org.commondata.data.DistributeMqData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.twb.entity.SendbackData;
import com.twb.repository.SendbackDataRepository;
import com.twb.service.SendbackDataService;

@Service
public class SendbackDataServiceImp implements SendbackDataService
{

	private static final Logger logger = LoggerFactory.getLogger(SendbackDataServiceImp.class);

	@Autowired
	private SendbackDataRepository sendbackDataRepository;

	@Autowired
	private MqProductServiceImp mqProductServiceImp;
	
	@Value("${COMMITCHAIN_TOPIC}")
	private String topic;

	@Value("${COMMITCHAIN_TAG}")
	private String tag;
	
	@Value("${min_amount}")
	private String min_amount;

	@Transactional(rollbackFor = Exception.class)
	public void handlerTodoData() throws Exception
	{
		List<SendbackData> list = sendbackDataRepository.getTodoSendbackData();
		if(list==null||list.isEmpty())
		{
			return;
		}
		logger.info("handlerTodoData,list "+list.size());
		for(SendbackData sd :list)
		{
			try
			{
				doingBusiness(sd);
			}
			catch (Exception e)
			{
				logger.error("处理错误",e);
				e.printStackTrace();
			}
		}
	
	}

	public void doingBusiness(SendbackData sd) throws Exception
	{
		logger.info("doingBusiness,"+sd.getId());
		
//		BigDecimal minBD = new BigDecimal(min_amount);
//		BigDecimal sdBD = new BigDecimal(sd.getAmountvalue());
//		if(sdBD.compareTo(minBD)<=0)
//		{
//			logger.info("回退金额小于最小金额，"+sdBD.doubleValue());
//			sd.setHandleFlag(SponsorData.HANDLE_FLAG_SUCCESS);
//			sd.setHandleMsg("回退金额小于最小金额");
//			sendbackDataRepository.save(sd);
//		}
		
		CommitchainMqData cmd = new CommitchainMqData();
		cmd.setAmountcurrency(sd.getAmountcurrency());
		cmd.setAmountissuer(sd.getAmountissuer());
		cmd.setAmountvalue(new BigDecimal(sd.getAmountvalue()).doubleValue());
		cmd.setBusinessid(sd.getClass().getSimpleName()+sd.getId());
		cmd.setBusinessTag("");//无需反馈
		cmd.setBusinessTopic("");//无需反馈
		cmd.setCounterparty(sd.getCounterparty());
		Map memos = new HashMap();
		memos.put("prehash", sd.getHash());
		memos.put("msg", "备注错误，请核对后再试，谢谢！");
		cmd.setMemos(memos);
		SendResult sr = mqProductServiceImp.sendMQ(topic, tag, cmd);
		if(sr!=null)
		{
			logger.info("MQ发送成功,"+sr.getMessageId());
			sd.setHandleFlag(SendbackData.HANDLE_FLAG_SUCCESS);
			sd.setHandleMsg("MQ发送成功");
			sd.setCommitchainMessageId(sr.getMessageId());
		}
		else
		{
			logger.info("MQ发送失败");
			sd.setHandleFlag(SendbackData.HANDLE_FLAG_FAIL);
			sd.setHandleMsg("MQ发送失败");
		}
		sendbackDataRepository.save(sd);
		
	}

	
	@Override
	public SendbackData saveData(DistributeMqData dma, Message message) throws Exception
	{
		SendbackData sd = new SendbackData();
		sd.setAmountcurrency(dma.getAmountcurrency());
		sd.setAmountissuer(dma.getAmountissuer());
		sd.setAmountvalue(dma.getAmountvalue());
		sd.setCounterparty(dma.getCounterparty());
		sd.setDate(dma.getDate());
		sd.setFee(dma.getFee());
		sd.setHandleFlag(SendbackData.HANDLE_FLAG_TODO);
		sd.setHash(dma.getHash());
		sd.setMemos(dma.getMemos());
		sd.setResult(dma.getResult());
		sd.setType(dma.getType());
		sd.setBussinessid(dma.getId()+"");

		sd.setMessageDate(new Date());
		sd.setMessageId(message.getMsgID());
		sd.setMessageTag(message.getTag());
		sd.setMessageTopic(message.getTopic());
		
		sendbackDataRepository.save(sd);
		return sd;
	}

	
}
