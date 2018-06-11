package com.twb.wechat.controller;

import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static me.chanjar.weixin.common.api.WxConsts.MenuButtonType;

/**
 * <pre>
 *  注意：此contorller 实现WxMpMenuService接口，仅是为了演示如何调用所有菜单相关操作接口，
 *      实际项目中无需这样，根据自己需要添加对应接口即可
 * </pre>
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@RestController
@RequestMapping("/wechat/material")
public class WxMaterialController  {

private final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private WxMpService wxService;

  

  @GetMapping("/getMaterial")
  public String materialGet() throws WxErrorException {
	  logger.info("getMaterial start");
    return this.wxService.getMaterialService().materialNewsBatchGet(0,10).toString();
  }

 
}
