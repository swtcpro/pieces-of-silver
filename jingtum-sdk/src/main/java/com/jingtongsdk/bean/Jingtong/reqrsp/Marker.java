package com.jingtongsdk.bean.Jingtong.reqrsp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jingtongsdk.utils.JingtongRequstConstants;

public class Marker
{
	private Integer ledger;
	private Integer seq;
	public Integer getLedger()
	{
		return ledger;
	}
	public void setLedger(Integer ledger)
	{
		this.ledger = ledger;
	}
	public Integer getSeq()
	{
		return seq;
	}
	public void setSeq(Integer seq)
	{
		this.seq = seq;
	}
	@Override
	public String toString()
	{
		try
		{
			return URLEncoder.encode("{ledger:" + ledger + ", seq:" + seq +"}","utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();

			return "";
		}
	}
	
	
	
	
}
