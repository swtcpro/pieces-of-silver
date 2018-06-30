package com.twb.wechat.utils;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;

@Component(value="TransferUtil")
public class TransferUtil
{

	private static Logger logger = LoggerFactory.getLogger(TransferUtil.class);

	static WXPayConfigImpl config ;
	
//	public static WXPayRequest wxPayRequest ;
	public static WXPay wxpay;
	
//	@Value("${wxcert_path}")
	private String certPath="";    
    @Value("${wechat.mp.appId}")
    private String appid;
    @Value("${wechat.mch_id}")
    private String mchid;
    @Value("${wechat.mch_key}")
    private String key;
    
    @Value("${wechat.notify_url}")
	private String notify_url;
	
	@PostConstruct
	public void init()
	{

		try
		{
			config = WXPayConfigImpl.getInstance(certPath, appid, mchid, key);
//			WXPayRequest wxPayRequest = new WXPayRequest(config);
			wxpay = new WXPay(config,notify_url);
		}
		catch (Exception e)
		{
			logger.error("TransferUtil init error..",e);
			e.printStackTrace();
		}
	}

	  /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(Map<String, String> data) throws Exception {
        return WXPayUtil.generateSignature(data, config.getKey());
    }
	
}
