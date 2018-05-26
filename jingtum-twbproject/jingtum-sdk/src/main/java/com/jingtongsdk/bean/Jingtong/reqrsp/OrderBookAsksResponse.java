package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;


public class OrderBookAsksResponse  extends JingtongBaseResponse
{
	private String pair;
	private BidsAsks[] asks;
	public String getPair()
	{
		return pair;
	}
	public void setPair(String pair)
	{
		this.pair = pair;
	}
	public BidsAsks[] getAsks()
	{
		return asks;
	}
	public void setAsks(BidsAsks[] asks)
	{
		this.asks = asks;
	}


	
	
	
}
