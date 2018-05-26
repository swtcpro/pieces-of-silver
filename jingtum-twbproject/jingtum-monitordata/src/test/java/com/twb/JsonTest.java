package com.twb;

import com.jingtongsdk.utils.JingtongRequstConstants;
import com.twb.entity.DistributeLog;

import junit.framework.TestCase;

public class JsonTest extends TestCase
{
	public void testSend() throws Exception
	{
		DistributeLog dl = new DistributeLog();
		System.out.println(JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(dl));
	}
}
