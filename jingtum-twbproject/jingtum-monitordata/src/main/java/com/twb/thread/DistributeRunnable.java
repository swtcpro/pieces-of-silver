package com.twb.thread;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twb.entity.SocketData;
import com.twb.entity.TimerData;
import com.twb.service.DistributeService;
import com.twb.utils.DistributeQueue;

public class DistributeRunnable implements Runnable
{

	private static final Logger logger = LoggerFactory.getLogger(DistributeRunnable.class);

	private DistributeService distributeServiceImp;

	@Override
	public void run()
	{
		logger.info("线程:" + Thread.currentThread().getName() + "运行中.....");
		while (true)
		{
			try
			{
				Object obj = DistributeQueue.get();

				if(obj instanceof SocketData)
				{
					distributeServiceImp.handlerSocketData((SocketData) obj);
				}
				else if(obj instanceof TimerData)
				{
					distributeServiceImp.handlerTimerData((TimerData) obj);
				}
				else
				{
					logger.error("类型错误,"+obj.toString()+","+obj.getClass());
				}
//				withdrawalServiceImp.doingWithdrawal(withdrawal);

			}
			catch (Exception e)
			{
				logger.error("error.." ,e);
				e.printStackTrace();
			}
		}

	}

	public DistributeService getDistributeServiceImp()
	{
		return distributeServiceImp;
	}

	public void setDistributeServiceImp(DistributeService distributeServiceImp)
	{
		this.distributeServiceImp = distributeServiceImp;
	}

	
	

}
