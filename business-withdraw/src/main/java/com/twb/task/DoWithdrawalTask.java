package com.twb.task;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.twb.commondata.utils.CommonConstants;
import com.twb.commondata.utils.MQUtils;
import com.twb.entity.Withdrawal;
import com.twb.entity.WithdrawalCommitchain;
import com.twb.service.WithdrawalService;
import com.twb.thread.WithdrawListener;

@Component
@DependsOn(value = "TransferUtil")
public class DoWithdrawalTask
{
	final static Logger logger = LoggerFactory.getLogger(DoWithdrawalTask.class);

	@Autowired
	WithdrawalService withdrawalServiceImp;

	@Value("${ACCESS_KEY}")
	private String access_key;

	@Value("${SECRET_KEY}")
	private String secret_key;

	private String onsaddr = CommonConstants.COMMITCHAIN_ONSADDR;

	@Value("${WITHDRAWAL_CONSUMER_ID}")
	private String consumer_id;

	private String topic = CommonConstants.COMMITCHAIN_TOPIC;

	@Value("${WITHDRAWAL_TAG}")
	private String tag;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{

		List<Withdrawal> withdrawalList = null;
		try
		{
			withdrawalList = withdrawalServiceImp.getTodoWithdrawal();
		}
		catch (Exception e)
		{
			logger.error("getTodoWithdrawal error", e);
			e.printStackTrace();
		}

		if (withdrawalList != null && !withdrawalList.isEmpty())
		{
			for (Withdrawal w : withdrawalList)
			{
				WithdrawalCommitchain wc = null;
				try
				{
					wc = withdrawalServiceImp.doingWithdrawal(w);
				}
				catch (Exception e)
				{
					logger.error("doingWithdrawal error", e);
					e.printStackTrace();
				}

			}
		}
		List<WithdrawalCommitchain> wclList = null;

		try
		{
			wclList = withdrawalServiceImp.getTodoWithdrawalCommitchain();
		}
		catch (Exception e)
		{
			logger.error("getTodoWithdrawalCommitchain error", e);
			e.printStackTrace();
		}

		if (wclList != null && !wclList.isEmpty())
		{

			for (WithdrawalCommitchain w : wclList)
			{

				try
				{
					withdrawalServiceImp.withdrawalCommitChain(w);
				}
				catch (Exception e)
				{
					logger.error("withdrawalCommitChain error", e);
					e.printStackTrace();
				}
			}

		}
		// 启动MQ消费者，监听上链数据
		Properties consumerProperties = new Properties();
		consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumer_id);
		consumerProperties.setProperty(PropertyKeyConst.AccessKey, access_key);
		consumerProperties.setProperty(PropertyKeyConst.SecretKey, secret_key);
		consumerProperties.setProperty(PropertyKeyConst.ONSAddr, onsaddr);
		Consumer consumer = MQUtils.createConsumer(consumerProperties);
		consumer.subscribe(topic, tag, new WithdrawListener(withdrawalServiceImp));
		consumer.start();

	}
}
