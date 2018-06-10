package com.twb.commondata.data;

import java.util.HashMap;
import java.util.Map;

public class CommitchainMqData
{
	private String counterparty="";
	private double amountvalue;
	private String amountcurrency =""; 
	private String amountissuer="";
	private Map memos= new HashMap();
	private String businessid="";
	private String businessTopic="";
	private String businessTag="";
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
	public Map getMemos()
	{
		return memos;
	}
	public void setMemos(Map memos)
	{
		this.memos = memos;
	}
	public String getBusinessid()
	{
		return businessid;
	}
	public void setBusinessid(String businessid)
	{
		this.businessid = businessid;
	}
	public String getBusinessTopic()
	{
		return businessTopic;
	}
	public void setBusinessTopic(String businessTopic)
	{
		this.businessTopic = businessTopic;
	}
	public String getBusinessTag()
	{
		return businessTag;
	}
	public void setBusinessTag(String businessTag)
	{
		this.businessTag = businessTag;
	}

	public double getAmountvalue()
	{
		return amountvalue;
	}
	public void setAmountvalue(double amountvalue)
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
	@Override
	public String toString()
	{
		return "CommitchainMqData [counterparty=" + counterparty + ", amountvalue=" + amountvalue + ", amountcurrency="
				+ amountcurrency + ", amountissuer=" + amountissuer + ", memos=" + memos + ", businessid=" + businessid
				+ ", businessTopic=" + businessTopic + ", businessTag=" + businessTag + "]";
	}
	
	
}
