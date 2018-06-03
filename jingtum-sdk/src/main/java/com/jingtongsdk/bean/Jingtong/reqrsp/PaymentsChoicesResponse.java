package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;

public class PaymentsChoicesResponse extends JingtongBaseResponse
{
	private String client_id;//支付交易单号
	private Choices[] choices;//支付选择数组
	public String getClient_id()
	{
		return client_id;
	}
	public void setClient_id(String client_id)
	{
		this.client_id = client_id;
	}
	public Choices[] getChoices()
	{
		return choices;
	}
	public void setChoices(Choices[] choices)
	{
		this.choices = choices;
	}
	
	
}
