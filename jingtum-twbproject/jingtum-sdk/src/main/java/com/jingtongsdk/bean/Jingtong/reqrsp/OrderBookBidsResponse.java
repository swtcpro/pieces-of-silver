package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;


public class OrderBookBidsResponse  extends JingtongBaseResponse
{
	private String pair;
	private BidsAsks[] bids;
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

	
	
	
}
