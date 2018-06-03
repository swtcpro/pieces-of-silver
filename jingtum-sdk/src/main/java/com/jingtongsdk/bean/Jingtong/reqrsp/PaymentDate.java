package com.jingtongsdk.bean.Jingtong.reqrsp;
//支付记录
public class PaymentDate
{
	private Integer date;//支付时间，UNIXTIME时间
	private String hash;//支付hash
	private String type;//支付类型，sent或received
	private String fee;//支付费用
	private String result;//支付的服务器结果
	private String[] memos;//支付的备注，String数组
	private String counterparty;//交易对家
	private Amount amount;//交易金额
	private Effect[] effects;//支付的效果
	public Integer getDate()
	{
		return date;
	}
	public void setDate(Integer date)
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
	public String getFee()
	{
		return fee;
	}
	public void setFee(String fee)
	{
		this.fee = fee;
	}
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
	public String[] getMemos()
	{
		return memos;
	}
	public void setMemos(String[] memos)
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
	public Amount getAmount()
	{
		return amount;
	}
	public void setAmount(Amount amount)
	{
		this.amount = amount;
	}
	public Effect[] getEffects()
	{
		return effects;
	}
	public void setEffects(Effect[] effects)
	{
		this.effects = effects;
	}
	
	
	
	
	
	
	
}


