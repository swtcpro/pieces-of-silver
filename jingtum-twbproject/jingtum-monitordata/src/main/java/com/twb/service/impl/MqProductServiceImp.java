package com.twb.service.impl;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.commondata.data.CommitChainRespMqData;
import org.commondata.data.CommitchainMqData;
import org.commondata.data.DistributeMqData;
import org.commondata.utils.MQUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
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

	
	
	public SendResult sendMQ(String topic, String tag, String data)
	{
		return MQUtils.sendMQ(producer, topic, tag, data);
	}

	@Override
	public SendResult sendMQ(String topic, String tag, CommitchainMqData data)
	{
		return MQUtils.sendMQ(producer, topic, tag, data);
	}
	@Override
	public SendResult sendMQ(String topic, String tag, CommitChainRespMqData data)
	{
		return MQUtils.sendMQ(producer, topic, tag, data);
	}
	@Override
	public SendResult sendMQ(String topic, String tag, DistributeMqData data)
	{
		return MQUtils.sendMQ(producer, topic, tag, data);
	}
	

}
