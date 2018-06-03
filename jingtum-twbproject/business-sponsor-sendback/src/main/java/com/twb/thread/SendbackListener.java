package com.twb.thread;

import java.io.UnsupportedEncodingException;

import org.commondata.data.DistributeMqData;
import org.commondata.utils.MQUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.twb.entity.SendbackData;
import com.twb.service.SendbackDataService;


public class SendbackListener implements MessageListener
{

	private static final Logger logger = LoggerFactory.getLogger(SendbackListener.class);

	private SendbackDataService sendbackDataServiceImp;

	@Override
	public Action consume(Message message, ConsumeContext consumeContext)
	{
		// 如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
		logger.info(" Receive message, Topic is:" + message.getTopic() + ", MsgId is:" + message.getMsgID());

		String msgBody = "";
		try
		{
			msgBody = new String(message.getBody(), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			msgBody = new String(message.getBody());
			e.printStackTrace();
			logger.error("getBody", e);
		}
		logger.info("msgBody : " +msgBody);

		SendbackData sd = null;
		try
		{
			DistributeMqData dmd = MQUtils.getDistributeMqData(msgBody);
			sd = sendbackDataServiceImp.saveData(dmd, message);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			logger.error("saveData", e);
		}
		
		
		if(sd==null)
		{
			logger.error("MQ 获取数据失败，未插入数据库,msgId:"+message.getMsgID());
		}
		else
		{
			try
			{
				sendbackDataServiceImp.doingBusiness(sd);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("doingBusiness 异常", e);
			}
		}
		return Action.CommitMessage;
	}

	public SendbackListener(SendbackDataService sendbackDataServiceImp)
	{
		super();
		this.sendbackDataServiceImp = sendbackDataServiceImp;
	}

	public SendbackListener()
	{
		super();
	}
	
	


}
