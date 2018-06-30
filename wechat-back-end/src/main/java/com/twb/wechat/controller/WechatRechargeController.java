package com.twb.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twb.wechat.bean.InData;
import com.twb.wechat.bean.OutData;
import com.twb.wechat.service.OrderPayService;
import com.twb.wechat.utils.CommonUtils;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@RestController
@RequestMapping("/wechat/order")
@CrossOrigin
public class WechatRechargeController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  
  @Autowired
  private OrderPayService orderPayServiceImp;

  	@RequestMapping("/submit")
	@ResponseBody
	public OutData submitOrderPay(@RequestBody InData inData,HttpServletRequest request)
	{
  		String ip = "";
  		try
		{
			ip = CommonUtils.getIp(request);
			
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			logger.error("IP获取失败"+e1);
		}
  		
  		if(ip==null||ip.isEmpty())
		{
			ip="127.0.0.1";
		}
//  		ip = "222.247.199.185";
  		logger.info("============>submitOrderPay");
		OutData outData = new OutData();
		try
		{
			if (inData == null || inData.getInmap() == null)
			{
				outData.setReturncode("false");
				outData.setReturnmsg("数据为空");
				return outData;
			}
			outData = orderPayServiceImp.submitOrderPay(inData,ip);
		}
		catch (Exception e)
		{
			outData.setReturncode("false");
			outData.setReturnmsg(e.getMessage());
			logger.error("订单生成失败"+e);
		}

		return outData;
	}
  	
  	@RequestMapping("/getOrder")
	@ResponseBody
	public OutData getOrder(@RequestBody InData inData)
	{
  		String ip = "";
  		
  		logger.info("============>getOrder");
		OutData outData = new OutData();
		try
		{
			if (inData == null || inData.getInmap() == null)
			{
				outData.setReturncode("false");
				outData.setReturnmsg("数据为空");
				return outData;
			}
			outData = orderPayServiceImp.getOrderPay(inData.getInmap());
		}
		catch (Exception e)
		{
			outData.setReturncode("false");
			outData.setReturnmsg(e.getMessage());
			logger.error("getOrder失败",e);
		}

		return outData;
	}
  	
  	
  	
}
