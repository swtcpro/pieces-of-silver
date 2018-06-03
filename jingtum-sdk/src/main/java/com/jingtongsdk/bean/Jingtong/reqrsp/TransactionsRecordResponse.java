package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;
import com.jingtongsdk.utils.JingtongRequstConstants;

public class TransactionsRecordResponse extends JingtongBaseResponse
{
	private Marker marker;//交易记录标记
	private Transaction[] transactions; //具体的交易信息数组
	public Marker getMarker()
	{
		return marker;
	}
	public void setMarker(Marker marker)
	{
		this.marker = marker;
	}
	public Transaction[] getTransactions()
	{
		return transactions;
	}
	public void setTransactions(Transaction[] transactions)
	{
		this.transactions = transactions;
	}

	
}
