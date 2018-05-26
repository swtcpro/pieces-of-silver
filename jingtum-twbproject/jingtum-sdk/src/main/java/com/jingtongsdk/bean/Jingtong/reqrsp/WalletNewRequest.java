package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

/**
 * 
 * @Title:  WalletNewRequst.java   
 * @Package com.jingtongsdk.bean.Jingtong   
 * @Description:    创建钱包请求 
 * @author: 田文彬     
 * @date:   2018年4月4日 下午2:10:24   
 * @version V1.0
 */
public class WalletNewRequest extends JingtongBaseRequest
{

	public String requestUrl()
	{
		return "/v2/wallet/new";
	}
	
	public String requestType()
	{
		return JingtongRequstConstants.GET_TYPE;
	}
	
	
	

	
	
}
