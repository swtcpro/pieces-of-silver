package com.twb.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jingtongsdk.bean.Jingtong.reqrsp.Transaction;
import com.jingtongsdk.utils.JingtongRequstConstants;
import com.twb.data.SubscribeData;
import com.twb.entity.DistributeChannel;
import com.twb.entity.SocketData;
import com.twb.repository.SocketDataRepository;
import com.twb.service.SocketDataService;
import com.twb.utils.DistributeQueue;
import com.twb.utils.SocketDataCache;

@Service
public class SocketDataServiceImp implements SocketDataService
{

	private static final Logger logger = LoggerFactory.getLogger(SocketDataServiceImp.class);

	@Autowired
	private SocketDataRepository socketDataRepository;

	@Transactional(rollbackFor = Exception.class)
	public SocketData handlerSubscribeMsg(String msg) throws Exception
	{
		SubscribeData sd = JingtongRequstConstants.PRETTY_PRINT_GSON.fromJson(msg, SubscribeData.class);
		if (sd == null)
		{
			logger.error("SubscribeData is null," + msg);
			return null;
		}
		if(!"Payment".equals(sd.getType()))
		{
			logger.info("SubscribeData is not Payment," + msg);
			return null;
		}
		Transaction transaction = sd.getTransaction();
		if (transaction == null)
		{
			logger.error("transaction is null," + msg);
			return null;
		}
		if (!sd.isSuccess())
		{
			logger.info("sd is not Success," + msg);
			return null;
		}
		
		if (!DistributeChannel.TYPE_RECEIVED.equals(transaction.getType())&&!DistributeChannel.TYPE_SENT.equals(transaction.getType()))
		{
			logger.info("transaction is not received or sent," + msg);
			return null;
		}
		
		SocketData socketData = new SocketData();
		socketData.setDate(new Date((long)(transaction.getDate())*1000));
		socketData.setAmountcurrency(transaction.getAmount().getCurrency());
		socketData.setAmountissuer(transaction.getAmount().getIssuer());
		socketData.setAmountvalue(transaction.getAmount().getValue());
		socketData.setFee(transaction.getFee());
		socketData.setHash(transaction.getHash());
		if(transaction.getMemos()!=null&&transaction.getMemos().length>0)
		{
			String memos = transaction.getMemos()[0];
			if(memos!=null&&memos.length()>1000)
			{
				memos = memos.substring(0, 1000);
			}
			socketData.setMemos(memos);
		}
		socketData.setCounterparty(transaction.getCounterparty());
		socketData.setType(transaction.getType());
		socketData.setFlag(SocketData.FLAG_TODO);
		socketData.setResult(transaction.getResult());
		
		socketData=socketDataRepository.save(socketData);
		
		return socketData;
	}

	@Override
	public void socketDataForCheck() throws Exception
	{
		List<SocketData> list = socketDataRepository.getSocketData1day();
		if(list!=null&&!list.isEmpty())
		{
			for(SocketData w :list)
			{
				SocketDataCache.add(w.getHash());
			}
		}
	}

	@Override
	public void socketDataDistribute() throws Exception
	{
		List<SocketData> list = socketDataRepository.getTodoSocketData();
		if(list!=null&&!list.isEmpty())
		{
			for(SocketData w :list)
			{
				DistributeQueue.add(w);
			}
		}
		
	}

}
