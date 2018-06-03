package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.Exclude;
import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

/**
 * 
 * @Title:  OrdersListRequest.java   
 * @Package com.jingtongsdk.bean.Jingtong.reqrsp   
 * @Description:    获取挂单列表 
 * @author: 田文彬     
 * @date:   2018年4月4日 下午9:48:34   
 * @version V1.0
 */
public class OrdersListRequest  extends JingtongBaseRequest
{
	@Exclude
	private String address;//用户的井通地址
	

	@Override
	public String requestUrl()
	{
		return "/v2/accounts/{:address}/orders";
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
	
	
}
