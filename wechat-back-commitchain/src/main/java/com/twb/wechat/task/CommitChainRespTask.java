package com.twb.wechat.task;

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
import com.twb.wechat.service.OrderPayService;
import com.twb.wechat.thread.CommitChainRespListener;


@Component
public class CommitChainRespTask
{
	final static Logger logger = LoggerFactory.getLogger(CommitChainRespTask.class);


	
	@Autowired
	OrderPayService orderPayServiceImp;
	
	@Value("${ACCESS_KEY}")
	private String access_key;

	@Value("${SECRET_KEY}")
	private String secret_key;


	@Value("${WECHAT_CONSUMER_ID}")
	private String consumer_id;
	
	private String topic = CommonConstants.COMMITCHAIN_TOPIC;

	@Value("${WECHAT_TAG}")
	private String tag;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
		logger.info("CommitChainRespTask init,"+topic+","+tag);
		
		// 启动MQ消费者，监听上链数据
		Properties consumerProperties = new Properties();
		consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumer_id);
		consumerProperties.setProperty(PropertyKeyConst.AccessKey, access_key);
		consumerProperties.setProperty(PropertyKeyConst.SecretKey, secret_key);
		Consumer consumer = MQUtils.createConsumer(consumerProperties);
		consumer.subscribe(topic, tag, new CommitChainRespListener(orderPayServiceImp));
		consumer.start();
	}
}


