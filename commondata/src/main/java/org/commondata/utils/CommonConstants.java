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

}
