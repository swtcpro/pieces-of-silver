package com.twb.commondata.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.twb.commondata.data.CommitChainRespMqData;
import com.twb.commondata.data.CommitchainMqData;
import com.twb.commondata.data.DistributeMqData;

public class MQUtils
{
	private static final Logger logger = LoggerFactory.getLogger(MQUtils.class);

	public static SendResult sendMQ(Producer producer,String topic, String tag, String data)
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
	/**
	 * 
	 * @Title: sendMQ   
	 * @Description: 发送MQ上链数据
	 * @param: @param producer
	 * @param: @param topic
	 * @param: @param tag
	 * @param: @param data
	 * @param: @return      
	 * @return: SendResult      
	 * @throws
	 */
	public static SendResult sendMQ(Producer producer,String topic, String tag, CommitchainMqData data)
	{
		return sendMQ(producer, topic, tag, CommonConstants.PRETTY_PRINT_GSON.toJson(data));
	}
	/**
	 * 
	 * @Title: sendCommitChainMQ   
	 * @Description: 发送上链的MQ
	 * @param: @param producer
	 * @param: @param data
	 * @param: @return      
	 * @return: SendResult      
	 * @throws
	 */
	public static SendResult sendCommitChainMQ(Producer producer,CommitchainMqData data)
	{
		return sendMQ(producer, CommonConstants.COMMITCHAIN_TOPIC, CommonConstants.COMMITCHAIN_TAG, CommonConstants.PRETTY_PRINT_GSON.toJson(data));
	}
	/**
	 * 
	 * @Title: sendMQ   
	 * @Description:发送MQ上链确认反馈数据
	 * @param: @param producer
	 * @param: @param topic
	 * @param: @param tag
	 * @param: @param data
	 * @param: @return      
	 * @return: SendResult      
	 * @throws
	 */
	public static SendResult sendMQ(Producer producer,String topic, String tag, CommitChainRespMqData data)
	{
		return sendMQ(producer, topic, tag, CommonConstants.PRETTY_PRINT_GSON.toJson(data));
	}
	
	/**
	 * 
	 * @Title: sendMQ   
	 * @Description: 发送MQ，分发数据
	 * @param: @param producer
	 * @param: @param topic
	 * @param: @param tag
	 * @param: @param data
	 * @param: @return      
	 * @return: SendResult      
	 * @throws
	 */
	public static SendResult sendMQ(Producer producer,String topic, String tag, DistributeMqData data)
	{
		return sendMQ(producer, topic, tag, CommonConstants.PRETTY_PRINT_GSON.toJson(data));
	}
	
	/**
	 * 
	 * @Title: getCommitchainMqData   
	 * @Description: 解析上链的MQ数据
	 * @param: @param data
	 * @param: @return      
	 * @return: CommitchainMqData      
	 * @throws
	 */
	public static CommitchainMqData getCommitchainMqData(String data)
	{
		if(data==null||data.length()==0)
		{
			return null;
		}
		return CommonConstants.PRETTY_PRINT_GSON.fromJson(data, CommitchainMqData.class);
	}
	
	/**
	 * 
	 * @Title: getCommitChainRespMqData   
	 * @Description: 解析上链结果
	 * @param: @param data
	 * @param: @return      
	 * @return: CommitChainRespMqData      
	 * @throws
	 */
	public static CommitChainRespMqData getCommitChainRespMqData(String data)
	{
		if(data==null||data.length()==0)
		{
			return null;
		}
		return CommonConstants.PRETTY_PRINT_GSON.fromJson(data, CommitChainRespMqData.class);
	}
	/**
	 * 
	 * @Title: getDistributeMqData   
	 * @Description: 解析分发的数据
	 * @param: @param data
	 * @param: @return      
	 * @return: DistributeMqData      
	 * @throws
	 */
	public static DistributeMqData getDistributeMqData(String data)
	{
		if(data==null||data.length()==0)
		{
			return null;
		}
		return CommonConstants.PRETTY_PRINT_GSON.fromJson(data, DistributeMqData.class);
	}
	/**
	 * 
	 * @Title: fromJson   
	 * @Description: json转对象
	 * @param: @param data
	 * @param: @param cla
	 * @param: @return      
	 * @return: T      
	 * @throws
	 */
	public static <T> T fromJson(String data,Class<T> cla)
	{
		if(data==null||data.length()==0)
		{
			return null;
		}
		return CommonConstants.PRETTY_PRINT_GSON.fromJson(data, cla);
	}
	/**
	 * 
	 * @Title: toJson   
	 * @Description: 对象转json
	 * @param: @param obj
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String toJson(Object obj)
	{
		return CommonConstants.PRETTY_PRINT_GSON.toJson(obj);
	}
	
	/**
	 * 
	 * @Title: createConsumer   
	 * @Description: 创建消费者
	 * @param: @param consumerProperties
	 * @param: @return      
	 * @return: Consumer      
	 * @throws
	 */
	public static Consumer createConsumer(Properties consumerProperties)
	{
		String onsAddr = (String) consumerProperties.get(PropertyKeyConst.ONSAddr);
		if(onsAddr==null||onsAddr.isEmpty())
		{
			consumerProperties.setProperty(PropertyKeyConst.ONSAddr, CommonConstants.COMMITCHAIN_ONSADDR);
		}
		
		return ONSFactory.createConsumer(consumerProperties);
	}
	
	/**
	 * 
	 * @Title: createProducer   
	 * @Description: 创建生产者
	 * @param: @param producerProperties
	 * @param: @return      
	 * @return: Producer      
	 * @throws
	 */
	public static Producer createProducer(Properties producerProperties)
	{
		String onsAddr = (String) producerProperties.get(PropertyKeyConst.ONSAddr);
		if(onsAddr==null||onsAddr.isEmpty())
		{
			producerProperties.setProperty(PropertyKeyConst.ONSAddr, CommonConstants.COMMITCHAIN_ONSADDR);
		}
		String producer_id = (String) producerProperties.get(PropertyKeyConst.ProducerId);
		if(producer_id==null||producer_id.isEmpty())
		{
			producerProperties.setProperty(PropertyKeyConst.ProducerId, CommonConstants.COMMITCHAIN_PRODUCER_ID);
		}
		return ONSFactory.createProducer(producerProperties);
	}
	
	
	
	
	
}
