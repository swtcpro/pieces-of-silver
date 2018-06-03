package com.jingtongsdk.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.jingtongsdk.bean.HttpClientResponseBean;

public class HttpClientUtils
{
	public static HttpClientResponseBean send(String url,String type,String body) throws HttpException, IOException
	{
		if(JingtongRequstConstants.GET_TYPE.equals(type))
		{
			return sendGet(url);
		}
		else
		{
			return sendPost(url,body);
		}
	}
	
	
	
	public static HttpClientResponseBean sendGet(String getUrl) throws HttpException, IOException
	{
		HttpClientResponseBean hcrb = new HttpClientResponseBean();
		HttpClient client = new HttpClient();
		GetMethod httpget = new GetMethod(getUrl);

		try
		{
			httpget.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
			httpget.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
			client.executeMethod(httpget);
			int statusCode = httpget.getStatusCode();
			String responseBody = httpget.getResponseBodyAsString();
			hcrb.setResponseBody(responseBody);
			hcrb.setStatusCode(statusCode);
		}
		finally
		{
			httpget.releaseConnection();
		}

		return hcrb;
	}

	
	public static HttpClientResponseBean sendPost(String postUrl, String json) throws IOException
	{
		HttpClientResponseBean hcrb = new HttpClientResponseBean();
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(postUrl);
		try
		{

			
//			if (header != null && header.size() > 0)
//			{
//				Iterator it = header.entrySet().iterator();
//				String key;
//				String value;
//				while (it.hasNext())
//				{
//					Map.Entry entry = (Map.Entry) it.next();
//					if (entry.getKey() != null && entry.getValue() != null)
//					{
//						post.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
//					}
//
//				}
//			}

			RequestEntity se = new StringRequestEntity(json, "application/json", "UTF-8");

			post.setRequestEntity(se);

			post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);

			client.executeMethod(post);

			int statusCode = post.getStatusCode();
			String responseBody = post.getResponseBodyAsString();

			hcrb.setResponseBody(responseBody);
			hcrb.setStatusCode(statusCode);

		}
		finally
		{
			post.releaseConnection();
		}

		return hcrb;
	}
//
//	public static HttpClientResponseBean sendDelete(String deleteUrl, String json, Map header)
//	{
//		HttpClientResponseBean hcrb = new HttpClientResponseBean();
//		HttpClient client = new HttpClient();
//		DeleteMethod delete = new DeleteMethod(deleteUrl);
//		try
//		{
//
//			
//			if (header != null && header.size() > 0)
//			{
//				Iterator it = header.entrySet().iterator();
//				String key;
//				String value;
//				while (it.hasNext())
//				{
//					Map.Entry entry = (Map.Entry) it.next();
//					if (entry.getKey() != null && entry.getValue() != null)
//					{
//						delete.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
//					}
//
//				}
//			}
//
//
////			delete.setQueryString(json);
//			
//			RequestEntity se = new StringRequestEntity(json, "application/json", "UTF-8");
//
//			delete.set.setRequestEntity(se);
//
//			post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
//
//			client.executeMethod(post);
//			
//			
//			client.executeMethod(delete);
//
//			int statusCode = delete.getStatusCode();
//			String responseBody = delete.getResponseBodyAsString();
//
//			hcrb.setSendState("Y");
//			hcrb.setResponseBody(responseBody);
//			hcrb.setStatusCode(statusCode);
//
//		}
//		catch (Exception e)
//		{
//			hcrb.setSendState("N");
//			hcrb.setMsg(e.toString() + "," + e.getMessage());
//		}
//		finally
//		{
//			delete.releaseConnection();
//		}
//
//		return hcrb;
//	}
}
