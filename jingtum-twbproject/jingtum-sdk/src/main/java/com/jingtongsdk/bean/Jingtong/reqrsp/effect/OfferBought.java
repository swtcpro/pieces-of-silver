package com.jingtongsdk.bean.Jingtong.reqrsp.effect;

import com.jingtongsdk.bean.Jingtong.reqrsp.Amount;
import com.jingtongsdk.bean.Jingtong.reqrsp.Effect;

//5）offer_bought，挂单买到/卖出，成交的单子信息；交易提示信息建议：您以XXX价格买了/卖了XXX卖了/买了XXX；其中包含的
//信息有：
public class OfferBought extends Effect
{

	private Counterparty counterparty;
	private Amount got;
	private Amount paid;
	private String type;
	private String price;
	private String pair;
	private String amount;
	public Counterparty getCounterparty()
	{
		return counterparty;
	}
	public void setCounterparty(Counterparty counterparty)
	{
		this.counterparty = counterparty;
	}
	public Amount getGot()
	{
		return got;
	}
	public void setGot(Amount got)
	{
		this.got = got;
	}
	public Amount getPaid()
	{
		return paid;
	}
	public void setPaid(Amount paid)
	{
		this.paid = paid;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getPrice()
	{
		return price;
	}
	public void setPrice(String price)
	{
		this.price = price;
	}
	public String getPair()
	{
		return pair;
	}
	public void setPair(String pair)
	{
		this.pair = pair;
	}
	public String getAmount()
	{
		return amount;
	}
	public void setAmount(String amount)
	{
		this.amount = amount;
	}
	
}
