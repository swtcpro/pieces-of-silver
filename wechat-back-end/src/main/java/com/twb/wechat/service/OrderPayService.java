package com.twb.wechat.service;

import java.util.Map;

import com.twb.wechat.bean.InData;
import com.twb.wechat.bean.OutData;

public interface OrderPayService {
	
	
	OutData getOrderPay(Map inMap)throws Exception;
	
	OutData submitOrderPay(InData indata,String ip)throws Exception;
}
