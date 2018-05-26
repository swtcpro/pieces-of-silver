package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseResponse;

/**
 * 
 * @Title:  WalletNewResponse.java   
 * @Package com.jingtongsdk.bean.Jingtong   
 * @Description:    创建钱包响应 
 * @author: 田文彬     
 * @date:   2018年4月4日 下午2:10:40   
 * @version V1.0
 */
public class WalletNewResponse extends JingtongBaseResponse
{
	private Wallet wallet;

	public Wallet getWallet()
	{
		return wallet;
	}

	public void setWallet(Wallet wallet)
	{
		this.wallet = wallet;
	}

	
	
}

