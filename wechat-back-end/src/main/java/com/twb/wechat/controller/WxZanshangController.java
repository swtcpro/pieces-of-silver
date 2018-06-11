package com.twb.wechat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *  注意：此contorller 实现WxMpMenuService接口，仅是为了演示如何调用所有菜单相关操作接口，
 *      实际项目中无需这样，根据自己需要添加对应接口即可
 * </pre>
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Controller
@RequestMapping("/wechat")
public class WxZanshangController
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/zanshang")
	public ModelAndView oauthSnsapiBaseNew() throws Exception
	{
		return new ModelAndView("zanshang");
		

	}


}
