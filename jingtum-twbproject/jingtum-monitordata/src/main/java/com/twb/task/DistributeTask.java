package com.twb.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twb.service.DistributeService;
import com.twb.thread.DistributeRunnable;

@Component
public class DistributeTask
{
	final static Logger logger = LoggerFactory.getLogger(DistributeTask.class);


	private int threadNum = 5;
	
	@Autowired
	DistributeService distributeServiceImp;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
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
		
		
		  for (int i = 0; i < threadNum; ++i) {
			  DistributeRunnable runnable = new DistributeRunnable();
			  runnable.setDistributeServiceImp(distributeServiceImp);
              executorService.execute(runnable);
          }
		
		
		
	}
}
