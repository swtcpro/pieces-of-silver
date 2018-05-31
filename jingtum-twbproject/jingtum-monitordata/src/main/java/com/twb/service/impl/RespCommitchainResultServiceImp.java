package com.twb.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.ons.api.SendResult;
import com.jingtongsdk.utils.JingtongRequstConstants;
import com.twb.data.CommitChainRespMqData;
import com.twb.entity.CommitchainData;
import com.twb.repository.CommitchainDataRepository;
import com.twb.service.MqProductService;
import com.twb.service.RespCommitchainResultService;

@Service
public class RespCommitchainResultServiceImp implements RespCommitchainResultService
{
	private static final Logger logger = LoggerFactory.getLogger(RespCommitchainResultServiceImp.class);

	@Autowired
	CommitchainDataRepository commitchainDataRepository;
	
	@Autowired
	private MqProductService mqProductServiceImp;
	

	@Transactional(rollbackFor = Exception.class)
	public void respCommitchainResult() throws Exception
	{
		List<CommitchainData> list= commitchainDataRepository.getResponseCommitchainData();
		if(list==null)
		{
			return;
		}
		logger.info("respCommitchainResult,"+list.size());
		for(CommitchainData cd :list)
		{
			logger.info("处理反馈,"+cd.getId());
			try
			{
				String topic = cd.getBusinessTopic();
				String tag = cd.getBusinessTag();
				CommitChainRespMqData ccrmd = new CommitChainRespMqData();
				ccrmd.setAmountcurrency(cd.getAmountcurrency());
				ccrmd.setAmountissuer(cd.getAmountissuer());
				ccrmd.setAmountvalue(cd.getAmountvalue());
				ccrmd.setBusinessid(cd.getBusinessId());
				ccrmd.setCounterparty(cd.getCounterparty());
				ccrmd.setId(cd.getId());
				ccrmd.setCommitchainHash(cd.getCommitchainHash());
				ccrmd.setCommitchainMsg(cd.getCommitchainMsg());
				ccrmd.setCheckchainDate(cd.getCheckDate());
				ccrmd.setCheckFlag(cd.getCheckFlag());
				ccrmd.setMemos(cd.getMemos());
				SendResult sr = mqProductServiceImp.sendMQ(topic, tag, JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(ccrmd));
				if(sr!=null)
				{
					logger.info("反馈成功");
					cd.setBusinessFlag(CommitchainData.BUSINESS_FLAG_SUCCESS);
					cd.setBusinessMqdate(new Date());
					cd.setBusiness_mqid(sr.getMessageId());
				}
				else
				{
					logger.info("反馈失败");
					cd.setBusinessFlag(CommitchainData.BUSINESS_FLAG_FAIL);
				}
				commitchainDataRepository.save(cd);
			}
			catch (Exception e)
			{
				
				try
				{
					logger.error("MQ 反馈数据异常,"+cd.getId(),e);
					cd.setBusinessFlag(CommitchainData.BUSINESS_FLAG_FAIL);
					commitchainDataRepository.save(cd);
				}
				catch (Exception e1)
				{
					logger.error("数据更新异常",e1);
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
	
	
}
