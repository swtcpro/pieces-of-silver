package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.Exclude;
import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

/**
 * 
 * @Title:  GetBalancesRequest.java   
 * @Package com.jingtongsdk.bean.Jingtong.reqrsp   
 * @Description:   查询余额请求
 * @author: 田文彬     
 * @date:   2018年4月4日 下午4:27:02   
 * @version V1.0
 */
public class BalancesRequest extends JingtongBaseRequest
{
	@Exclude
	private String address;//井通钱包地址
	
	private String currency;//指定返回对应货币的余额
	
	private String issuer;//指定返回对应银关发行的货币
	
	public String requestUrl()
	{
		
		return "/v2/accounts/{:address}/balances";
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
