package com.twb.service;

import org.commondata.data.CommitChainRespMqData;
import org.commondata.data.CommitchainMqData;
import org.commondata.data.DistributeMqData;

import com.aliyun.openservices.ons.api.SendResult;

public interface MqProductService
{
//	SendResult sendMQ(String topic,String tag,String data);

	SendResult sendMQ(String topic, String tag, CommitchainMqData data);

	SendResult sendMQ(String topic, String tag, CommitChainRespMqData data);

	SendResult sendMQ(String topic, String tag, DistributeMqData data);
}
