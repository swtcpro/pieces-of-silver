package com.twb.wechat.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twb.wechat.entity.Wxuser;
import com.twb.wechat.repository.WxuserRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

	@Autowired
	WxuserRepository wxuserRepository;
	
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {
    String openId = wxMessage.getFromUser();
    this.logger.info("取消关注用户 OPENID: " + openId);
    if (!StringUtils.isEmpty(openId)) {
		try
		{
			Wxuser wxuser = wxuserRepository.getWxuserByOpenid(openId);
			if(wxuser!=null)
	    	{
				wxuser.setSubscribe("false");
	    		 wxuserRepository.save(wxuser);
	    	}
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("数据更新失败："+openId);
			logger.error(e.toString() + "," + Arrays.toString(e.getStackTrace()));
		}
    	
    }
    return null;
  }

}
