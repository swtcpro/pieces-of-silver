package com.twb.task;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.twb.commondata.utils.CommonConstants;
import com.twb.commondata.utils.MQUtils;
import com.twb.service.SponsorDataService;
import com.twb.thread.SponsorListener;

@Component
public class SponsorTask
{
	final static Logger logger = LoggerFactory.getLogger(SponsorTask.class);


	
	@Autowired
	SponsorDataService sponsorDataServiceImp;
	
	@Value("${ACCESS_KEY}")
	private String access_key;

	@Value("${SECRET_KEY}")
	private String secret_key;


	@Value("${SPONSORTASK_CONSUMER_ID}")
	private String consumer_id;
	
	private String topic = CommonConstants.COMMITCHAIN_TOPIC;

	@Value("${SPONSORTASK_TAG}")
	private String tag;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
		logger.info("SponsorTask init,"+topic+","+tag);
		try
		{
			sponsorDataServiceImp.handlerTodoData();
		}
		catch (Exception e)
		{
			logger.error("sponsorDataServiceImp.handlerTodoData error",e);
			e.printStackTrace();
		}
		
		// 启动MQ消费者，监听上链数据
		Properties consumerProperties = new Properties();
		consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumer_id);
		consumerProperties.setProperty(PropertyKeyConst.AccessKey, access_key);
		consumerProperties.setProperty(PropertyKeyConst.SecretKey, secret_key);
		Consumer consumer = MQUtils.createConsumer(consumerProperties);
		consumer.subscribe(topic, tag, new SponsorListener(sponsorDataServiceImp));
		consumer.start();
	}
}
