package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.Exclude;
import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

//获得支付信息
public class PaymentInformationRequest extends JingtongBaseRequest
{
	@Exclude
	private String address;//支付用户的井通地址
	@Exclude
	private String id;//支付交易的hash或资源号
	
	
	public String requestUrl()
	{
		return "/v2/accounts/{:address}/payments/{:id}";
	}
	
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	
}
