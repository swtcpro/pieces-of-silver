package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.Exclude;
import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

/**
 * 
 * @Title:  OrdersSubmitRequest.java   
 * @Package com.jingtongsdk.bean.Jingtong.reqrsp   
 * @Description:    提交挂单 
 * @author: 田文彬     
 * @date:   2018年4月4日 下午9:37:37   
 * @version V1.0
 */
public class OrdersSubmitRequest  extends JingtongBaseRequest
{
	@Exclude
	private String address;//用户的井通地址
	
	private String secret;//用户的钱包私钥
	private Order order;//提交的挂单
	
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getSecret()
	{
		return secret;
	}
	public void setSecret(String secret)
	{
		this.secret = secret;
	}
	public Order getOrder()
	{
		return order;
	}
	public void setOrder(Order order)
	{
		this.order = order;
	}
	@Override
	public String requestUrl()
	{
		return "/v2/accounts/{:address}/orders";
	}
	@Override
	public String requestType()
	{
		
		return JingtongRequstConstants.POST_TYPE;
	}
	
	
}
