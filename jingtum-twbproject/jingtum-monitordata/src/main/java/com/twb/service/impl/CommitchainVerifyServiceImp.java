package com.twb.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.twb.entity.CommitchainData;
import com.twb.entity.CommitchainVerifyData;
import com.twb.entity.DistributeChannel;
import com.twb.entity.TimerData;
import com.twb.repository.CommitchainDataRepository;
import com.twb.repository.CommitchainVerifyDataRepository;
import com.twb.service.CommitchainVerifyService;
import com.twb.utils.CommitChainVerifyDataQueue;

@Service
public class CommitchainVerifyServiceImp implements CommitchainVerifyService
{

	private static final Logger logger = LoggerFactory.getLogger(CommitchainVerifyServiceImp.class);

	@Autowired
	private CommitchainVerifyDataRepository commitchainVerifyDataRepository;

	@Autowired
	CommitchainDataRepository commitchainDataRepository;

	
	@Value("${commitchain_fail_time}")
	private int commitchainFailTime;
	
	@Transactional(rollbackFor = Exception.class)
	public List<CommitchainVerifyData> getCommitchainVerifyData(List<TimerData> list) throws Exception
	{
		List toVerifyList = new ArrayList();
		if (list == null || list.isEmpty())
		{
			return toVerifyList;
		}

		for (TimerData timerData : list)
		{
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

				rvd.setResult(timerData.getResult());
				rvd.setType(timerData.getType());

				String memos = timerData.getMemos();
				rvd.setMemos(memos);

				Integer cid = getCid(memos);
				if (cid == null || cid == 0)
				{
					rvd.setCid(cid);
					rvd.setCheckflag(CommitchainVerifyData.CHECKFLAG_ERROR);
					rvd.setCheckmsg("cid 解析错误");
					commitchainVerifyDataRepository.save(rvd);
				}
				else
				{
					rvd.setCid(cid);
					rvd.setCheckflag(CommitchainVerifyData.CHECKFLAG_TOCHECK);
					commitchainVerifyDataRepository.save(rvd);
					toVerifyList.add(rvd);
				}

			}
		}
		return toVerifyList;
	}

	// 解析memos，拿到上链数据表CommitchainData id
	private Integer getCid(String memos)
	{
		Integer cid = 0;
		if (StringUtils.isEmpty(memos))
		{
			return cid;
		}
		try
		{
			Map maps = (Map) JSON.parse(memos);
			cid = (Integer) maps.get("id");
			if (cid == null)
			{
				cid = 0;
			}
		}
		catch (Exception e)
		{
			logger.error("解析错误:" + memos, e);
			e.printStackTrace();
		}

		return cid;

	}

	@Override
	public void addChainVerifydataQueue(List<CommitchainVerifyData> list) throws Exception
	{
		if (list == null)
		{
			return;
		}
		for (CommitchainVerifyData c : list)
		{
			CommitChainVerifyDataQueue.add(c);
		}

	}

	@Override
	public List<CommitchainVerifyData> getTocheckCVD() throws Exception
	{
		return commitchainVerifyDataRepository.getTocheckCVD();
	}

	@Transactional(rollbackFor = Exception.class)
	public void doingTocheckCVD(CommitchainVerifyData cvd) throws Exception
	{
		if (cvd == null)
		{
			logger.error("cvd is null");
			return;
		}
		logger.info("doingTocheckCVD,id:"+cvd.getId());
		Integer cid = cvd.getCid();
		if (cid == null || cid == 0)
		{
			logger.error("cid 错误");
			saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_ERROR, "cid 错误");
			return;
		}

		CommitchainData cd = null;
		try
		{
			cd = commitchainDataRepository.getCommitchainDataById(cid);
		}
		catch (Exception e1)
		{
			logger.error("doingTocheckCVD，上链数据获取失败", e1);
			saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_ERROR, "上链数据获取失败，" + cid);
			return;

		}
		if (cd == null)
		{
			logger.error("doingTocheckCVD，上链数据获取为null");
			saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_ERROR, "上链数据获取为null，" + cid);
			return;
		}
		
		try
		{
			String cd_counterparty = cd.getCounterparty();// 交易对家
			String cd_amountvalue = cd.getAmountvalue();// 交易金额
			String cd_amountcurrency = cd.getAmountcurrency();// 货币类型
			String cd_amountissuer = cd.getAmountissuer();// 货币发行方
			String cd_commitchainHash = cd.getCommitchainHash();// 上链的hash
			String cd_checkFlag = cd.getCheckFlag();// 上链的hash
			logger.info("doingTocheckCVD，cd_checkFlag"+cd_checkFlag);
			if(!StringUtils.isEmpty(cd_checkFlag)&&!CommitchainData.CHECK_FLAG_TODO.equals(cd_checkFlag))
			{
				logger.error("上链数据检查标志不是待检查!，cid:" + cid+",cd_checkFlag:"+cd_checkFlag);
				saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_ERROR, "上链数据检查标志不是待检查!，cid:" + cid+",cd_checkFlag:"+cd_checkFlag);
				return;
			}
			logger.info("doingTocheckCVD，begin compare");
			String cvd_counterparty = cvd.getCounterparty();// 交易对家
			String cvd_amountvalue = cvd.getAmountvalue();// 交易金额
			String cvd_amountcurrency = cvd.getAmountcurrency();// 货币类型
			String cvd_amountissuer = cvd.getAmountissuer();// 货币发行方
			String cd_hash = cvd.getHash();// hash
			boolean counterpartyCheck = checkStrEqual(cd_counterparty, cvd_counterparty);
			boolean amountvalueCheck = checkNumberEqual(cd_amountvalue, cvd_amountvalue);
			boolean amountcurrencyCheck = checkStrEqual(cd_amountcurrency, cvd_amountcurrency);
			boolean amountissuerCheck = checkStrEqual(cd_amountissuer, cvd_amountissuer);
			boolean hashCheck = checkStrEqual(cd_commitchainHash, cd_hash);
			logger.info("doingTocheckCVD，id"+cid+"counterpartyCheck"+counterpartyCheck+",amountvalueCheck"+amountvalueCheck+",amountcurrencyCheck"+amountcurrencyCheck+",amountissuerCheck"+amountissuerCheck+",hashCheck"+hashCheck);
			// 如果转账数据一样,
			if (counterpartyCheck && amountvalueCheck && amountcurrencyCheck && amountissuerCheck)
			{
				logger.info("data same");
				// 如果hash检查也一样
				if (hashCheck)
				{
					logger.info("hash same");
					saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_SUCCESS, "检查成功");
					cd.setCheckFlag(CommitchainData.CHECK_FLAG_SUCCESS);
					cd.setCheckDate(cvd.getDate());
					commitchainDataRepository.save(cd);
				}
				else
				{
					logger.info("hash not same");
					// 如果上链数据，commitchainHash不存在,有可能发送时候，异常或者断线，但是实际数据上链了
					if (StringUtils.isEmpty(cd_commitchainHash))
					{
						logger.info("hash empty");
						saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_SUCCESS, "检查成功，上链数据补上commitchainHash");

						cd.setCheckFlag(CommitchainData.CHECK_FLAG_SUCCESS);// 校验成功
						cd.setCheckDate(cvd.getDate());
						cd.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_SUCCESS);// 设置为发送成功
						cd.setCommitchainHash(cd_hash);
						cd.setCommitchainMsg("提交时候未记录到，由验证时候补全hash,之前cd_checkFlag:"+cd_checkFlag);
						cd.setCommitchainDate(cvd.getDate());
						commitchainDataRepository.save(cd);
					}
					else
					{
						logger.info("hash not empty");
						saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_ERROR, "检查错误，上链数据commitchainHash和检查hash不一致,之前cd_checkFlag:"+cd_checkFlag);
						cd.setCheckFlag(CommitchainData.CHECK_FLAG_ERROR);
						cd.setCheckDate(new Date());
						commitchainDataRepository.save(cd);
					}
				}

			}
			else
			{
				logger.info("data not empty");
				saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_ERROR, "检查错误，上链数据和检查的不一致");
				cd.setCheckFlag(CommitchainData.CHECK_FLAG_ERROR);
				cd.setCheckDate(new Date());
				commitchainDataRepository.save(cd);
			}
		}
		catch (Exception e)
		{
			cd.setCheckFlag(CommitchainData.CHECK_FLAG_ERROR);
			cd.setCheckDate(new Date());
			commitchainDataRepository.save(cd);
			saveCVDResult(cvd, CommitchainVerifyData.CHECKFLAG_ERROR, "检查异常");
			logger.error("检查异常", e);
			e.printStackTrace();
		}

	}

	private boolean checkNumberEqual(String cd_amountvalue, String cvd_amountvalue)
	{
		if (StringUtils.isEmpty(cd_amountvalue) || StringUtils.isEmpty(cvd_amountvalue))
		{
			return false;
		}
		BigDecimal cd = new BigDecimal(cd_amountvalue);
		BigDecimal cvd = new BigDecimal(cvd_amountvalue);
		return cd.compareTo(cvd) == 0;
	}

	private boolean checkStrEqual(String cd_str, String cvd_str)
	{
		if (StringUtils.isEmpty(cd_str) && StringUtils.isEmpty(cvd_str))
		{
			return true;
		}

		if (cd_str != null && cd_str.equals(cvd_str))
		{
			return true;
		}

		return false;
	}

	
	private void saveCVDResult(CommitchainVerifyData cvd, String flag, String msg)
	{
		cvd.setCheckflag(flag);
		cvd.setCheckmsg(msg);
		commitchainVerifyDataRepository.save(cvd);
	}

	@Override
	public Date getLastDate() throws Exception
	{
		CommitchainVerifyData cvd = commitchainVerifyDataRepository.getLastCVD();
		if(cvd!=null)
		{
			return cvd.getDate();
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public void commitChainFailCheck(Date lastDate) throws Exception
	{
		List<CommitchainData> list = commitchainDataRepository.getCdBycheckFlag(CommitchainData.CHECK_FLAG_TODO);
		if(list==null)
		{
			return;
		}
		logger.info("commitChainFailCheck,"+list.size());
		for(CommitchainData cd:list)
		{
			Date commitchainDate=cd.getCommitchainDate();
			//如果是待验证并且已经超过上链确认时间。
			if(lastDate.getTime()-commitchainDate.getTime()>commitchainFailTime*60*1000)
			{
				logger.info("待验证并且已经超过上链确认时间,lastDate:"+lastDate+",commitchainDate:"+commitchainDate+"id"+cd.getId()+",commitflag"+cd.getCommitchainFlag()+",commitmsg"+cd.getCommitchainMsg());
				cd.setCheckDate(new Date());
				cd.setCheckFlag(CommitchainData.CHECK_FLAG_FAIL);
				commitchainDataRepository.save(cd);
			}
		}
	}

}
