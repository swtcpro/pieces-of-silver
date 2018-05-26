package com.twb.data;

import com.jingtongsdk.bean.Jingtong.reqrsp.Transaction;

public class SubscribeData
{
	private String account;
	private boolean success;
	private String type;
	private Transaction transaction;
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Transaction getTransaction()
	{
		return transaction;
	}
	public void setTransaction(Transaction transaction)
	{
		this.transaction = transaction;
	}
	public boolean isSuccess()
	{
		return success;
	}
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	
}
