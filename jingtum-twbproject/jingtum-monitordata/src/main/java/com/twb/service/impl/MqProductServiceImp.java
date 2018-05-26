package com.twb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.twb.entity.DistributeChannel;
import com.twb.entity.DistributeLog;
import com.twb.service.MqProductService;

@Service
public class MqProductServiceImp implements MqProductService
{

	private static final Logger logger = LoggerFactory.getLogger(MqProductServiceImp.class);

	@Value("${ACCESS_KEY}")
	private String access_key;

	@Value("${SECRET_KEY}")
	private String secret_key;

	@Value("${PRODUCER_ID}")
	private String producer_id;

	@Value("${ONSADDR}")
	private String onsaddr;
	
	Producer producer;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
		Properties producerProperties = new Properties();
		producerProperties.setProperty(PropertyKeyConst.ProducerId, producer_id);
		producerProperties.setProperty(PropertyKeyConst.AccessKey, access_key);
		producerProperties.setProperty(PropertyKeyConst.SecretKey, secret_key);
		producerProperties.setProperty(PropertyKeyConst.ONSAddr, onsaddr);
		producer = ONSFactory.createProducer(producerProperties);
		producer.start();
	}

	
	
	@Override
	public SendResult sendMQ(String topic, String tag, String data)
	{
		try
		{

			Message message = new Message(topic,tag, data.getBytes("UTF-8"));
			SendResult sendResult = producer.send(message);
			logger.info("发送成功1，" + "Topic is:" + topic + "," + tag + " msgId is: "
					+ sendResult.getMessageId());
			return sendResult;

		}
		catch (Exception e)
		{
			logger.error("发送失败1", e);

			try
			{
				Message message = new Message(topic,tag, data.getBytes("UTF-8"));
				SendResult sendResult = producer.send(message);
				logger.info("发送成功2，" + "Topic is:" + topic + "," + tag + " msgId is: "
						+ sendResult.getMessageId());
				return sendResult;
			}
			catch (Exception e1)
			{
				logger.error("发送失败2", e);
				e1.printStackTrace();
				return null;
			}

		}
	}

	

}
