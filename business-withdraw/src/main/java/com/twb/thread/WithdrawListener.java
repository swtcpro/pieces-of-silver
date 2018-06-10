package com.twb.thread;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.twb.commondata.data.DistributeMqData;
import com.twb.commondata.listener.DistributeMqListener;
import com.twb.commondata.utils.MQUtils;
import com.twb.entity.Withdrawal;
import com.twb.entity.WithdrawalCommitchain;
import com.twb.service.WithdrawalService;

/**
 * 
 * @Title:  CommitchainListener.java   
 * @Package com.twb.thread   
 * @Description:    MQ监听数据，并插入数据库 
 * @author: 田文彬     
 * @date:   2018年5月27日 下午11:51:28   
 * @version V1.0
 */
public class WithdrawListener extends DistributeMqListener
{

	private static final Logger logger = LoggerFactory.getLogger(WithdrawListener.class);

	private WithdrawalService withdrawalServiceImp;

	@Override
	public Action consume(DistributeMqData dmd,Message message, ConsumeContext consumeContext)
	{
		logger.info("WithdrawListener consume"+dmd);
		Withdrawal withdrawal = null;
		try
		{
			withdrawal = withdrawalServiceImp.saveWithdrawal(dmd, message);
		}
		catch (Exception e)
		{
			logger.error("saveWithdrawal", e);
		}

		if (withdrawal == null)
		{
			logger.error("MQ 获取数据失败，未插入数据库,msgId:" + message.getMsgID());
			return Action.CommitMessage;
		}
		
		WithdrawalCommitchain wc =null;
		try
		{
			wc = withdrawalServiceImp.doingWithdrawal(withdrawal);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("withdrawalServiceImp.doingWithdrawal error", e);
		}
		
		if (wc == null)
		{
			logger.error("提现上链数据获取失败,withdrawal Id:"+withdrawal.getId());
			return Action.CommitMessage;
		}
		
		try
		{
			withdrawalServiceImp.withdrawalCommitChain(wc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("withdrawalServiceImp.withdrawalCommitChain error", e);
		}
		
		
		return Action.CommitMessage;
	}

	public WithdrawListener(WithdrawalService withdrawalServiceImp)
	{
		super();
		this.withdrawalServiceImp = withdrawalServiceImp;
	}

	public WithdrawListener()
	{
		super();
	}

}
