package com.twb.service;

import com.aliyun.openservices.ons.api.SendResult;
import com.twb.commondata.data.CommitChainRespMqData;
import com.twb.commondata.data.CommitchainMqData;
import com.twb.commondata.data.DistributeMqData;

public interface MqProductService
{
//	SendResult sendMQ(String topic,String tag,String data);

//	SendResult sendMQ(String topic, String tag, CommitchainMqData data);

	SendResult sendMQ(String topic, String tag, CommitChainRespMqData data);

	SendResult sendMQ(String topic, String tag, DistributeMqData data);
	
	SendResult sendCommitChainMQ(CommitchainMqData data);
}
