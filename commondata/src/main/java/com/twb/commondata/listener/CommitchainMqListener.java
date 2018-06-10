package com.twb.commondata.listener;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.twb.commondata.data.CommitchainMqData;
import com.twb.commondata.utils.MQUtils;

public abstract class CommitchainMqListener implements MessageListener
{

	private static final Logger logger = LoggerFactory.getLogger(CommitchainMqListener.class);


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
			logger.error("getBody", e);
		}
		logger.info("msgBody : " + msgBody);

		CommitchainMqData cmd = null;
		try
		{
			cmd = MQUtils.getCommitchainMqData(msgBody);
		}
		catch (Exception e)
		{
			logger.error("getCommitchainMqData", e);
		}
		
		if(cmd==null)
		{
			logger.error("CommitchainMq 解析错误:"+msgBody+ ", MsgId is:" + message.getMsgID());
			return Action.CommitMessage;
		}
		else
		{
			return consume(cmd, message, consumeContext);
		}
		
		
		
	}
	
	
	public abstract Action consume(CommitchainMqData cmd,Message message, ConsumeContext consumeContext);
	

}
