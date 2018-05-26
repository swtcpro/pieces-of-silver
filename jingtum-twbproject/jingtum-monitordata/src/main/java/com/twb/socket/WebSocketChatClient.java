package com.twb.socket;

import java.net.URI;
import java.net.URISyntaxException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twb.entity.SocketData;
import com.twb.service.SocketDataService;
import com.twb.utils.DistributeQueue;
import com.twb.utils.SocketDataCache;

public class WebSocketChatClient extends WebSocketClient
{
	private static final Logger logger = LoggerFactory.getLogger(WebSocketChatClient.class);

	private SocketDataService socketDataService;

	public String uri = "";

	public String subscribeAddress = "";

	public WebSocketChatClient(SocketDataService socketDataService, String uri, String subscribeAddress) throws URISyntaxException
	{
		super(new URI(uri));
		this.socketDataService = socketDataService;
		this.uri = uri;
		this.subscribeAddress = subscribeAddress;
	}

	public void connectJingtong() throws InterruptedException, URISyntaxException
	{
		trustAllHosts(this);
		this.connectBlocking();

		this.send("{\"command\": \"subscribe\",\"type\": \"account\",\"account\": \"" + subscribeAddress + "\"}");

	}

	@Override
	public void onOpen(ServerHandshake handshakedata)
	{
		logger.info("Connected");

	}

	@Override
	public void onMessage(String message)
	{
		logger.info("socket got: " + message);
		try
		{
			SocketData socketData = socketDataService.handlerSubscribeMsg(message);
			if(socketData!=null)
			{
				logger.info("SocketData socket 缓存数据大小:"+SocketDataCache.size());
				SocketDataCache.add(socketData.getHash());
				DistributeQueue.add(socketData);
				
			}
		}
		catch (Exception e)
		{

			e.printStackTrace();
			logger.error("got error: " + message,e);
		}

	}

	@Override
	public void onClose(int code, String reason, boolean remote)
	{
		logger.info("Disconnected");
		// 断开连接重新连接
		try
		{
			Thread.sleep(10000);
			WebSocketChatClient wcc = new WebSocketChatClient(socketDataService, uri, subscribeAddress);
			wcc.connectJingtong();
		}
		catch (Exception e)
		{

			logger.error("WebSocketChatClient onClose",e);
			e.printStackTrace();
		}

	}

	@Override
	public void onError(Exception e)
	{
		e.printStackTrace();
		logger.error("WebSocketChatClient onError",e);
	}

	/**
	 * Trust every server - dont check for any certificate
	 
	* @param <AppWebSocketClient>*/
	private static <AppWebSocketClient> void trustAllHosts(AppWebSocketClient appClient)
	{
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]
		{ new X509TrustManager()
		{
			public java.security.cert.X509Certificate[] getAcceptedIssuers()
			{
				return new java.security.cert.X509Certificate[]
				{};
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException
			{
				// TODO Auto-generated method stub

			}
		} };

		// Install the all-trusting trust manager
		try
		{
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			((WebSocketClient) appClient).setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sc));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
