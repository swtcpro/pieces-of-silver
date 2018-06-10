package com.twb.thread;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.twb.commondata.data.CommitchainMqData;
import com.twb.commondata.data.DistributeMqData;
import com.twb.commondata.listener.CommitchainMqListener;
import com.twb.entity.CommitchainData;
import com.twb.service.CommitchainDataService;
import com.twb.utils.CommitchainDataQueue;

/**
 * 
 * @Title:  CommitchainListener.java   
 * @Package com.twb.thread   
 * @Description:    MQ监听数据，并插入数据库 
 * @author: 田文彬     
 * @date:   2018年5月27日 下午11:51:28   
 * @version V1.0
 */
public class CommitchainListener extends CommitchainMqListener
{

	private static final Logger logger = LoggerFactory.getLogger(CommitchainListener.class);

	private CommitchainDataService commitchainDataServiceImp;

	public Action consume(CommitchainMqData cmd,Message message, ConsumeContext consumeContext)
	{
		// 如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
		logger.info(" Receive message, Topic is:" + message.getTopic() + ", MsgId is:" + message.getMsgID());

		
		CommitchainData cd = null;
		try
		{
			cd = commitchainDataServiceImp.savaCdFromMq(cmd);
		}
		catch (Exception e)
		{
			logger.error("savaCdFromMq", e);
		}
		
		
		if(cd==null)
		{
			logger.error("MQ 获取数据失败，未插入数据库,msgId:"+message.getMsgID());
		}
		else
		{
			try
			{
				CommitchainDataQueue.add(cd);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				logger.error("CommitchainDataQueue.add", e);
			}
		}
		return Action.CommitMessage;
	}

	public CommitchainListener(CommitchainDataService commitchainDataServiceImp)
	{
		super();
		this.commitchainDataServiceImp = commitchainDataServiceImp;
	}

	public CommitchainListener()
	{
		super();
	}


}
