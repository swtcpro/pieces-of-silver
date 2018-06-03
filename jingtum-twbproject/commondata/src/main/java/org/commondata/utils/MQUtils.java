package org.commondata.utils;

import org.commondata.data.CommitChainRespMqData;
import org.commondata.data.CommitchainMqData;
import org.commondata.data.DistributeMqData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;

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
	
	public static CommitchainMqData getCommitchainMqData(String data)
	{
		if(data==null||data.length()==0)
		{
			return null;
		}
		return CommonConstants.PRETTY_PRINT_GSON.fromJson(data, CommitchainMqData.class);
	}
	
	public static CommitChainRespMqData getCommitChainRespMqData(String data)
	{
		if(data==null||data.length()==0)
		{
			return null;
		}
		return CommonConstants.PRETTY_PRINT_GSON.fromJson(data, CommitChainRespMqData.class);
	}
	
	public static DistributeMqData getDistributeMqData(String data)
	{
		if(data==null||data.length()==0)
		{
			return null;
		}
		return CommonConstants.PRETTY_PRINT_GSON.fromJson(data, DistributeMqData.class);
	}
	
	public static <T> T fromJson(String data,Class<T> cla)
	{
		if(data==null||data.length()==0)
		{
			return null;
		}
		return CommonConstants.PRETTY_PRINT_GSON.fromJson(data, cla);
	}
	
	public static String toJson(Object obj)
	{
		return CommonConstants.PRETTY_PRINT_GSON.toJson(obj);
	}
	
}
