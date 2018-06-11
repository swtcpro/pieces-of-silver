package com.twb.wechat.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twb.wechat.builder.TextBuilder;
import com.twb.wechat.entity.Wxuser;
import com.twb.wechat.repository.WxuserRepository;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class SubscribeHandler extends AbstractHandler {

	@Autowired
	WxuserRepository wxuserRepository;

	
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService weixinService,
                                  WxSessionManager sessionManager) throws WxErrorException {

    this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

    // 获取微信用户基本信息
    WxMpUser userWxInfo = weixinService.getUserService()
        .userInfo(wxMessage.getFromUser(), null);
    logger.info("userWxInfo:"+userWxInfo);
    
    if (userWxInfo != null&&!StringUtils.isEmpty(userWxInfo.getOpenId())&&userWxInfo.getSubscribe()) {
    	String openid = userWxInfo.getOpenId();
    	
		try
		{
			Wxuser wxuser = wxuserRepository.getWxuserByOpenid(openid);
			if(wxuser==null)
	    	{
	    		 wxuser = new Wxuser();
	    	}
			wxuser.setCity(userWxInfo.getCity());
			wxuser.setCountry(userWxInfo.getCountry());
			wxuser.setGroupid(userWxInfo.getGroupId()+"");
			wxuser.setHeadimgurl(userWxInfo.getHeadImgUrl());
			wxuser.setLanguage(userWxInfo.getLanguage());
			wxuser.setNickname(userWxInfo.getNickname());
			wxuser.setOpenid(userWxInfo.getOpenId());
			wxuser.setProvince(userWxInfo.getProvince());
			wxuser.setRemark(userWxInfo.getRemark());
			wxuser.setSex(userWxInfo.getSex()+"");
			wxuser.setSubscribe(userWxInfo.getSubscribe()+"");
			wxuser.setSubscribeTime(new Date((long)(userWxInfo.getSubscribeTime())*1000));
			wxuser.setTagids(Arrays.toString(userWxInfo.getTagIds()));
			wxuserRepository.save(wxuser);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("数据更新失败："+openid);
			logger.error(e.toString() + "," + Arrays.toString(e.getStackTrace()));
		}
    	
    }

    WxMpXmlOutMessage responseResult = null;
    try {
      responseResult = handleSpecial(wxMessage);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    if (responseResult != null) {
      return responseResult;
    }

    try {
      return new TextBuilder().build("谢谢关注我们公众号!\n点击下方“描述”便可获取井通零花钱实时领取方法，我们目前是免手续费的！", wxMessage, weixinService);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    return null;
  }

  /**
   * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
   */
  private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
      throws Exception {
    //TODO
    return null;
  }

}
