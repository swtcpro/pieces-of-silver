package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;


public class OrdersSubmitResponse  extends JingtongBaseResponse
{
	private String hash;//交易hash
	
	private String result;//交易结果
	private String fee;//交易费用，井通计价
	private Integer sequence;//交易单子序号
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
	public String getFee()
	{
		return fee;
	}
	public void setFee(String fee)
	{
		this.fee = fee;
	}
	public Integer getSequence()
	{
		return sequence;
	}
	public void setSequence(Integer sequence)
	{
		this.sequence = sequence;
	}
	
	
	
	
}
