package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.Exclude;
import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

//转账支付
public class PaymentsTransferRequest extends JingtongBaseRequest
{
	@Exclude
	private String source_address;//支付方的井通地址
	
	private String secret;//支付方的钱包私钥
	private String client_id;//此次请求的交易单号，交易单需要唯一
	private Payment payment;//支付对象
	
	
	public String requestUrl()
	{
		return "/v2/accounts/{:source_address}/payments";
	}
	
	public String requestType()
	{
		return JingtongRequstConstants.POST_TYPE;
	}

	public String getSource_address()
	{
		return source_address;
	}

	public void setSource_address(String source_address)
	{
		this.source_address = source_address;
	}

	public String getSecret()
	{
		return secret;
	}

	public void setSecret(String secret)
	{
		this.secret = secret;
	}

	public String getClient_id()
	{
		return client_id;
	}

	public void setClient_id(String client_id)
	{
		this.client_id = client_id;
	}

	public Payment getPayment()
	{
		return payment;
	}

	public void setPayment(Payment payment)
	{
		this.payment = payment;
	}
	
	
}
