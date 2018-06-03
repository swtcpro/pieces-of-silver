package com.twb.thread;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twb.entity.CommitchainData;
import com.twb.entity.CommitchainVerifyData;
import com.twb.service.CommitchainVerifyService;
import com.twb.utils.CommitChainVerifyDataQueue;


public class CommitchainVerifyRunnable implements Runnable
{

	private static final Logger logger = LoggerFactory.getLogger(CommitchainVerifyRunnable.class);

	private CommitchainVerifyService commitchainVerifyServiceImp;
	
	@Override
	public void run()
	{
		logger.info("线程:" + Thread.currentThread().getName() + "运行中.....");
		while (true)
		{
			try
			{
				CommitchainVerifyData cvd = CommitChainVerifyDataQueue.get();
				//如果是待上链数据，将数据准备
				commitchainVerifyServiceImp.doingTocheckCVD(cvd);
				
			}
			catch (Exception e)
			{
				logger.error("error.." + e.toString() + "," + Arrays.toString(e.getStackTrace()));
				e.printStackTrace();
			}
		}

	}

	public CommitchainVerifyRunnable(CommitchainVerifyService commitchainVerifyServiceImp)
	{
		super();
		this.commitchainVerifyServiceImp = commitchainVerifyServiceImp;
	}

	public CommitchainVerifyRunnable()
	{
		super();
	}

}
