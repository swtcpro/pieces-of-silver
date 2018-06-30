package com.twb.wechat.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.twb.wechat.service.impl.OrderPayServiceImp;


@Component
public class OrderPayTask
{
	Logger logger = LoggerFactory.getLogger(OrderPayTask.class);

	@Scheduled(cron = "1 0 0 * * ?")
	public void task()
	{

		logger.info("OrderPayTask.task start");
		OrderPayServiceImp.AMOUT_MAP.clear();
		
		logger.info("OrderPayTask.task end");
		
	}
	
}
