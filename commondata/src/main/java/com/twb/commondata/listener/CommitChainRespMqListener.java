package com.twb.commondata.listener;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.twb.commondata.data.CommitChainRespMqData;
import com.twb.commondata.utils.MQUtils;

public abstract class CommitChainRespMqListener implements MessageListener
{

	private static final Logger logger = LoggerFactory.getLogger(CommitChainRespMqListener.class);


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

		CommitChainRespMqData dmd = null;
		try
		{
			dmd = MQUtils.getCommitChainRespMqData(msgBody);
		}
		catch (Exception e)
		{
			logger.error("getCommitChainRespMqData", e);
		}
		if(dmd==null)
		{
			logger.error("CommitChainRespMq 解析错误:"+msgBody+ ", MsgId is:" + message.getMsgID());
			return Action.CommitMessage;
		}
		else
		{
			return consume(dmd, message, consumeContext);
		}
		
		
		
	}
	
	
	public abstract Action consume(CommitChainRespMqData ccrmd,Message message, ConsumeContext consumeContext);
	

}
