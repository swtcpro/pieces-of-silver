package com.twb.wechat.service;

import java.util.List;

import com.twb.wechat.entity.Wxopenid;

public interface WxopenidService {
	
	
	List<Wxopenid> getAllOpenId()throws Exception;
	
	void saveOpenid(String openidxff,String openiddsb) throws Exception;

}
