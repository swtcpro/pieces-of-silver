package com.twb.wechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils
{

	/**
	 * 
	 * @Title: getMd5Salt   
	 * @Description: MD5加盐
	 * @param: @param plainText
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getMd5Salt(String plainText)
	{
		return getMd5(plainText+"twbbwt");
	}
	
	/**
	 * 
	* @Title: getMd5 
	* @Description: MD5加密
	* @param @param plainText
	* @param @return
	* @return String
	* @throws
	 */
    public static String getMd5(String plainText) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(plainText.getBytes());  
            byte b[] = md.digest();  
  
            int i;  
  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32位加密  
            return buf.toString();  
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;  
        } 
    }  
}
