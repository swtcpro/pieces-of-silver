package com.jingtongsdk.bean.Jingtong.reqrsp.effect;

import com.jingtongsdk.bean.Jingtong.reqrsp.Effect;

//offer_cancelled，被关联交易取消单子，交易单子被取消；交易提示信息建议：由于缺少金额，单子XXX被取消；
public class OfferCancelled extends Effect
{
	private String type;
	private String pair;
	private String amount;
	private String price;
	private Integer seq;
	private String hash;
	private Boolean deleted;
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
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
	public String getPrice()
	{
		return price;
	}
	public void setPrice(String price)
	{
		this.price = price;
	}
	public Integer getSeq()
	{
		return seq;
	}
	public void setSeq(Integer seq)
	{
		this.seq = seq;
	}
	public String getHash()
	{
		return hash;
	}
	public void setHash(String hash)
	{
		this.hash = hash;
	}
	public Boolean getDeleted()
	{
		return deleted;
	}
	public void setDeleted(Boolean deleted)
	{
		this.deleted = deleted;
	}
	
}
