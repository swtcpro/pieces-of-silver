package com.twb.wechat.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twb.wechat.entity.OrderPay;
import com.twb.wechat.service.OrderPayService;
import com.twb.wechat.utils.TransferUtil;

@RestController
@RequestMapping("/wechat/pay")
public class WechatController
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderPayService orderPayServiceImp;
	
	
	

	@PostMapping(path = "/callback", produces = "application/xml; charset=UTF-8")
	public String callbackOrderPay(HttpServletRequest request)
	{
		logger.info("============>callbackOrderPay");
		String xml = "";
		try
		{
			xml = inputStream2String(request.getInputStream(), "UTF-8");
		}
		catch (IOException e)
		{
			logger.error("XML获取错误", e);
			return "fail";
		}
		logger.info("callbackOrderPay xml:" + xml);
		Map xmlMap;
		try
		{
			xmlMap = TransferUtil.wxpay.processResponseXml(xml);
		}
		catch (Exception e)
		{
			logger.error("XML解析或验证错误", e);
			return "fail";
		}
		logger.info("callbackOrderPay xmlMap:" + xmlMap);
		String return_code = (String) xmlMap.get("return_code");
		if ("SUCCESS".equals(return_code))
		{
			OrderPay op = null;
			try
			{
				op = orderPayServiceImp.hanlderOrderPay(xmlMap);
			}
			catch (Exception e)
			{
				logger.error("数据更新失败");
				e.printStackTrace();
			}
			// 支付成功状态
			if (op != null && OrderPay.STATE_PAY.equals(op.getState()))
			{
				try
				{
					orderPayServiceImp.sendMq(op);
				}
				catch (Exception e)
				{
					logger.error("MQ发送失败"+op.getId(),e);
				}
			}
		}
		else
		{
			logger.error("return_code is not success," + xmlMap.get("return_msg"));
		}

		return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}

	/**
	* InputStream流转换成String字符串
	* @param inStream InputStream流
	* @param encoding 编码格式
	* @return String字符串
	*/
	public String inputStream2String(InputStream inStream, String encoding)
	{
		String result = null;
		try
		{
			int buffersize = 1024;
			if (inStream != null)
			{
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] tempBytes = new byte[buffersize];
				int count = -1;
				while ((count = inStream.read(tempBytes, 0, buffersize)) != -1)
				{
					outStream.write(tempBytes, 0, count);
				}
				tempBytes = null;
				outStream.flush();
				result = new String(outStream.toByteArray(), encoding);
			}
		}
		catch (Exception e)
		{
			result = null;
		}
		return result;
	}

}
