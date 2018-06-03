package com.twb.service;

import com.twb.entity.SocketData;

public interface SocketDataService {
	
	/**
	 * 
	 * @Title: handlerSubscribeMsg   
	 * @Description: 处理监听的消息
	 * @param: @param msg
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: SocketData      
	 * @throws
	 */
	SocketData handlerSubscribeMsg(String msg) throws Exception;

	/**
	 * 
	 * @Title: socketDataForCheck   
	 * @Description: 重启服务后，socketData添加到缓存,用来与TransactionLog比对
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void socketDataForCheck() throws Exception;
	
	/**
	 * 
	 * @Title: socketDataDistribute   
	 * @Description: 首次启动，分发状态为待分发的socketData
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void socketDataDistribute() throws Exception;
}
