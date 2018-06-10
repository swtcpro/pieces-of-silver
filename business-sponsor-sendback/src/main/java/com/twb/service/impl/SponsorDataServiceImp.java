package com.twb.service.impl;

import java.math.BigDecimal;
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

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.twb.commondata.data.CommitchainMqData;
import com.twb.commondata.data.DistributeMqData;
import com.twb.entity.SponsorData;
import com.twb.repository.SponsorDataRepository;
import com.twb.service.SponsorDataService;

@Service
public class SponsorDataServiceImp implements SponsorDataService
{

	private static final Logger logger = LoggerFactory.getLogger(SponsorDataServiceImp.class);

	@Autowired
	private SponsorDataRepository sponsorDataRepository;
	
	@Autowired
	private MqProductServiceImp mqProductServiceImp;
	
	
	@Value("${min_amount}")
	private String min_amount;
	

	@Transactional(rollbackFor = Exception.class)
	public void handlerTodoData() throws Exception
	{
		List<SponsorData> list = sponsorDataRepository.getTodoSponsorData();
		if(list==null||list.isEmpty())
		{
			return;
		}
		logger.info("handlerTodoData,list "+list.size());
		for(SponsorData sd :list)
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



	public void doingBusiness(SponsorData sd) throws Exception
	{
		logger.info("doingBusiness,"+sd.getId());
		
		BigDecimal minBD = new BigDecimal(min_amount);
		BigDecimal sdBD = new BigDecimal(sd.getAmountvalue());
		if(sdBD.compareTo(minBD)<=0)
		{
			logger.info("赞赏金额小于最小金额，"+sdBD.doubleValue());
			sd.setHandleFlag(SponsorData.HANDLE_FLAG_SUCCESS);
			sd.setHandleMsg("赞赏金额小于最小金额");
			sponsorDataRepository.save(sd);
		}
		
		CommitchainMqData cmd = new CommitchainMqData();
		cmd.setAmountcurrency(sd.getAmountcurrency());
		cmd.setAmountissuer(sd.getAmountissuer());
		cmd.setAmountvalue(minBD.doubleValue());
		cmd.setBusinessid(sd.getClass().getSimpleName()+sd.getId());
		cmd.setBusinessTag("");//无需反馈
		cmd.setBusinessTopic("");//无需反馈
		cmd.setCounterparty(sd.getCounterparty());
		Map memos = new HashMap();
		memos.put("prehash", sd.getHash());
		memos.put("msg", "谢谢您的赞赏，谢谢！");
		cmd.setMemos(memos);
		SendResult sr = mqProductServiceImp.sendCommitChainMQ(cmd);
		if(sr!=null)
		{
			logger.info("MQ发送成功,"+sr.getMessageId());
			sd.setHandleFlag(SponsorData.HANDLE_FLAG_SUCCESS);
			sd.setHandleMsg("MQ发送成功");
			sd.setCommitchainMessageId(sr.getMessageId());
		}
		else
		{
			logger.info("MQ发送失败");
			sd.setHandleFlag(SponsorData.HANDLE_FLAG_FAIL);
			sd.setHandleMsg("MQ发送失败");
		}
		sponsorDataRepository.save(sd);
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public SponsorData saveData(DistributeMqData dma,Message message) throws Exception
	{
		SponsorData sd = new SponsorData();
		sd.setAmountcurrency(dma.getAmountcurrency());
		sd.setAmountissuer(dma.getAmountissuer());
		sd.setAmountvalue(dma.getAmountvalue());
		sd.setCounterparty(dma.getCounterparty());
		sd.setDate(dma.getDate());
		sd.setFee(dma.getFee());
		sd.setHandleFlag(SponsorData.HANDLE_FLAG_TODO);
		sd.setHash(dma.getHash());
		sd.setMemos(dma.getMemos());
		sd.setResult(dma.getResult());
		sd.setType(dma.getType());
		sd.setBussinessid(dma.getId()+"");

		sd.setMessageDate(new Date());
		sd.setMessageId(message.getMsgID());
		sd.setMessageTag(message.getTag());
		sd.setMessageTopic(message.getTopic());
		
		sponsorDataRepository.save(sd);
		return sd;
	}
	



}
