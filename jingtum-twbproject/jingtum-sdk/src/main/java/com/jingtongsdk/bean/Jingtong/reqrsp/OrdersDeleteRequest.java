package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.Exclude;
import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;


public class OrdersDeleteRequest  extends JingtongBaseRequest
{
	private String address;//挂单方的井通地址
	
	private String order;//订单的序号

	@Override
	public String requestUrl()
	{
		return "/v2/accounts/{:address}/orders/{:order}";
	}
	@Override
	public String requestType()
	{
		
		return JingtongRequstConstants.DELETE_TYPE;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getOrder()
	{
		return order;
	}
	public void setOrder(String order)
	{
		this.order = order;
	}
	
	
	
}
