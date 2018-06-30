package com.twb.wechat.service;

import java.util.Map;

import com.twb.commondata.data.CommitChainRespMqData;
import com.twb.wechat.entity.OrderPay;

public interface OrderPayService {
	
	
	OrderPay hanlderOrderPay(Map xmlMap)throws Exception;
	
	void sendMq(OrderPay op)throws Exception;

	void doingCcrmd(CommitChainRespMqData ccrmd);
}
