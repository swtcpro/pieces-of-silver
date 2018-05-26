package com.twb.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.jingtongsdk.bean.Jingtong.reqrsp.Amount;
import com.jingtongsdk.bean.Jingtong.reqrsp.Payment;
import com.jingtongsdk.bean.Jingtong.reqrsp.PaymentsTransferRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.PaymentsTransferResponse;
import com.jingtongsdk.utils.JingtongRequestUtils;
import com.twb.entity.CommitchainData;
import com.twb.repository.CommitchainDataRepository;
import com.twb.service.CommitchainDataService;
import com.twb.utils.CommitchainDataQueue;

@Service
public class CommitchainDataServiceImp implements CommitchainDataService
{

	private static final Logger logger = LoggerFactory.getLogger(CommitchainDataServiceImp.class);

	@Autowired
	private CommitchainDataRepository commitchainDataRepository;

	@Value("${client_id_pre}")
	private String client_id_pre;

	@Value("${address}")
	private String address;

	@Value("${secret}")
	private String secret;

	public static Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("^[+]{0,1}(\\d+)$|^[+]{0,1}(\\d+\\.\\d+)$");

	@Transactional(rollbackFor = Exception.class)
	public List<CommitchainData> getTodoCommitchainData() throws Exception
	{
		List<CommitchainData> list = commitchainDataRepository
				.getAllCommitchainDataByState(CommitchainData.RESPONSE_FLAG_TODO);
		List todoList = new ArrayList();
		if (list != null && !list.isEmpty())
		{
			for (CommitchainData cd : list)
			{
				String checkMsg = checkTodoCommitchainData(cd);
				if (!StringUtils.isEmpty(checkMsg))
				{
					cd.setResponseFlag(CommitchainData.RESPONSE_FLAG_CHECKFAIL);
					cd.setResponseMsg(checkMsg);

				}
				else
				{
					todoList.add(cd);
				}
				commitchainDataRepository.save(cd);
			}
		}

		return todoList;
	}

	/**
	 * 
	 * @Title: checkTodoCommitchainData   
	 * @Description: 检查数据
	 * @param: @param cd
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String checkTodoCommitchainData(CommitchainData cd)
	{
		String counterparty = cd.getCounterparty();
		String amountvalue = cd.getAmountvalue();
		String amountcurrency = cd.getAmountcurrency();
		String amountissuer = cd.getAmountissuer();
		String memos = cd.getMemos();

		StringBuffer sb = new StringBuffer();

		if (StringUtils.isEmpty(counterparty))
		{
			sb.append("交易对家为空 ");
		}

		if (!POSITIVE_NUMBER_PATTERN.matcher(amountvalue).matches())
		{
			sb.append("金额错误 ");
		}
		if (StringUtils.isEmpty(amountcurrency))
		{
			sb.append("货币为空 ");
		}
		if (StringUtils.isEmpty(amountissuer) && !"SWT".equals(amountcurrency))
		{
			sb.append("货币发行方为空 ");
		}

		if (!StringUtils.isEmpty(memos))
		{
			try
			{
				Map maps = (Map) JSON.parse(memos);
				if (maps.containsKey("id"))
				{
					sb.append("memos包含了id ");
				}

			}
			catch (Exception e)
			{
				logger.info("memos解析错误:" + memos, e);
				e.printStackTrace();
				sb.append("memos解析错误 ");
			}
		}

		return sb.toString();
	}

	public List<CommitchainData> getDoingCommitchainData() throws Exception
	{
		List<CommitchainData> list = commitchainDataRepository
				.getAllCommitchainDataByState(CommitchainData.RESPONSE_FLAG_DOING);

		if (list == null)
		{
			list = new ArrayList();
		}
		return list;
	}

	@Override
	public List handlerTodoCommitchainData(List<CommitchainData> list) throws Exception
	{
		if (list == null)
		{
			list = new ArrayList();
		}
		for (CommitchainData commitchainData : list)
		{
			prepareCommitchainData(commitchainData);
			commitchainData.setResponseFlag(CommitchainData.RESPONSE_FLAG_DOING);
			commitchainDataRepository.save(commitchainData);
		}
		return list;
	}

	private void prepareCommitchainData(CommitchainData commitchainData)
	{
		String memos = commitchainData.getMemos();
		if (StringUtils.isEmpty(memos))
		{
			Map map = new HashMap();
			map.put("id", commitchainData.getId());
			String str = JSON.toJSONString(map);
			commitchainData.setMemos(str);
		}
		else
		{
			Map maps = (Map) JSON.parse(memos);
			maps.put("id", commitchainData.getId());
			String str = JSON.toJSONString(maps);
			commitchainData.setMemos(str);
		}

	}

	// @Override
	// public void handlerDoingCommitchainData() throws Exception
	// {
	// if (list == null)
	// {
	// list = new ArrayList();
	// }
	// for (CommitchainData commitchainData : list)
	// {
	// CommitchainDataQueue.add(commitchainData);
	// }
	//
	// }

	@Transactional(rollbackFor = Exception.class)
	public void doingCommitchainData(CommitchainData commitchainData) throws Exception
	{
		logger.info("doingCommitchainData start " + commitchainData.getId());
		String currency = commitchainData.getAmountcurrency();
		String issuer = commitchainData.getAmountissuer();
		String value = commitchainData.getAmountvalue();

		String memos = commitchainData.getMemos();
		String destination = commitchainData.getCounterparty();

		if (memos == null)
		{
			memos = "";
		}

		PaymentsTransferRequest ptr = new PaymentsTransferRequest();
		ptr.setSource_address(address);
		ptr.setSecret(secret);
		ptr.setClient_id(client_id_pre + commitchainData.getId());

		Payment payment = new Payment();
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setValue(value);
		amount.setIssuer(issuer);
		payment.setAmount(amount);
		payment.setDestination(destination);
		payment.setSource(address);
		payment.setMemos(new String[]{ memos });
		ptr.setPayment(payment);

		try
		{
			PaymentsTransferResponse jtr = (PaymentsTransferResponse) JingtongRequestUtils.sendRequest(ptr);
			if (jtr.isSuccess() && "tesSUCCESS".equals(jtr.getResult()))
			{
				commitchainData.setResponseData(new Date());
				commitchainData.setResponseHash(jtr.getHash());
				commitchainData.setResponseMsg(jtr.getMessage());
				commitchainData.setResponseFlag(CommitchainData.RESPONSE_FLAG_SUCCESS);
			}
			else
			{

				String msg = jtr.getMessage();
				if (StringUtils.isEmpty(msg))
				{
					msg = jtr.getResult();
				}
				commitchainData.setResponseData(new Date());
				commitchainData.setResponseMsg(msg);
				commitchainData.setResponseFlag(CommitchainData.RESPONSE_FLAG_FAIL);

			}
		}
		catch (Exception e)
		{
			logger.error("error.." + e.toString() + "," + Arrays.toString(e.getStackTrace()));
			try
			{
				// 异常，用同一Client_id再次支付，如果之前支付过，这次会失败，并且提示client_id已使用。
				ptr.setClient_id(client_id_pre + commitchainData.getId());
				// 再试一次
				PaymentsTransferResponse jtr = (PaymentsTransferResponse) JingtongRequestUtils.sendRequest(ptr);
				if (jtr.isSuccess() && "tesSUCCESS".equals(jtr.getResult()))
				{
					commitchainData.setResponseData(new Date());
					commitchainData.setResponseHash(jtr.getHash());
					commitchainData.setResponseMsg(jtr.getMessage());
					commitchainData.setResponseFlag(CommitchainData.RESPONSE_FLAG_SUCCESS);
				}
				else
				{
					String msg = jtr.getMessage();
					if (StringUtils.isEmpty(msg))
					{
						msg = jtr.getResult();
					}
					commitchainData.setResponseData(new Date());
					commitchainData.setResponseMsg(msg);
					commitchainData.setResponseFlag(CommitchainData.RESPONSE_FLAG_FAIL);
				}
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				logger.error("error.." + e1.toString() + "," + Arrays.toString(e1.getStackTrace()));
				commitchainData.setResponseData(new Date());
				commitchainData.setResponseMsg(e1.toString());
				commitchainData.setResponseFlag(CommitchainData.RESPONSE_FLAG_FAIL);

			}

		}
		commitchainDataRepository.save(commitchainData);
	}

}
