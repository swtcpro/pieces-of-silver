package com.twb.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.wxpay.sdk.WXPayRequest;
import com.github.wxpay.sdk.WXPayUtil;

@Component(value="TransferUtil")
public class TransferUtil
{

	private static Logger logger = LoggerFactory.getLogger(TransferUtil.class);

	static WXPayConfigImpl config ;
	
	static WXPayRequest wxPayRequest ;
	
	@Value("${wxcert_path}")
	private String certPath;    
    @Value("${wx_appid}")
    private String appid;
    @Value("${wx_mchid}")
    private String mchid;
    @Value("${wx_key}")
    private String key;
    
	
	@PostConstruct
	public void init()
	{

		try
		{
			config = WXPayConfigImpl.getInstance(certPath, appid, mchid, key);
			wxPayRequest = new WXPayRequest(config);
		}
		catch (Exception e)
		{
			logger.error("TransferUtil init error..",e);
			e.printStackTrace();
		}
	}
	
    public final static BigDecimal BD100 = new BigDecimal("100"); 


    /**
     * 
     * @Title: transfer   
     * @Description: TODO
     * @param: @param partner_trade_no 商户订单号
     * @param: @param openid
     * @param: @param backAmount
     * @param: @return
     * @param: @throws Exception      
     * @return: Map      
     * @throws
     */
	public static Map transfer( String partner_trade_no, String openid,String backAmount,String desc) throws Exception
	{
		String result;
		String mch_id = config.getMchID();
		String appid = config.getAppID();
		
		String nonce_str = generateRadomCode();

		// 测试一分钱
		BigDecimal bigInterestRate = new BigDecimal(backAmount); 
		
		int amount = bigInterestRate.multiply(BD100).intValue();
//		String desc = "红包";
		String ip = "127.0.0.1";
		// ----------------------------------------------------------------------
		String nick_name = "";
		String checkName = "NO_CHECK";
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("mch_appid", appid);
		params.put("mchid", mch_id);
		params.put("nonce_str", nonce_str);
		params.put("partner_trade_no", partner_trade_no);
		params.put("openid", openid);
		params.put("check_name", checkName);
		params.put("re_user_name", nick_name);
		params.put("amount", amount+"");
		params.put("desc", desc);
		params.put("spbill_create_ip", ip);

		String sign = WXPayUtil.generateSignature(params, config.getKey());
		String companyxml = "<xml>" + "<mch_appid>" + appid + "</mch_appid>"
				+ "<mchid>" + mch_id + "</mchid>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<partner_trade_no>" + partner_trade_no
				+ "</partner_trade_no>" + "<openid>" + openid + "</openid>"
				+ "<check_name>" + checkName + "</check_name>"
				+ "<re_user_name>" + nick_name + "</re_user_name>" + "<amount>"
				+ amount + "</amount>" + "<desc>" + desc + "</desc>"
				+ "<spbill_create_ip>" + ip + "</spbill_create_ip>" + "<sign>"
				+ sign + "</sign>" + "</xml>";
        
        
		logger.info("转账数据："+companyxml);
        result = wxPayRequest.requestWithCert("/mmpaymkttransfers/promotion/transfers", "", companyxml, 10000, 10000, true);
        logger.info("转账结果："+result);
        
        Map map = WXPayUtil.xmlToMap(result);
		return map;
	}

	
	public static String generateRadomCode()
	{
		String a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		return generateRadomCode(14,a);
	}
	public static String generateRadomCode(int len, String radomCode)
	{
		Random random = new Random();
		int charLen = radomCode.length();
		StringBuffer value = new StringBuffer();
		for (int i = 0; i < len; ++i)
		{
			int iV = random.nextInt(charLen);
			value.append(radomCode.charAt(iV));
		}

		return value.toString();
	}

	
}
