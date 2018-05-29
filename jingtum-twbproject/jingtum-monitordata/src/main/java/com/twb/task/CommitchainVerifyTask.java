package com.twb.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twb.entity.CommitchainVerifyData;
import com.twb.service.CommitchainVerifyService;
import com.twb.thread.CommitchainVerifyRunnable;
import com.twb.utils.CommitChainVerifyDataQueue;

@Component
public class CommitchainVerifyTask
{
	Logger logger = LoggerFactory.getLogger(CommitchainVerifyTask.class);

	private int threadNum = 3;
	
	@Autowired
	CommitchainVerifyService commitchainVerifyServiceImp;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
		try
		{
			List<CommitchainVerifyData> list1 = commitchainVerifyServiceImp.getTocheckCVD();
			for (CommitchainVerifyData cvd : list1)
			{
				CommitChainVerifyDataQueue.add(cvd);
			}
		}
		catch (Exception e)
		{
			logger.error("数据库获取待校验上链数据失败", e);
			e.printStackTrace();
		}

		// 添加处理线程
		ExecutorService executorService = Executors.newFixedThreadPool(threadNum, new ThreadFactory()
		{
			public Thread newThread(Runnable r)
			{
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});

		for (int i = 0; i < threadNum; ++i)
		{
			CommitchainVerifyRunnable runnable = new CommitchainVerifyRunnable(commitchainVerifyServiceImp);
			executorService.execute(runnable);
		}

	}

}
