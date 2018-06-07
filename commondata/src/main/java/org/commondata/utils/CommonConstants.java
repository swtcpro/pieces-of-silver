package org.commondata.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommonConstants
{

	

	public static final Gson PRETTY_PRINT_GSON = new GsonBuilder()
			.addSerializationExclusionStrategy(new MyExclusionStrategy())//设置序列化时字段采用策略
			.serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.create();
	
	

	//提交上链的参数
	public static final String COMMITCHAIN_PRODUCER_ID="PID_jingtumtwb";
	public static final String COMMITCHAIN_TOPIC="jingtum";
	public static final String COMMITCHAIN_TAG="commitchain";
	public static final String COMMITCHAIN_CONSUMER_ID="CID_JingtumCommitchain";
	public static final String COMMITCHAIN_ONSADDR="http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet";
	
//			#ONSADDR 请根据不同Region进行配置
//			#公网测试: http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet
//			#公有云生产: http://onsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
//			#杭州金融云: http://jbponsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
//			#深圳金融云: http://mq4finance-sz.addr.aliyun.com:8080/rocketmq/nsaddr4client-internal

	
	
	
}
