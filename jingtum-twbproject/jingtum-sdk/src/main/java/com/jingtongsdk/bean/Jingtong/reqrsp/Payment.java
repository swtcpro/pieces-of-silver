package com.jingtongsdk.bean.Jingtong.reqrsp;
//支付对象
public class Payment
{
	private String source;//发起账号
	private String destination;//目标账号
	private Amount amount;//支付金额对象，包含金额数量，货币类型，货币	发行方
	private String choice;//支付选择的key，可选
	private String[] memos;//支付的备注，String数组，可选
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getDestination()
	{
		return destination;
	}
	public void setDestination(String destination)
	{
		this.destination = destination;
	}
	public Amount getAmount()
	{
		return amount;
	}
	public void setAmount(Amount amount)
	{
		this.amount = amount;
	}
	public String getChoice()
	{
		return choice;
	}
	public void setChoice(String choice)
	{
		this.choice = choice;
	}
	public String[] getMemos()
	{
		return memos;
	}
	public void setMemos(String[] memos)
	{
		this.memos = memos;
	}
	
	
	
	
}


