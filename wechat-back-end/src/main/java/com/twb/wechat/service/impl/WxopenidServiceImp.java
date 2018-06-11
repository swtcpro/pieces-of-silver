package com.twb.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twb.wechat.entity.Wxopenid;
import com.twb.wechat.repository.WxopenidRepository;
import com.twb.wechat.service.WxopenidService;

@Service
public class WxopenidServiceImp implements WxopenidService
{

	private static final Logger logger = LoggerFactory.getLogger(WxopenidServiceImp.class);

	@Autowired
	WxopenidRepository wxopenidRepository;

	@Override
	public List<Wxopenid> getAllOpenId() throws Exception
	{
		return wxopenidRepository.getAllWxopenid();
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveOpenid(String openidxff, String openiddsb) throws Exception
	{
		Wxopenid wxopenid = new Wxopenid();
		wxopenid.setOpenidxff(openidxff);
		wxopenid.setOpeniddsb(openiddsb);
		wxopenid.setDate(new Date());
		wxopenidRepository.save(wxopenid);
		
	}
	





	

}
