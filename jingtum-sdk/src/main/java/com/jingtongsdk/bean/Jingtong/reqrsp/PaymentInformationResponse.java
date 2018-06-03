package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;

//获得支付信息
public class PaymentInformationResponse extends JingtongBaseResponse
{
	
	private Integer date;
	private String hash;
	private String type;
	private String fee;
	private String result;
	private String[] memos;
	private String counterparty;
	private Amount amount;
	private Effect[] effects;
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
