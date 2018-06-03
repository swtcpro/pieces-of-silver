package com.twb.task;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.twb.entity.CommitchainData;
import com.twb.service.CommitchainDataService;
import com.twb.thread.CommitchainListener;
import com.twb.thread.CommitchainRunnable;
import com.twb.utils.CommitchainDataQueue;

@Component
public class CommitchainTask
{
	Logger logger = LoggerFactory.getLogger(CommitchainTask.class);

	@Autowired
	CommitchainDataService commitchainDataServiceImp;

	@Value("${ACCESS_KEY}")
	private String access_key;

	@Value("${SECRET_KEY}")
	private String secret_key;

	@Value("${COMMITCHAIN_CONSUMER_ID}")
	private String consumer_id;

	@Value("${ONSADDR}")
	private String onsaddr;

	@Value("${COMMITCHAIN_TOPIC}")
	private String topic;

	@Value("${COMMITCHAIN_TAG}")
	private String tag;
	
	//井通转账，线程多了，好像很容易异常
	@Value("${COMMITCHAIN_THREADNUM}")
	private int commitchainThreadnum ;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
		//获取数据库中待上链数据，传到CommitchainDataQueue中
		try
		{
			List<CommitchainData> list1 = commitchainDataServiceImp.getTodoCommitchainData();
			logger.info("CommitchainTask init list1 size "+list1.size());
			List<CommitchainData> list2 = commitchainDataServiceImp.getDoingCommitchainData();
			logger.info("CommitchainTask init list2 size "+list2.size());
			for(CommitchainData cd : list1)
			{
				CommitchainDataQueue.add(cd);
			}
			for(CommitchainData cd : list2)
			{
				CommitchainDataQueue.add(cd);
			}
		}
		catch (Exception e)
		{
			logger.error("数据库获取待上链数据失败",e);
			e.printStackTrace();
		}
		
		
		// 启动MQ消费者，监听上链数据
		Properties consumerProperties = new Properties();
		consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumer_id);
		consumerProperties.setProperty(PropertyKeyConst.AccessKey, access_key);
		consumerProperties.setProperty(PropertyKeyConst.SecretKey, secret_key);
		consumerProperties.setProperty(PropertyKeyConst.ONSAddr, onsaddr);
		Consumer consumer = ONSFactory.createConsumer(consumerProperties);
		consumer.subscribe(topic, tag, new CommitchainListener(commitchainDataServiceImp));
		consumer.start();
		
		
		

		// 启动上链线程
		ExecutorService executorService = Executors.newFixedThreadPool(commitchainThreadnum, new ThreadFactory()
		{
			public Thread newThread(Runnable r)
			{
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});

		for (int i = 0; i < commitchainThreadnum; ++i)
		{
			CommitchainRunnable runnable = new CommitchainRunnable(commitchainDataServiceImp);
			executorService.execute(runnable);
		}
	}

}
