package org.commondata.data;

import java.util.Date;

public class CommitChainRespMqData
{
	
	public static final String CHECK_FLAG_SUCCESS = "2";// 2.已验证上链成功
	public static final String CHECK_FLAG_FAIL = "3";// 3.已验证上链失败

	private int id;
	
	private String counterparty="";
	private String amountvalue="";
	private String amountcurrency =""; 
	private String amountissuer="";
	private String memos= "";
	private String businessid="";
	
	private String commitchainMsg = "";//上链成功失败等信息
	private String commitchainHash = "";//上链的hash
	
	private Date checkchainDate;//确认上链的时间,如果上链成功就是上链时间
	private String checkFlag = "";//1.待验证 2.已验证成功 3.已验证失败4.已验证异常
	
	public String getCounterparty()
	{
		return counterparty;
	}
	public void setCounterparty(String counterparty)
	{
		this.counterparty = counterparty;
	}
	public String getAmountissuer()
	{
		return amountissuer;
	}
	public void setAmountissuer(String amountissuer)
	{
		this.amountissuer = amountissuer;
	}

	
	public String getBusinessid()
	{
		return businessid;
	}
	public void setBusinessid(String businessid)
	{
		this.businessid = businessid;
	}
	
	public String getAmountcurrency()
	{
		return amountcurrency;
	}
	public void setAmountcurrency(String amountcurrency)
	{
		this.amountcurrency = amountcurrency;
	}
	public String getAmountvalue()
	{
		return amountvalue;
	}
	public void setAmountvalue(String amountvalue)
	{
		this.amountvalue = amountvalue;
	}
	public String getCommitchainMsg()
	{
		return commitchainMsg;
	}
	public void setCommitchainMsg(String commitchainMsg)
	{
		this.commitchainMsg = commitchainMsg;
	}
	public String getCommitchainHash()
	{
		return commitchainHash;
	}
	public void setCommitchainHash(String commitchainHash)
	{
		this.commitchainHash = commitchainHash;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public Date getCheckchainDate()
	{
		return checkchainDate;
	}
	public void setCheckchainDate(Date checkchainDate)
	{
		this.checkchainDate = checkchainDate;
	}
	public String getCheckFlag()
	{
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag)
	{
		this.checkFlag = checkFlag;
	}
	public String getMemos()
	{
		return memos;
	}
	public void setMemos(String memos)
	{
		this.memos = memos;
	}
	
	
}
