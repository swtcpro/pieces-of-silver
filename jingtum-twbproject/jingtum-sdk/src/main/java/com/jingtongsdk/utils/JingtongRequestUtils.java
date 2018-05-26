package com.jingtongsdk.utils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jingtongsdk.bean.HttpClientResponseBean;
import com.jingtongsdk.bean.JingtongRequest;
import com.jingtongsdk.bean.JingtongResponse;
import com.jingtongsdk.bean.Jingtong.reqrsp.WalletNewRequest;
import com.jingtongsdk.exception.SdkException;

public class JingtongRequestUtils
{
	public static final Pattern PATTERN = Pattern.compile("\\{:(\\w+)\\}");// 匹配的模式

	private static Logger logger = LoggerFactory.getLogger(JingtongRequestUtils.class);  
	public static void main(String[] args) throws HttpException, IOException, Exception
	{
		WalletNewRequest wnr = new WalletNewRequest();
		getResponseClass(wnr);
	}

	public static JingtongResponse sendRequest(JingtongRequest jingtongRequest)
			throws HttpException, IOException, ClassNotFoundException
	{
		String url = getUrl(jingtongRequest);
		logger.info("==========url==============");
		logger.info(url);
		logger.info("==========Request==============");
		logger.info(jingtongRequest.toString());
		String type = jingtongRequest.requestType();
		HttpClientResponseBean hcrb = HttpClientUtils.send(url, type,jingtongRequest.toString());
		JingtongResponse jr = null;
		String body ="";
		if (hcrb.getStatusCode() == 200)
		{
			body = hcrb.getResponseBody();
			logger.info("==========Response===============");
			logger.info(body);
			jr = (JingtongResponse) JingtongRequstConstants.PRETTY_PRINT_GSON.fromJson(body,
					getResponseClass(jingtongRequest));
		}
		if(jr!=null)
		{

			logger.info("==========JingtongResponse===============");
			logger.info(jr.toString());
		}
		
		return jr;
	}

	/**
	 * 
	 * @Title: getUrl   
	 * @Description: 获取url,替换掉动态字段
	 * @param: @param jingtongRequest
	 * @param: @return
	 * @param: @throws SdkException      
	 * @return: String      
	 * @throws
	 */
	public static String getUrl(JingtongRequest jingtongRequest) throws SdkException 
	{
		String url = jingtongRequest.requestUrl();
		List<String> fieldsList = getFields(url);

		Field[] fields = jingtongRequest.getClass().getDeclaredFields();

		try
		{
			PropertyDescriptor pd = null;
			for (String fieldName : fieldsList)
			{
				for (Field f : fields)
				{

					if (fieldName.equals(f.getName()))
					{
						pd = new PropertyDescriptor(f.getName(), jingtongRequest.getClass());
						Object value = pd.getReadMethod().invoke(jingtongRequest);
						if(value!=null)
						{
							url = url.replace("{:" + fieldName + "}", value.toString());
						}
						else
						{
							url = url.replace("{:" + fieldName + "}", "");
						}
						break;
						
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new SdkException("Convert url failed.");
		}
		return JingtongRequstConstants.URL_HEAD+url;
	}

	

	/**  
	 * 正则表达式匹配符串中间的内容  
	 * @param soap  
	 * @return  
	 */
	public static List<String> getFields(String soap)
	{
		List<String> list = new ArrayList<String>();

		Matcher m = PATTERN.matcher(soap);
		while (m.find())
		{
			int i = 1;
			list.add(m.group(i));
			i++;
		}
		return list;
	}

	
	public static Class getResponseClass(JingtongRequest jingtongRequest) throws ClassNotFoundException
	{
		String responseName = jingtongRequest.getClass().getName().replace("Request", "Response");
		return Class.forName(responseName);
	}
}
