package com.twb.task;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twb.service.SocketDataService;
import com.twb.socket.WebSocketChatClient;

@Component
public class WebSocketTask 
{
	private static final Logger logger = LoggerFactory.getLogger(WebSocketTask.class);

	@Autowired
	private SocketDataService socketDataService;


	@Value("${subscribe_address}")
	private String subscribeAddress;
	
	
	@Value("${jingtong_uri}")
	private String jingtongUri;
	
	
	@PostConstruct
	public void init()
	{
		logger.info("connectJingtong");
		try
		{
			WebSocketChatClient wcc = new WebSocketChatClient(socketDataService, jingtongUri, subscribeAddress);
			wcc.connectJingtong();
			socketDataService.socketDataForCheck();
			socketDataService.socketDataDistribute();
		}
		catch (Exception e)
		{
			logger.error("connectJingtong error!",e);
			e.printStackTrace();
		}
	}

}
