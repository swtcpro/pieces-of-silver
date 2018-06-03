package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;

public class PaymentsTransferResponse extends JingtongBaseResponse
{
	private String client_id;//支付交易单号
	private String hash;//支付交易Hash
	private String result;//支付交易的服务器结果，tesSUCCESS表示成功，其他类型	详见错误信息
	private Integer date;//交易时间，UNIXTIME
	private String fee;	//交易费用，井通计价
	public String getClient_id()
	{
		return client_id;
	}
	public void setClient_id(String client_id)
	{
		this.client_id = client_id;
	}
	public String getHash()
	{
		return hash;
	}
	public void setHash(String hash)
	{
		this.hash = hash;
	}
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
	public Integer getDate()
	{
		return date;
	}
	public void setDate(Integer date)
	{
		this.date = date;
	}
	public String getFee()
	{
		return fee;
	}
	public void setFee(String fee)
	{
		this.fee = fee;
	}

	
	
}
