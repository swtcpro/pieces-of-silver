package com.twb.wechat.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


public class CommonUtils
{
	public static String toString(Object obj)
	{
		if (obj == null)
		{
			return "";
		}
		else if (obj instanceof Timestamp || obj instanceof Date)
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
		}
		else
		{
			return obj.toString();
		}

	}
	
	   public static String getIp(HttpServletRequest request) {
		   String ipAddress = request.getHeader("x-forwarded-for");  
           if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
               ipAddress = request.getHeader("Proxy-Client-IP");  
           }  
           if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
               ipAddress = request.getHeader("WL-Proxy-Client-IP");  
           }  
           if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
               ipAddress = request.getRemoteAddr();  
               if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                   //根据网卡取本机配置的IP  
                   InetAddress inet=null;  
                   try {  
                       inet = InetAddress.getLocalHost();  
                   } catch (UnknownHostException e) {  
                       e.printStackTrace();  
                   }  
                   ipAddress= inet.getHostAddress();  
               }  
           }  
           //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
           if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
               if(ipAddress.indexOf(",")>0){  
                   ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
               }  
           }  
           return ipAddress;  
	    }
	   
	   /**
		 * 
		 * @Title: validateNumber
		 * @Description: 检查是否全数字
		 * @param @param number
		 * @param @return
		 * @return boolean
		 * @throws
		 */
		public static boolean validateNumber(String number)
		{
			boolean flag = false;
			if (number != null)
			{
				Matcher m = null;
				Pattern p = Pattern.compile("^[0-9]+$");
				m = p.matcher(number);
				flag = m.matches();
			}

			return flag;

		}
		public static int string2Int(String str, int defaultVal)
		{
			int i;
			try
			{
				i = Integer.parseInt(str);
			}
			catch (NumberFormatException e)
			{
				i = defaultVal;
			}
			return i;
		}
}
