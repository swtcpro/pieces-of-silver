package com.twb.thread;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twb.entity.CommitchainData;
import com.twb.service.CommitchainDataService;
import com.twb.utils.CommitchainDataQueue;


public class CommitchainRunnable implements Runnable
{

	private static final Logger logger = LoggerFactory.getLogger(CommitchainRunnable.class);

	private CommitchainDataService commitchainDataServiceImp;
	
	@Override
	public void run()
	{
		logger.info("线程:" + Thread.currentThread().getName() + "运行中.....");
		while (true)
		{
			try
			{
				CommitchainData cd = CommitchainDataQueue.get();
				//如果是待上链数据，将数据准备
				if(cd!=null&&CommitchainData.COMMITCHAIN_FLAG_TODO.equals(cd.getCommitchainFlag()))
				{
					cd = commitchainDataServiceImp.handlerTodoCommitchainData(cd);
				}
				//如果是数据准备完成，开始上链数据，提交上链
				if (cd!=null&&CommitchainData.COMMITCHAIN_FLAG_DOING.equals(cd.getCommitchainFlag()))
				{
					commitchainDataServiceImp.doingCommitchainData(cd);
				}
				
			}
			catch (Exception e)
			{
				logger.error("error.." + e.toString() + "," + Arrays.toString(e.getStackTrace()));
				e.printStackTrace();
			}
		}

	}

	public CommitchainRunnable(CommitchainDataService commitchainDataServiceImp)
	{
		super();
		this.commitchainDataServiceImp = commitchainDataServiceImp;
	}

	public CommitchainRunnable()
	{
		super();
	}


	
	

}
