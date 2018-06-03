package com.jingtongsdk.bean.Jingtong.reqrsp;
//提交的挂单
public class Order
{
	private String type;//挂单的类型，sell或buy
	private String pair;//交易的货币对(兼容swt:issuer)
	private String amount;//挂单的数量
	private String price;//挂单的价格
	private String sequence;
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
	public String getSequence()
	{
		return sequence;
	}
	public void setSequence(String sequence)
	{
		this.sequence = sequence;
	}
	
	
}
