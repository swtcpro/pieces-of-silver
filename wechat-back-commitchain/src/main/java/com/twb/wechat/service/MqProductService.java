package com.twb.wechat.service;

import com.aliyun.openservices.ons.api.SendResult;
import com.twb.commondata.data.CommitchainMqData;

public interface MqProductService
{
	SendResult sendCommitChainMQ(CommitchainMqData data);
}
