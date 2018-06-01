package com.twb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.commondata.data.DistributeMqData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.twb.service.MqProductService;

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

	@Autowired
	private MqProductService mqProductServiceImp;

	List<DistributeChannel> distributeChlAll = new ArrayList();
	List<DistributeChannel> distributeChlOthersNone = new ArrayList();
	List<DistributeChannel> disChlStartWithFlag = new ArrayList();

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

	}

	private void handlerDistributeLog(DistributeLog dl)
	{
		String sendData = JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(dl);
		logger.info("handlerDistributeLog sendData:" + sendData);
		
		DistributeMqData dmd = new DistributeMqData();
		dmd.setAmountcurrency(dl.getAmountcurrency());
		dmd.setAmountissuer(dl.getAmountissuer());
		dmd.setAmountvalue(dl.getAmountvalue());
		dmd.setCounterparty(dl.getCounterparty());
		dmd.setDate(dl.getDate());
		dmd.setDistributeType(dl.getDistributeType());
		dmd.setFee(dl.getFee());
		dmd.setHash(dl.getHash());
		dmd.setId(dl.getId());
		dmd.setMemos(dl.getMemos());
		dmd.setResult(dl.getResult());
		dmd.setType(dl.getType());
		
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
					if (memos != null && memos.length() > 0 && flag != null && flag.length() > 0
							&& memos.startsWith(flag))
					{
						hasSend = true;
						sendMQ(dl, dmd, dc);
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
						sendMQ(dl, dmd, dc);
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
					sendMQ(dl, dmd, dc);
				}

			}
			catch (Exception e)
			{
				logger.error("handlerDistributeLog distributeChlAll 失败," + i, e);
			}

		}

	}

	private void sendMQ(DistributeLog dl, DistributeMqData dmd, DistributeChannel dc) throws CloneNotSupportedException
	{
		logger.info("发送，hash:" + dl.getHash()+",topic:"+dc.getTopic()+",tag:"+dc.getTag());
		SendResult sendResult = mqProductServiceImp.sendMQ(dc.getTopic(), dc.getTag(), dmd);
		if (sendResult != null)
		{
			DistributeLog dlClone = (DistributeLog) dl.clone();
			dlClone.setTopic(dc.getTopic());
			dlClone.setTag(dc.getTag());
			dlClone.setMessageDate(new Date());
			dlClone.setMessageid(sendResult.getMessageId());
			dlClone.setSendResult(DistributeLog.SENDRESULT_SUCCESS);
			distributeLogRepository.save(dlClone);
		}
		else
		{

			DistributeLog dlClone = (DistributeLog) dl.clone();
			dlClone.setTopic(dc.getTopic());
			dlClone.setTag(dc.getTag());
			dlClone.setMessageDate(new Date());
			dlClone.setSendResult(DistributeLog.SENDRESULT_FAIL);
			distributeLogRepository.save(dlClone);
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
