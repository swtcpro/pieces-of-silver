package com.twb.service;

import com.aliyun.openservices.ons.api.SendResult;

public interface MqProductService
{
	SendResult sendMQ(String topic,String tag,String data);
}
