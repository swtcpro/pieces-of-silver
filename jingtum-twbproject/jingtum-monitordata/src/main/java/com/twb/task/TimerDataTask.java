package com.twb.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.twb.entity.TimerData;
import com.twb.service.TimerDataService;


@Component
public class TimerDataTask
{
	Logger logger = LoggerFactory.getLogger(TimerDataTask.class);

	@Resource
	TimerDataService timerDataService;

	public static boolean firstRun = true;

	@Value("${subscribe_address}")
	private String subscribeAddress;

	public static List<TimerData> tobeCheckTranList = new ArrayList();
	
	@Scheduled(cron = "1 0/1 * * * ?")
	public void task()
	{

		
		logger.info("TimerDataTask.task start");
		if (firstRun)
		{
			firstRun = false;
			logger.info("TimerDataTask.task first run");
			// 没有验证过的数据，放入待验证List
			try
			{
				tobeCheckTranList = timerDataService.getTobeCheckTran();
				
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("firstRun getTobeCheckTran error..",e);
				tobeCheckTranList = new ArrayList();
			}
		}
		logger.info("tobeCheckTranList Size:"+tobeCheckTranList.size());
		
		
		String lastHash = "";
		// 获得最后一条数据的hash
		try
		{
			lastHash = timerDataService.getLastTranHash();
		}
		catch (Exception e1)
		{
			lastHash = "";
			e1.printStackTrace();
		}
		
		logger.info("TimerDataTask.task lastHash"+lastHash);
		
		try
		{
			List<TimerData> list = timerDataService.getTranFromJingtong(subscribeAddress,
					lastHash);
			tobeCheckTranList.addAll(list);

		}
		catch (Exception e)
		{
			logger.error("getTranFromJingtong" ,e);
		}
		logger.info("tobeCheckTranList Size:"+tobeCheckTranList.size());

		try
		{
			tobeCheckTranList = timerDataService.checkSocketData(tobeCheckTranList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("checkSocketData ",e);
		}
		logger.info("tobeCheckTranList Size:"+tobeCheckTranList.size());

		logger.info("TimerDataTask.task end");

	}

}
