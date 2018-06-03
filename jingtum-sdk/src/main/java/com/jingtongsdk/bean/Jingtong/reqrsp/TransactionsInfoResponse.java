package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;

public class TransactionsInfoResponse extends JingtongBaseResponse
{
//	private Marker marker;//交易记录标记
	private Transaction transaction;
	
	public Transaction getTransaction()
	{
		return transaction;
	}
	public void setTransaction(Transaction transaction)
	{
		this.transaction = transaction;
	}
	
}
