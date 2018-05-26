package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;


public class OrderBookListResponse  extends JingtongBaseResponse
{
	private String pair;
	private BidsAsks[] bids;
	private BidsAsks[] asks;
	public String getPair()
	{
		return pair;
	}
	public void setPair(String pair)
	{
		this.pair = pair;
	}
	public BidsAsks[] getBids()
	{
		return bids;
	}
	public void setBids(BidsAsks[] bids)
	{
		this.bids = bids;
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
