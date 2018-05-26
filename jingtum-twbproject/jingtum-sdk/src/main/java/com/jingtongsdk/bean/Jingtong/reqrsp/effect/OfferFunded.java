package com.jingtongsdk.bean.Jingtong.reqrsp.effect;

import com.jingtongsdk.bean.Jingtong.reqrsp.Amount;
import com.jingtongsdk.bean.Jingtong.reqrsp.Effect;
//offer_funded,交易实际成交；交易提示信息建议：交易成交，您以XXX价格买了/卖了XXX卖了/买了XXX，价格是XXX；其中
//包含的信息有：
public class OfferFunded extends Effect
{
	private Counterparty counterparty;
	private Amount got;
	private Amount paid;
	private String type;
	private Integer seq;
	private String price;
	private boolean deleted;
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
	public boolean isDeleted()
	{
		return deleted;
	}
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
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
