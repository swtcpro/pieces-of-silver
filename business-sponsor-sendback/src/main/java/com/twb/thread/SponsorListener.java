package com.twb.thread;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.twb.commondata.data.DistributeMqData;
import com.twb.commondata.listener.DistributeMqListener;
import com.twb.commondata.utils.MQUtils;
import com.twb.entity.SponsorData;
import com.twb.service.SponsorDataService;

public class SponsorListener extends DistributeMqListener
{

	private static final Logger logger = LoggerFactory.getLogger(SponsorListener.class);

	private SponsorDataService sponsorDataServiceImp;

	@Override
	public Action consume(DistributeMqData dmd, Message message, ConsumeContext consumeContext)
	{
		logger.info("SponsorListener consume"+dmd);
		SponsorData sd = null;
		try
		{
			sd = sponsorDataServiceImp.saveData(dmd, message);
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
				sponsorDataServiceImp.doingBusiness(sd);
			}
			catch (Exception e)
			{
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
