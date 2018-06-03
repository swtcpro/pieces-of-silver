package com.jingtongsdk.bean;

public class HttpClientResponseBean
{
	private String sendState; // 发送状态
	private String msg; // 发送失败原因
	private int statusCode;// 响应码
	private String responseBody;// 返回数据

	public String getSendState()
	{
		return sendState;
	}

	public void setSendState(String sendState)
	{
		this.sendState = sendState;
	}


	public String getResponseBody()
	{
		return responseBody;
	}

	public void setResponseBody(String responseBody)
	{
		this.responseBody = responseBody;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}


}
