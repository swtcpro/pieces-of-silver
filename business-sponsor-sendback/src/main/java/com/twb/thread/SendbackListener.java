package com.twb.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.twb.commondata.data.DistributeMqData;
import com.twb.commondata.listener.DistributeMqListener;
import com.twb.entity.SendbackData;
import com.twb.service.SendbackDataService;

public class SendbackListener extends DistributeMqListener
{

	private static final Logger logger = LoggerFactory.getLogger(SendbackListener.class);

	private SendbackDataService sendbackDataServiceImp;

	public Action consume(DistributeMqData dmd, Message message, ConsumeContext consumeContext)
	{
		logger.info("SendbackListener consume"+dmd);
		SendbackData sd = null;
		try
		{
			sd = sendbackDataServiceImp.saveData(dmd, message);
		}
		catch (Exception e)
		{
			logger.error("saveData", e);
		}

		if (sd == null)
		{
			logger.error("MQ 获取数据失败，未插入数据库,msgId:" + message.getMsgID());
		}
		else
		{
			try
			{
				sendbackDataServiceImp.doingBusiness(sd);
			}
			catch (Exception e)
			{
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
