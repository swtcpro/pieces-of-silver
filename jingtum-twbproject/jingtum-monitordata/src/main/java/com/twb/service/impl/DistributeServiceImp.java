package com.twb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.jingtongsdk.utils.JingtongRequstConstants;
import com.twb.entity.DistributeChannel;
import com.twb.entity.DistributeLog;
import com.twb.entity.SocketData;
import com.twb.entity.TimerData;
import com.twb.repository.DistributeChannelRepository;
import com.twb.repository.DistributeLogRepository;
import com.twb.repository.SocketDataRepository;
import com.twb.repository.TimerDataRepository;
import com.twb.service.DistributeService;

@Service
public class DistributeServiceImp implements DistributeService
{

	private static final Logger logger = LoggerFactory.getLogger(DistributeServiceImp.class);

	@Autowired
	private DistributeLogRepository distributeLogRepository;

	@Autowired
	private SocketDataRepository socketDataRepository;

	@Autowired
	private TimerDataRepository timerDataLogRepository;

	@Autowired
	private DistributeChannelRepository distributeChannelRepository;

	@Value("${ACCESS_KEY}")
	private String access_key;

	@Value("${SECRET_KEY}")
	private String secret_key;

	@Value("${PRODUCER_ID}")
	private String producer_id;

	@Value("${ONSADDR}")
	private String onsaddr;

	List<DistributeChannel> distributeChlAll = new ArrayList();
	List<DistributeChannel> distributeChlOthersNone = new ArrayList();
	List<DistributeChannel> disChlStartWithFlag = new ArrayList();

	Producer producer;

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
		try
		{
			List<DistributeChannel> distributeChannelList = distributeChannelRepository.getAllDistributeChannel();
			for (int i = 0; i < distributeChannelList.size(); i++)
			{
				DistributeChannel dc = distributeChannelList.get(i);
				String flagtype = dc.getFlagtype();
				if (DistributeChannel.FLAGTYPE_ALL.equals(flagtype))
				{
					distributeChlAll.add(dc);
				}
				else if (DistributeChannel.FLAGTYPE_OTHERS_NONE.equals(flagtype))
				{
					distributeChlOthersNone.add(dc);
				}
				else if (DistributeChannel.FLAGTYPE_MEMOS_STARTWITH_FLAG.equals(flagtype))
				{
					disChlStartWithFlag.add(dc);
				}
			}

		}
		catch (Exception e)
		{
			logger.error("distributeChannelList 获取失败", e);
		}

		Properties producerProperties = new Properties();
		producerProperties.setProperty(PropertyKeyConst.ProducerId, producer_id);
		producerProperties.setProperty(PropertyKeyConst.AccessKey, access_key);
		producerProperties.setProperty(PropertyKeyConst.SecretKey, secret_key);
		producerProperties.setProperty(PropertyKeyConst.ONSAddr, onsaddr);
		producer = ONSFactory.createProducer(producerProperties);
		producer.start();
	}

	private void handlerDistributeLog(DistributeLog dl)
	{
		String sendData = JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(dl);
		logger.info("handlerDistributeLog sendData:" + sendData);

		boolean hasSend = false;

		// 普通条件过滤，发送MQ
		for (int i = 0; i < disChlStartWithFlag.size(); i++)
		{
			try
			{
				DistributeChannel dc = disChlStartWithFlag.get(i);
				if (dl.getType().equals(dc.getType()))
				{
					String memos = dl.getMemos();
					String flag = dc.getFlag();
					if (memos != null && memos.length() > 0 &&flag!=null&&flag.length()>0&& memos.startsWith(flag))
					{
						hasSend = true;
						sendMQ(dl, sendData, dc);
					}

				}

			}
			catch (Exception e)
			{
				logger.error("handlerDistributeLog distributeChlNormal 失败," + i, e);
			}
		}

		// 如果普通条件都没有过滤到，发送MQ
		if (!hasSend)
		{
			for (int i = 0; i < distributeChlOthersNone.size(); i++)
			{
				try
				{
					DistributeChannel dc = distributeChlOthersNone.get(i);
					if (dl.getType().equals(dc.getType()))
					{
						sendMQ(dl, sendData, dc);
					}

				}
				catch (Exception e)
				{
					logger.error("handlerDistributeLog distributeChlAll 失败," + i, e);
				}

			}
		}

		// 所有的都发送
		for (int i = 0; i < distributeChlAll.size(); i++)
		{
			try
			{
				DistributeChannel dc = distributeChlAll.get(i);
				if (dl.getType().equals(dc.getType()))
				{
					sendMQ(dl, sendData, dc);
				}

			}
			catch (Exception e)
			{
				logger.error("handlerDistributeLog distributeChlAll 失败," + i, e);
			}

		}

	}

	private void sendMQ(DistributeLog dl, String sendData, DistributeChannel dc) throws CloneNotSupportedException
	{
		try
		{

			Message message = new Message(dc.getTopic(), dc.getTag(), sendData.getBytes("UTF-8"));
			SendResult sendResult = producer.send(message);
			logger.info("发送成功1，hash:" + dl.getHash() + "Topic is:" + dc.getTopic() + "," + dc.getTag() + " msgId is: "
					+ sendResult.getMessageId());
			DistributeLog dlClone = (DistributeLog) dl.clone();
			dlClone.setTopic(dc.getTopic());
			dlClone.setTag(dc.getTag());
			dlClone.setMessageDate(new Date());
			dlClone.setMessageid(sendResult.getMessageId());
			dlClone.setSendResult(DistributeLog.SENDRESULT_SUCCESS);
			distributeLogRepository.save(dlClone);

		}
		catch (Exception e)
		{
			logger.error("发送失败1", e);

			try
			{
				Message message = new Message(dc.getTopic(), dc.getTag(), sendData.getBytes("UTF-8"));
				SendResult sendResult = producer.send(message);
				logger.info("发送成功2，hash:" + dl.getHash() + "Topic is:" + dc.getTopic() + "," + dc.getTag()
						+ " msgId is: " + sendResult.getMessageId());
				DistributeLog dlClone = (DistributeLog) dl.clone();
				dlClone.setTopic(dc.getTopic());
				dlClone.setTag(dc.getTag());
				dlClone.setMessageDate(new Date());
				dlClone.setMessageid(sendResult.getMessageId());
				dlClone.setSendResult(DistributeLog.SENDRESULT_SUCCESS);
				distributeLogRepository.save(dlClone);
			}
			catch (Exception e1)
			{
				logger.error("发送失败2", e);
				e1.printStackTrace();
				DistributeLog dlClone = (DistributeLog) dl.clone();
				dlClone.setTopic(dc.getTopic());
				dlClone.setTag(dc.getTag());
				dlClone.setMessageDate(new Date());
				dlClone.setSendResult(DistributeLog.SENDRESULT_FAIL);
				distributeLogRepository.save(dlClone);
			}

		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void handlerSocketData(SocketData socketData) throws Exception
	{
		DistributeLog dl = new DistributeLog();
		dl.setDate(socketData.getDate());
		dl.setHash(socketData.getHash());
		dl.setType(socketData.getType());
		dl.setResult(socketData.getResult());
		dl.setFee(socketData.getFee());
		dl.setMemos(socketData.getMemos());
		dl.setCounterparty(socketData.getCounterparty());
		dl.setAmountvalue(socketData.getAmountvalue());
		dl.setAmountcurrency(socketData.getAmountcurrency());
		dl.setAmountissuer(socketData.getAmountissuer());
		dl.setDistributeType(DistributeLog.DISTRIBUTETYPE_SOCKET);
		handlerDistributeLog(dl);
		socketData.setFlag(SocketData.FLAG_DONE);
		socketDataRepository.save(socketData);

	}

	@Transactional(rollbackFor = Exception.class)
	public void handlerTimerData(TimerData timerData) throws Exception
	{
		DistributeLog dl = new DistributeLog();
		dl.setDate(timerData.getDate());
		dl.setHash(timerData.getHash());
		dl.setType(timerData.getType());
		dl.setResult(timerData.getResult());
		dl.setFee(timerData.getFee());
		dl.setMemos(timerData.getMemos());
		dl.setCounterparty(timerData.getCounterparty());
		dl.setAmountvalue(timerData.getAmountvalue());
		dl.setAmountcurrency(timerData.getAmountcurrency());
		dl.setAmountissuer(timerData.getAmountissuer());
		dl.setDistributeType(DistributeLog.DISTRIBUTETYPE_TIMER);
		handlerDistributeLog(dl);
		timerData.setCheckflag(TimerData.CHECKFLAG_DISTRIBUTED_DONE);
		timerDataLogRepository.save(timerData);
	}

}
