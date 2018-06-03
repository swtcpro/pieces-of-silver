package com.jingtongsdk.bean.Jingtong.reqrsp.effect;

import com.jingtongsdk.bean.Jingtong.reqrsp.Amount;
import com.jingtongsdk.bean.Jingtong.reqrsp.Effect;

//offer_partially_funded，交易部分成交；交易提示信息建议：交易部分成交，您以XXX价格买了/卖了XXX卖了/买了XXX，价格
//是XXX，剩余挂单由于金额不足被取消（可选，根据cancelled），还剩XXX单子（可选，根据remaining）；其中包含的信息有：
public class OfferPartiallyFunded extends Effect
{
	private Counterparty counterparty;
	private boolean remaining;
	private Amount got;
	private Amount paid;
	private String type;
	private Integer seq;
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
	public boolean isRemaining()
	{
		return remaining;
	}
	public void setRemaining(boolean remaining)
	{
		this.remaining = remaining;
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
	public Integer getSeq()
	{
		return seq;
	}
	public void setSeq(Integer seq)
	{
		this.seq = seq;
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
