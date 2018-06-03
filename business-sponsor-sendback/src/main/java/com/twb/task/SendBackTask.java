package com.twb.task;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.twb.service.SendbackDataService;
import com.twb.thread.SendbackListener;


@Component
public class SendBackTask
{
	final static Logger logger = LoggerFactory.getLogger(SendBackTask.class);


	
	@Autowired
	SendbackDataService sendbackDataServiceImp;
	
	@Value("${ACCESS_KEY}")
	private String access_key;

	@Value("${SECRET_KEY}")
	private String secret_key;

	@Value("${ONSADDR}")
	private String onsaddr;

	@Value("${SENDBACKTASK_CONSUMER_ID}")
	private String consumer_id;
	
	@Value("${SENDBACKTASK_TOPIC}")
	private String topic;

	@Value("${SENDBACKTASK_TAG}")
	private String tag;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
		logger.info("SendBackTask init,"+topic+","+tag);
		try
		{
			sendbackDataServiceImp.handlerTodoData();
		}
		catch (Exception e)
		{
			logger.error("sendbackDataServiceImp.handlerTodoData error",e);
			e.printStackTrace();
		}
		
		// 启动MQ消费者，监听上链数据
		Properties consumerProperties = new Properties();
		consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumer_id);
		consumerProperties.setProperty(PropertyKeyConst.AccessKey, access_key);
		consumerProperties.setProperty(PropertyKeyConst.SecretKey, secret_key);
		consumerProperties.setProperty(PropertyKeyConst.ONSAddr, onsaddr);
		Consumer consumer = ONSFactory.createConsumer(consumerProperties);
		consumer.subscribe(topic, tag, new SendbackListener(sendbackDataServiceImp));
		consumer.start();
	}
}


