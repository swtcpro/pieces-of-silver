package com.jingtongsdk.bean.Jingtong;

import com.jingtongsdk.bean.JingtongRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

public abstract class JingtongBaseRequest implements JingtongRequest
{
	
	public String toString() {
	        return JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(this);
	    }
	
}
