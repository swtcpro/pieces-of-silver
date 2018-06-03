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
import com.twb.entity.SponsorData;
import com.twb.service.SponsorDataService;


public class SponsorListener implements MessageListener
{

	private static final Logger logger = LoggerFactory.getLogger(SponsorListener.class);

	private SponsorDataService sponsorDataServiceImp;

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

		SponsorData sd = null;
		try
		{
			DistributeMqData dmd = MQUtils.getDistributeMqData(msgBody);
			sd = sponsorDataServiceImp.saveData(dmd, message);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			logger.error("savaCdFromMq", e);
		}
		
		
		if(sd==null)
		{
			logger.error("MQ 获取数据失败，未插入数据库,msgId:"+message.getMsgID());
		}
		else
		{
			try
			{
				sponsorDataServiceImp.doingBusiness(sd);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("doingBusiness 异常", e);
			}
		}
		return Action.CommitMessage;
	}

	public SponsorListener(SponsorDataService sponsorDataServiceImp)
	{
		super();
		this.sponsorDataServiceImp = sponsorDataServiceImp;
	}

	public SponsorListener()
	{
		super();
	}

	

}
