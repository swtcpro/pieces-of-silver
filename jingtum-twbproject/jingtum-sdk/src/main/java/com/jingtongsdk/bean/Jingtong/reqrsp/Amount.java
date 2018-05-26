package com.jingtongsdk.bean.Jingtong.reqrsp;

public class Amount
{
	private String value;//金额数量
	private String currency;//货币类型
	private String issuer;//货币发行方
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	public String getCurrency()
	{
		return currency;
	}
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
	public String getIssuer()
	{
		return issuer;
	}
	public void setIssuer(String issuer)
	{
		this.issuer = issuer;
	}
	
	
}
