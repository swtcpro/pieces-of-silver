package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.Exclude;
import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

//获取挂单信息
public class OrdersInfoRequest  extends JingtongBaseRequest
{
	@Exclude
	private String address;//用户的井通地址
	
	@Exclude
	private String hash;//交易Hash

	@Override
	public String requestUrl()
	{
		return "/v2/accounts/{:address}/orders/{:hash}";
	}
	@Override
	public String requestType()
	{
		
		return JingtongRequstConstants.GET_TYPE;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getHash()
	{
		return hash;
	}
	public void setHash(String hash)
	{
		this.hash = hash;
	}
	
	
}
