package com.twb.service.impl;

import java.text.DecimalFormat;
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
import com.twb.commondata.data.CommitchainMqData;
import com.twb.commondata.utils.MQUtils;
import com.twb.entity.CommitchainData;
import com.twb.repository.CommitchainDataRepository;
import com.twb.service.CommitchainDataService;

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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<CommitchainData> getTodoCommitchainData() throws Exception
	{
		List<CommitchainData> list = commitchainDataRepository
				.getAllCommitchainDataByState(CommitchainData.COMMITCHAIN_FLAG_TODO);
		if (list == null)
		{
			list = new ArrayList();
		}
		return list;
	}
	
	@Override
	public List<CommitchainData> getDoingCommitchainData() throws Exception
	{
		List<CommitchainData> list = commitchainDataRepository
				.getAllCommitchainDataByState(CommitchainData.COMMITCHAIN_FLAG_DOING);

		if (list == null)
		{
			list = new ArrayList();
		}
		return list;
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

		if (amountvalue==null||!POSITIVE_NUMBER_PATTERN.matcher(amountvalue).matches())
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

	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public CommitchainData handlerTodoCommitchainData(CommitchainData commitchainData) throws Exception
	{
		
		if (commitchainData == null)
		{
			return null;
		}
		
		logger.info("handlerTodoCommitchainData"+commitchainData.getId());
		String checkMsg = checkTodoCommitchainData(commitchainData);
		//数据校验失败,ResponseFlag失败，CheckFlag已验证失败，结果准备反馈
		logger.info("handlerTodoCommitchainData,checkMsg "+checkMsg);
		if (!StringUtils.isEmpty(checkMsg))
		{
			commitchainData.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_CHECKFAIL);
			commitchainData.setCommitchainMsg(checkMsg);
			commitchainData.setBusinessFlag(CommitchainData.BUSINESS_FLAG_TODO);
			commitchainData.setCheckFlag(CommitchainData.CHECK_FLAG_FAIL);
			commitchainData.setCheckDate(new Date());
			commitchainDataRepository.save(commitchainData);
			return commitchainData;
		}
		
		//准备数据
		prepareCommitchainData(commitchainData);
		commitchainData.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_DOING);
		commitchainDataRepository.save(commitchainData);
		
		
		return commitchainData;
	}

	/**
	 * 
	 * @Title: prepareCommitchainData   
	 * @Description: 准备数据
	 * @param: @param commitchainData      
	 * @return: void      
	 * @throws
	 */
	private void prepareCommitchainData(CommitchainData commitchainData)
	{
		
		String memos = commitchainData.getMemos();
		logger.info("begin"+memos);
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
		logger.info("end"+memos);

	}

	/**
	 * 数据上链
	 */
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
		payment.setMemos(new String[]
		{ memos });
		ptr.setPayment(payment);

		try
		{
			logger.info("上链1");
			PaymentsTransferResponse jtr = (PaymentsTransferResponse) JingtongRequestUtils.sendRequest(ptr);
			if (jtr.isSuccess() && "tesSUCCESS".equals(jtr.getResult()))
			{
				commitchainData.setCommitchainDate(new Date());
				commitchainData.setCommitchainHash(jtr.getHash());
				commitchainData.setCommitchainMsg(jtr.getMessage());
				commitchainData.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_SUCCESS);
			}
			else
			{

				String msg = jtr.getMessage();
				if (StringUtils.isEmpty(msg))
				{
					msg = jtr.getResult();
				}
				commitchainData.setCommitchainDate(new Date());
				commitchainData.setCommitchainMsg(msg);
				commitchainData.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_FAIL);

			}
		}
		catch (Exception e)
		{
			logger.error("error.." + e.toString() + "," + Arrays.toString(e.getStackTrace()));
			try
			{
				logger.info("上链2");
				// 异常，用同一Client_id再次支付，如果之前支付过，这次会失败，并且提示client_id已使用。
				// 再试一次
				PaymentsTransferResponse jtr = (PaymentsTransferResponse) JingtongRequestUtils.sendRequest(ptr);
				if (jtr.isSuccess() && "tesSUCCESS".equals(jtr.getResult()))
				{
					commitchainData.setCommitchainDate(new Date());
					commitchainData.setCommitchainHash(jtr.getHash());
					commitchainData.setCommitchainMsg(jtr.getMessage());
					commitchainData.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_SUCCESS);
				}
				else
				{
					String msg = jtr.getMessage();
					if (StringUtils.isEmpty(msg))
					{
						msg = jtr.getResult();
					}
					commitchainData.setCommitchainDate(new Date());
					commitchainData.setCommitchainMsg(msg);
					commitchainData.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_FAIL);
				}
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				logger.error("error.." + e1.toString() + "," + Arrays.toString(e1.getStackTrace()));
				commitchainData.setCommitchainDate(new Date());
				commitchainData.setCommitchainMsg(e1.toString());
				commitchainData.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_FAIL);

			}

		}
		
		//上链数据待确认
		commitchainData.setCheckFlag(CommitchainData.CHECK_FLAG_TODO);
		commitchainDataRepository.save(commitchainData);
	}

	@Transactional(rollbackFor = Exception.class)
	public CommitchainData savaCdFromMq(CommitchainMqData cmd) throws Exception
	{
		
		String counterparty = cmd.getCounterparty();
		double amountvalue = cmd.getAmountvalue();
		String amountcurrency = cmd.getAmountcurrency();
		String amountissuer = cmd.getAmountissuer();
		Map memos = cmd.getMemos();
		String businessid = cmd.getBusinessid();

		String businessTopic = cmd.getBusinessTopic();// 结果反馈业务系统MQ主题
		String businessTag = cmd.getBusinessTag();// 结果反馈业务系统TAG

		// 业务id，唯一索引
		if (StringUtils.isEmpty(businessid))
		{
			logger.error("businessid is empty:" + cmd);
			return null;
		}

		if(amountvalue<=0)
		{
			logger.error("amountvalue<=0:" + cmd);
			return null;
		}
		CommitchainData cd = new CommitchainData();
		cd.setCounterparty(counterparty);
		DecimalFormat decimalFormat = new DecimalFormat("##########.######");//格式化设置 
		cd.setAmountvalue(decimalFormat.format(amountvalue));
		cd.setAmountcurrency(amountcurrency);
		cd.setAmountissuer(amountissuer);
		cd.setMemos(MQUtils.toJson(memos));
		cd.setBusinessId(businessid);

		cd.setCommitchainFlag(CommitchainData.COMMITCHAIN_FLAG_TODO);
		if (!StringUtils.isEmpty(businessTopic))
		{
			cd.setBusinessTopic(businessTopic);
			cd.setBusinessTag(businessTag);
			cd.setBusinessFlag(CommitchainData.BUSINESS_FLAG_TODO);
		}
		else
		{
			cd.setBusinessFlag(CommitchainData.BUSINESS_FLAG_NONE);
		}

		commitchainDataRepository.save(cd);
		return cd;
	}

}
