package com.twb.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jingtongsdk.bean.Jingtong.reqrsp.Transaction;
import com.jingtongsdk.bean.Jingtong.reqrsp.TransactionAmount;
import com.jingtongsdk.bean.Jingtong.reqrsp.TransactionsRecordRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.TransactionsRecordResponse;
import com.jingtongsdk.utils.JingtongRequestUtils;
import com.twb.entity.CommitchainVerifyData;
import com.twb.entity.DistributeChannel;
import com.twb.entity.TimerData;
import com.twb.repository.CommitchainVerifyDataRepository;
import com.twb.repository.TimerDataRepository;
import com.twb.service.TimerDataService;
import com.twb.utils.DistributeQueue;
import com.twb.utils.SocketDataCache;

@Service
public class TimerDataServiceImp implements TimerDataService
{

	private static final Logger logger = LoggerFactory.getLogger(TimerDataServiceImp.class);

	@Autowired
	private TimerDataRepository timerDataRepository;
	
	@Autowired
	private CommitchainVerifyDataRepository commitchainVerifyDataRepository;
	

	@Transactional(rollbackFor = Exception.class)
	public void handlerSubscribeMsg(String msg) throws Exception
	{

	}

	@Transactional(rollbackFor = Exception.class)
	public List<TimerData> getTranFromJingtong(String address, String lastHash) throws Exception
	{
		logger.info("getTranFromJingtong:" + address + "," + lastHash);
		List<TimerData> list = new ArrayList();

		// 井通取出来的数据，在数据库中是否存在
		boolean checkLastHash = false;

		// 需要添加数据库的list
		List<Transaction> addList = new ArrayList<Transaction>();

		TransactionsRecordRequest oblr = new TransactionsRecordRequest();
		oblr.setAddress(address);
		while (!checkLastHash)
		{
			TransactionsRecordResponse jrr = (TransactionsRecordResponse) JingtongRequestUtils.sendRequest(oblr);
			if (jrr == null)
			{
				break;
			}

			// 接口返回的数据是根据时间倒序排列,最新的数据排在前面
			Transaction[] timerDatas = jrr.getTransactions();
			for (Transaction tran : timerDatas)
			{
				if (lastHash != null && lastHash.length() > 0 && lastHash.equals(tran.getHash()))
				{
					checkLastHash = true;
					break;
				}

				addList.add(tran);
			}

			// 下条数据marker
			if (jrr.getMarker() != null)
			{
				oblr.setMarker(jrr.getMarker());
			}
			else
			{
				break;
			}

		}

		Collections.reverse(addList);
		for (Transaction tran : addList)
		{
			TimerData timerData = new TimerData();
			TransactionAmount amount = tran.getAmount();
			timerData.setAmountcurrency(amount.getCurrency());
			timerData.setAmountissuer(amount.getIssuer());
			timerData.setAmountvalue(amount.getValue());

			timerData.setCounterparty(tran.getCounterparty());
			timerData.setDate(new Date((long) (tran.getDate()) * 1000));
			timerData.setFee(tran.getFee());
			timerData.setHash(tran.getHash());
			if (tran.getMemos() != null && tran.getMemos().length > 0)
			{
				String memos = tran.getMemos()[0];
				if (memos != null && memos.length() > 1000)
				{
					memos = memos.substring(0, 1000);
				}
				timerData.setMemos(memos);
			}

			timerData.setResult(tran.getResult());
			timerData.setType(tran.getType());

			// 成功，并且是入账或者是出账,添加到待比对List
			if ("tesSUCCESS".equals(timerData.getResult()) && (DistributeChannel.TYPE_SENT.equals(timerData.getType())
					|| DistributeChannel.TYPE_RECEIVED.equals(timerData.getType())))
			{
				list.add(timerData);
				timerData.setCheckflag(TimerData.CHECKFLAG_TOCHECK);
			}
			else
			{
				timerData.setCheckflag(TimerData.CHECKFLAG_NONEED);
			}
			timerDataRepository.save(timerData);
			
			// 成功，并且是出账,添加到上链数据验证表
			if ("tesSUCCESS".equals(timerData.getResult()) && DistributeChannel.TYPE_SENT.equals(timerData.getType()))
			{
				CommitchainVerifyData rvd = new CommitchainVerifyData();
				rvd.setAmountcurrency(timerData.getAmountcurrency());
				rvd.setAmountissuer(timerData.getAmountissuer());
				rvd.setAmountvalue(timerData.getAmountvalue());
				rvd.setCounterparty(timerData.getCounterparty());
				rvd.setDate(timerData.getDate());
				rvd.setFee(timerData.getFee());
				rvd.setHash(timerData.getHash());
				rvd.setMemos(timerData.getMemos());
				rvd.setResult(timerData.getResult());
				rvd.setType(timerData.getType());
				rvd.setCheckflag(CommitchainVerifyData.CHECKFLAG_TOCHECK);
				commitchainVerifyDataRepository.save(rvd);
			}
			
		}
		return list;
	}

	@Override
	public String getLastTranHash() throws Exception
	{
		TimerData timerData = timerDataRepository.getLastTran();
		if (timerData == null)
		{
			return "";
		}
		else
		{
			return timerData.getHash();
		}

	}

	@Override
	public List<TimerData> getTobeCheckTran() throws Exception
	{
		List list = timerDataRepository.getCheckTranList();
		if (list == null)
		{
			return new ArrayList();
		}
		return list;
	}

	@Transactional(noRollbackFor = Exception.class)
	public List<TimerData> checkSocketData(List<TimerData> list) throws Exception
	{
		logger.info("checkSocketData start");
		if (list == null || list.isEmpty())
		{
			return new ArrayList();
		}

		// 要继续比对的list
		List<TimerData> continueList = new ArrayList<TimerData>();

		for (TimerData timerData : list)
		{

			try
			{
				// 如果不是成功，或者是入账收账，无需比对
				if (!"tesSUCCESS".equals(timerData.getResult())
						&& (DistributeChannel.TYPE_SENT.equals(timerData.getType())
								|| DistributeChannel.TYPE_RECEIVED.equals(timerData.getType())))
				{
					logger.warn("异常数据进行比对：" + timerData.getHash());
					timerData.setCheckflag(TimerData.CHECKFLAG_NONEED);
					timerDataRepository.save(timerData);
					continue;
				}

				// SocketData不存在
				if (SocketDataCache.remove(timerData.getHash()))
				{
					logger.info("socket已监听到，核对成功：" + timerData.getHash());
					timerData.setCheckflag(TimerData.CHECKFLAG_SUCCESS);
					timerDataRepository.save(timerData);
				}
				else
				{
					if (TimerData.CHECKFLAG_TOCHECK.equals(timerData.getCheckflag()))
					{
						timerData.setCheckflag(TimerData.CHECKFLAG_TOENSURE);
						timerDataRepository.save(timerData);
						continueList.add(timerData);
					}
					else if (TimerData.CHECKFLAG_TOENSURE.equals(timerData.getCheckflag()))
					{
						DistributeQueue.add(timerData);
						logger.info("socket未监听到,已添加到消息队列：" + timerData.getHash());
					}
					else
					{
						logger.error("transaction has error data:" + timerData.getHash());

						timerData.setCheckmsg("transaction has error flag :" + timerData.getCheckflag());

						timerData.setCheckflag(TimerData.CHECKFLAG_EXCEPTION);
						timerDataRepository.save(timerData);
					}

				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("socketData 添加错误：" + timerData.getHash(), e);
				try
				{
					timerData.setCheckflag(TimerData.CHECKFLAG_EXCEPTION);
					timerData.setCheckmsg(e.toString());
					timerDataRepository.save(timerData);

				}
				catch (Exception e1)
				{
					logger.error("timerData数据插入异常", e1);
					e1.printStackTrace();
				}
			}

		}
		return continueList;

	}

}
