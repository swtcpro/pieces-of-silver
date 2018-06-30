package com.twb.wechat.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;



public class WXPayConfigImpl extends WXPayConfig{

	private Logger logger = LoggerFactory.getLogger(WXPayConfigImpl.class);
    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;
    
    
    private String appid;
    private String mchid;
    private String key;

    private WXPayConfigImpl(String certPath,String appid,String mchid,String key) throws Exception{
    	this.appid= appid;
    	this.mchid= mchid;
    	this.key= key;
        File file = new File(certPath);
        logger.info("file exists:"+file.exists()+","+file.getAbsolutePath());
        if(file.exists())
        {
        	  InputStream certStream = new FileInputStream(file);
              this.certData = new byte[(int) file.length()];
              certStream.read(this.certData);
              certStream.close();
        }
      
    }

    public static WXPayConfigImpl getInstance(String certPath,String appid,String mchid,String key) throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl(certPath, appid, mchid, key);
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return appid;
    }

    public String getMchID() {
        return mchid;
    }

    public String getKey() {
        return key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }


    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }

	@Override
	public IWXPayDomain getWXPayDomain()
	{
		return null;
	}
}
