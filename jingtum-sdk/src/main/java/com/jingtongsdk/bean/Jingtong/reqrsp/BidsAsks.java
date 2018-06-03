package com.jingtongsdk.bean.Jingtong.reqrsp;

public class BidsAsks
{
	private Double price;//该档的价格
	private Double funded;//实际中用户可以成交的金额
	private String order_maker;//挂单用户
	private String sequence;//交易序号
	private String passive;//交易是否是被动交易
	private String sell;//是否卖单
	public Double getPrice()
	{
		return price;
	}
	public void setPrice(Double price)
	{
		this.price = price;
	}
	
	public Double getFunded()
	{
		return funded;
	}
	public void setFunded(Double funded)
	{
		this.funded = funded;
	}
	public String getOrder_maker()
	{
		return order_maker;
	}
	public void setOrder_maker(String order_maker)
	{
		this.order_maker = order_maker;
	}
	public String getSequence()
	{
		return sequence;
	}
	public void setSequence(String sequence)
	{
		this.sequence = sequence;
	}
	public String getPassive()
	{
		return passive;
	}
	public void setPassive(String passive)
	{
		this.passive = passive;
	}
	public String getSell()
	{
		return sell;
	}
	public void setSell(String sell)
	{
		this.sell = sell;
	}
	
	
}
