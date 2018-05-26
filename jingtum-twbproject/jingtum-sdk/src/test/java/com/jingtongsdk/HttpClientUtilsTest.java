package com.jingtongsdk;

import com.jingtongsdk.utils.HttpClientUtils;

import junit.framework.TestCase;

public class HttpClientUtilsTest extends TestCase
{
	public void testSend() throws Exception
	{
		System.out.println("Test");
		long before = System.currentTimeMillis();
		HttpClientUtils.sendGet("https://www.baidu.com/");
		
		
		
		long after = System.currentTimeMillis();
		System.out.println(after-before);
	}
}
