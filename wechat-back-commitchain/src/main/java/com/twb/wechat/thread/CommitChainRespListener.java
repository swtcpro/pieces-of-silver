package com.twb.wechat.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.twb.commondata.data.CommitChainRespMqData;
import com.twb.commondata.listener.CommitChainRespMqListener;
import com.twb.wechat.service.OrderPayService;
import com.twb.wechat.service.impl.OrderPayServiceImp;

public class CommitChainRespListener extends CommitChainRespMqListener
{

	private static final Logger logger = LoggerFactory.getLogger(CommitChainRespListener.class);

	OrderPayService orderPayServiceImp;
	
	@Override
	public Action consume(CommitChainRespMqData ccrmd, Message message, ConsumeContext consumeContext)
	{
		logger.info("CommitChainRespListener consume"+ccrmd);
		
		try
		{
			orderPayServiceImp.doingCcrmd(ccrmd);
		}
		catch (Exception e)
		{
			logger.error("doingCcrmd 异常", e);
		}
		return Action.CommitMessage;
	}

	public CommitChainRespListener(OrderPayService orderPayServiceImp)
	{
		super();
		this.orderPayServiceImp = orderPayServiceImp;
	}



}
