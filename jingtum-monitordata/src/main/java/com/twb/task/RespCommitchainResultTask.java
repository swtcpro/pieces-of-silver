package com.twb.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.twb.service.RespCommitchainResultService;

/**
 * 
 * @Title:  RespCommitchainResultTask.java   
 * @Package com.twb.task   
 * @Description:    响应上链结果 
 * @author: 田文彬     
 * @date:   2018年5月31日 上午11:50:17   
 * @version V1.0
 */
@Component
public class RespCommitchainResultTask
{
	Logger logger = LoggerFactory.getLogger(RespCommitchainResultTask.class);
	@Autowired
	RespCommitchainResultService respCommitchainResultServiceImp;
	@Scheduled(cron = "30 0/1 * * * ?")
	public void commitChainFailCheckTask()
	{
		logger.info("RespCommitchainResultTask start");
		try
		{
			respCommitchainResultServiceImp.respCommitchainResult();
		}
		catch (Exception e)
		{
			logger.error("反馈异常",e);
			e.printStackTrace();
		}
		

		logger.info("RespCommitchainResultTask end");

	}
	

}
