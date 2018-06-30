package com.twb.wechat.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.twb.wechat.bean.HttpClientResponseBean;
import com.twb.wechat.entity.Wxopenid;
import com.twb.wechat.service.WxopenidService;
import com.twb.wechat.utils.EncryptUtils;
import com.twb.wechat.utils.HttpClientUtils;
import com.twb.wechat.utils.TokenProccessor;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * <pre>
 *  注意：此contorller 实现WxMpMenuService接口，仅是为了演示如何调用所有菜单相关操作接口，
 *      实际项目中无需这样，根据自己需要添加对应接口即可
 * </pre>
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Controller
@RequestMapping("/wechat/oauth")
public class WxOauthController
{
	@Autowired
	WxopenidService WxopenidServiceImp;

	@Autowired
	private WxMpService wxService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${yunwx.appid}")
	private String yunwx_appid;

	@Value("${yunwx.secret}")
	private String yunwx_secret;

	@Value("${weburl}")
	private String weburl;

	public static Map OPENID_MAP = new ConcurrentHashMap();
	public static Map TOEKN_MAP = new ConcurrentHashMap();

	public static final String OPENID_XFF = "openidxff";
	public static final String OPENID_DSB = "openiddsb";

	// 定义在构造方法完毕后，执行这个初始化方法
	@PostConstruct
	public void init()
	{
		List<Wxopenid> list=null;
		try
		{
			list = WxopenidServiceImp.getAllOpenId();
		}
		catch (Exception e)
		{
			logger.error("OPENID_MAP 从数据库获取数据失败");
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0)
		{
			for(Wxopenid wxopenid:list)
			{
				OPENID_MAP.put(wxopenid.getOpenidxff(), wxopenid.getOpeniddsb());
			}
		}
		
	}
	
	/**
	 * Oauth网页认证，由于支付用的不是当前公众号,需要获取当前公众号的openID和支付用的openid
	 * @Description: 
	 * @param request
	 * @return void
	 * @throws Exception 
	 */
	@RequestMapping("/openid")
	public ModelAndView oauthSnsapiBaseXffopenid(HttpServletRequest request,
			String code, String state) throws Exception
	{
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
		if (wxMpOAuth2AccessToken == null)
		{
			logger.error("wxMpOAuth2AccessToken is null");
		}
		String openidxff = wxMpOAuth2AccessToken.getOpenId();
		if (StringUtils.isEmpty(openidxff))
		{
			logger.error("openidxff is empty");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(state);
		// 没有带参数处理
		if (sb.indexOf("?") == -1)
		{
			sb.append("?").append("openid=").append(openidxff);
		}
		else
		{
			sb.append("&").append("openid=").append(openidxff);
		}
		logger.info("oauthSnsapiBaseXffopenid sendUrl:" + sb.toString());
		return new ModelAndView("redirect:" + sb.toString());

	}
	
	@RequestMapping("/submitorder")
	public ModelAndView submitorder(String code, String state) throws Exception
	{

		if(StringUtils.isEmpty(code))
		{
			return new ModelAndView("redirect:" + "https://open.weixin.qq.com/connect/oauth2/authorize?scope=snsapi_base&response_type=code&redirect_uri=https://www.twbbb.cn/wechat/oauth/submitorder&appid=wx3ef9424877a495df");
		}
		
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
		try
		{
			wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
		}
		catch (Exception e)
		{
			logger.error("submitorder oauth2getAccessToken",e);
			return new ModelAndView("redirect:" + "https://open.weixin.qq.com/connect/oauth2/authorize?scope=snsapi_base&response_type=code&redirect_uri=https://www.twbbb.cn/wechat/oauth/submitorder&appid=wx3ef9424877a495df");
		}
		if (wxMpOAuth2AccessToken == null)
		{
			logger.error("submitorder wxMpOAuth2AccessToken is null");
		}
		String openidxff = wxMpOAuth2AccessToken.getOpenId();
		if (StringUtils.isEmpty(openidxff))
		{
			logger.error("submitorder openidxff is empty");
		}
		
		Map paramMap = new HashMap();
		paramMap.put("openid", openidxff);
		
//		Map paramMap = new HashMap();
//		paramMap.put("openiddsb", "ocfZ605JmZQhqlZOq1Nf_6HZe1AE");
		
		return new ModelAndView("index", paramMap);

	}

	/**
	 * Oauth网页认证，由于支付用的不是当前公众号,需要获取当前公众号的openID和支付用的openid
	 * @Description: 
	 * @param request
	 * @return void
	 * @throws Exception 
	 */
	@RequestMapping("/{authAppid}/snsapi_base")
	public ModelAndView oauthSnsapiBaseNew(@PathVariable("authAppid") String authAppid, HttpServletRequest request,
			String code, String state) throws Exception
	{

		if(StringUtils.isEmpty(code))
		{
			return new ModelAndView("redirect:" + "https://open.weixin.qq.com/connect/oauth2/authorize?scope=snsapi_base&response_type=code&redirect_uri=https://www.twbbb.cn/wechat/oauth/wx3ef9424877a495df/snsapi_base.form&appid=wx3ef9424877a495df");
		}
		try
		{
			// 云大商帮获取OpenID
			if (yunwx_appid.equals(authAppid))
			{
				Map param = getYundsbParam(code, state);

				return new ModelAndView("tx", param);
			}

			Map paramMap = getXffParam(code);
			if (paramMap.containsKey(OPENID_XFF))
			{
				if (paramMap.containsKey(OPENID_DSB))
				{
					return new ModelAndView("tx", paramMap);
				}
				else
				{
					String openid_xff = (String) paramMap.get(OPENID_XFF);
					String token = TokenProccessor.getInstance().makeToken();
					TOEKN_MAP.put(token,true);
					String md5 = EncryptUtils.getMd5Salt(openid_xff+"|"+token);
					String stateSend = openid_xff+"|"+token + "|" + md5;
					String sendUrl = openidUrl(yunwx_appid, stateSend);
					logger.info("sendUrl:" + sendUrl);
					return new ModelAndView("redirect:" + sendUrl);
				}
			}
			return new ModelAndView("tx", paramMap);
		}
		catch (Exception e)
		{
			logger.error("oauthSnsapiBaseNew error",e);
			return new ModelAndView("redirect:" + "https://open.weixin.qq.com/connect/oauth2/authorize?scope=snsapi_base&response_type=code&redirect_uri=https://www.twbbb.cn/wechat/oauth/wx3ef9424877a495df/snsapi_base.form&appid=wx3ef9424877a495df");
		}

	}

	private Map getXffParam(String code) throws WxErrorException
	{
		Map map = new HashMap();
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
		if (wxMpOAuth2AccessToken == null)
		{
			logger.error("wxMpOAuth2AccessToken is null");
			return map;
		}
		String openidxff = wxMpOAuth2AccessToken.getOpenId();
		if (StringUtils.isEmpty(openidxff))
		{
			logger.error("openidxff is empty");
			return map;
		}
		logger.info("拿到openidxff：" + openidxff);
		map.put(OPENID_XFF, openidxff);
		if (OPENID_MAP.containsKey(openidxff))
		{

			String openiddsb = (String) OPENID_MAP.get(openidxff);
			logger.info("缓存拿到openiddsb：" + openidxff + "," + openiddsb);
			map.put(OPENID_DSB, openiddsb);
		}
		return map;
	}

	private Map getYundsbParam(String code, String state) throws IOException
	{
		Map map = new HashMap();
		if (StringUtils.isEmpty(state))
		{
			logger.error("state is empty" + state);
			return map;
		}

		String str[] = state.split("\\|");
		if (str.length != 3)
		{
			logger.error("state size is not 3" + Arrays.deepToString(str));
			return map;
		}
		String openidxff = str[0];
		String token = str[1];
		String md5 = str[2];
		
		logger.info("openidxff:"+openidxff+",token:"+token+",md5:"+md5);
		
		if(StringUtils.isEmpty(openidxff) || StringUtils.isEmpty(md5)||StringUtils.isEmpty(token))
		{
			logger.error("验证失败,数据有空" );
			return map;
		}
		
		Boolean b = (Boolean)TOEKN_MAP.remove(token);
		logger.info("token cache:"+b);
		if(b==null||!b)
		{
			logger.error("token验证失败");
			return map;
		}
		
		logger.info("token验证成功");
		if (!md5.equals(EncryptUtils.getMd5Salt(openidxff+"|"+token)))
		{
			logger.error("md5验证失败.");
			return map;
		}
		logger.info("md5验证成功.");

		String openIdurl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + yunwx_appid + "&secret="
				+ yunwx_secret + "&code=" + code + "&grant_type=authorization_code";
		HttpClientResponseBean hcrb = HttpClientUtils.sendPost(openIdurl, null);
		String openiddsb = "";

		if (hcrb.getStatusCode() != 200)
		{
			logger.error("未取到云大商帮openid" + code + "," + state);
			return map;
		}

		String body = hcrb.getResponseBody();
		logger.info("Response ：" + body);
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = WxMpOAuth2AccessToken.fromJson(hcrb.getResponseBody());
		if (wxMpOAuth2AccessToken != null)
		{
			openiddsb = wxMpOAuth2AccessToken.getOpenId();
		}

		if (StringUtils.isEmpty(openiddsb))
		{
			logger.error("未取到云大商帮openiddsb" + code + "," + state);
			return map;
		}
		logger.info("拿到openiddsb：" + openiddsb);
		if (!OPENID_MAP.containsKey(openidxff))
		{
			OPENID_MAP.put(openidxff, openiddsb);
			logger.info("openiddsb更新到缓存：" + openidxff + "," + openiddsb);
			// 保存数据库

			try
			{
				WxopenidServiceImp.saveOpenid(openidxff, openiddsb);
			}
			catch (Exception e)
			{
				logger.info("插入数据库失败：" + openidxff + "," + openiddsb);
				e.printStackTrace();
			}

		}

		map.put(OPENID_DSB, openiddsb);
		map.put(OPENID_XFF, openidxff);
		return map;
	}

	private String openidUrl(String appid, String state)
	{
		return "https://open.weixin.qq.com/connect/oauth2/authorize?scope=snsapi_base&response_type=code&redirect_uri="
				+ weburl + "wechat/oauth/" + appid + "/snsapi_base.form&appid=" + appid + "&state=" + state;
	}

}
