package com.twb.commondata.data;

import java.util.Date;

public class DistributeMqData
{
	private String id;

	private Date date; // 交易时间

	private String hash=""; // 交易hash
	private String type=""; // 类型
	private String result=""; // 结果
	private String fee=""; // 交易费用，井通计价
	private String memos=""; // 交易备注
	private String counterparty=""; // 交易对家
	private String amountvalue=""; // 交易金额
	private String amountcurrency=""; // 货币类型
	private String amountissuer=""; // 货币发行方
	
	private String distributeType="";// 1.socket数据分发 2.定时任务数据分发

	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getFee()
	{
		return fee;
	}

	public void setFee(String fee)
	{
		this.fee = fee;
	}

	public String getMemos()
	{
		return memos;
	}

	public void setMemos(String memos)
	{
		this.memos = memos;
	}

	public String getCounterparty()
	{
		return counterparty;
	}

	public void setCounterparty(String counterparty)
	{
		this.counterparty = counterparty;
	}

	public String getAmountvalue()
	{
		return amountvalue;
	}

	public void setAmountvalue(String amountvalue)
	{
		this.amountvalue = amountvalue;
	}

	public String getAmountcurrency()
	{
		return amountcurrency;
	}

	public void setAmountcurrency(String amountcurrency)
	{
		this.amountcurrency = amountcurrency;
	}

	public String getAmountissuer()
	{
		return amountissuer;
	}

	public void setAmountissuer(String amountissuer)
	{
		this.amountissuer = amountissuer;
	}

	public String getDistributeType()
	{
		return distributeType;
	}

	public void setDistributeType(String distributeType)
	{
		this.distributeType = distributeType;
	}

	@Override
	public String toString()
	{
		return "DistributeMqData [id=" + id + ", date=" + date + ", hash=" + hash + ", type=" + type + ", result="
				+ result + ", fee=" + fee + ", memos=" + memos + ", counterparty=" + counterparty + ", amountvalue="
				+ amountvalue + ", amountcurrency=" + amountcurrency + ", amountissuer=" + amountissuer
				+ ", distributeType=" + distributeType + "]";
	}
	
	
}
