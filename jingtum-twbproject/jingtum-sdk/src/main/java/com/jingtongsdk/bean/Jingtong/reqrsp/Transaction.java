package com.jingtongsdk.bean.Jingtong.reqrsp;

public class Transaction
{
	private Integer date;//交易时间，UNIXTIME
	private String hash;//交易hash
	private String type;//交易类型
	private String fee;//交易费用，井通计价
	private String result;//交易结果
	private String[] memos;//交易备注
	private String counterparty;//交易对家
	private TransactionAmount amount;//交易金额
	private String offertype;//挂单类型，sell或者buy，挂单交易才有
	private String pair;//交易的货币对，挂单交易才有
	private String price;//挂单的价格，挂单交易才有	
	private Integer seq;
	private Effect[] effects;//交易效果，详见下面分析
	
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
	public String getCounterparty()
	{
		return counterparty;
	}
	public void setCounterparty(String counterparty)
	{
		this.counterparty = counterparty;
	}
	
	public String[] getMemos()
	{
		return memos;
	}
	public void setMemos(String[] memos)
	{
		this.memos = memos;
	}
	public String getOffertype()
	{
		return offertype;
	}
	public void setOffertype(String offertype)
	{
		this.offertype = offertype;
	}
	public String getPair()
	{
		return pair;
	}
	public void setPair(String pair)
	{
		this.pair = pair;
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
	
	public Effect[] getEffects()
	{
		return effects;
	}
	public void setEffects(Effect[] effects)
	{
		this.effects = effects;
	}
	public TransactionAmount getAmount()
	{
		return amount;
	}
	public void setAmount(TransactionAmount amount)
	{
		this.amount = amount;
	}
	
}
