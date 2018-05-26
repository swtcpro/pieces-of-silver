package com.jingtongsdk.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jingtongsdk.bean.Jingtong.reqrsp.Effect;
import com.jingtongsdk.bean.Jingtong.reqrsp.TransactionAmount;

public class JingtongRequstConstants
{
	public static final String URL_HEAD = "https://api.jingtum.com";

	public static final String GET_TYPE = "0";
	public static final String POST_TYPE = "1";
	public static final String DELETE_TYPE = "2";

	

	public static final Gson PRETTY_PRINT_GSON = new GsonBuilder()
			.addSerializationExclusionStrategy(new JingtongExclusionStrategy())//设置序列化时字段采用策略
			.registerTypeAdapter(Effect.class, new JingtongEffectDeserializer())
			.registerTypeAdapter(TransactionAmount.class, new JingtongAmountDeserializer())
			.setPrettyPrinting()////对json结果格式化.  
			.serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.create();

}
