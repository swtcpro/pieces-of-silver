package com.jingtongsdk.bean.Jingtong.reqrsp.effect;

import com.jingtongsdk.bean.Jingtong.reqrsp.Effect;

//4）offer_created，交易单子创建；交易提示信息建议：您创建了一个买/卖单，以XXX交易XXX；其中包含的信息有：
public class OfferCreated extends Effect
{
	private String type;
	private Integer seq;
	private String price;
	private String pair;
	private String amount;
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
