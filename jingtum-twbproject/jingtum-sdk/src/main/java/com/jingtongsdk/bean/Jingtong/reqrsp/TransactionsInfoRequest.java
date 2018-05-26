package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

//查询交易信息
public class TransactionsInfoRequest extends JingtongBaseRequest
{
	private String address;//井通钱包地址
	private String id; //交易资源号或者交易hash

	public String requestUrl()
	{
		return "/v2/accounts/{:address}/transactions/{:id}";
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
