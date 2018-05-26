package com.jingtongsdk.bean.Jingtong.reqrsp;

//钱包
public class Wallet
{
	private String secret;//井通钱包私钥
	private String address;//井通钱包地址
	public String getSecret()
	{
		return secret;
	}
	public void setSecret(String secret)
	{
		this.secret = secret;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	
}
