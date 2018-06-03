package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;

/**
 * 
 * @Title:  BalancesResponse.java   
 * @Package com.jingtongsdk.bean.Jingtong.reqrsp   
 * @Description:     查询余额响应
 * @author: 田文彬     
 * @date:   2018年4月4日 下午4:28:39   
 * @version V1.0
 */
public class BalancesResponse extends JingtongBaseResponse
{
	private Balances[] balances;
	private Integer sequence;
	public Balances[] getBalances()
	{
		return balances;
	}
	public void setBalances(Balances[] balances)
	{
		this.balances = balances;
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
