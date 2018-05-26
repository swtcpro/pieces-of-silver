package com.twb.service;

import com.twb.entity.SocketData;
import com.twb.entity.TimerData;

/**
 * 
 * @Title:  DistributeService.java   
 * @Package com.twb.service   
 * @Description:    分发器 
 * @author: 田文彬     
 * @date:   2018年5月23日 下午3:46:47   
 * @version V1.0
 */
public interface DistributeService {
	
	/**
	 * 
	 * @Title: handlerSocketData   
	 * @Description: 处理SocketData
	 * @param: @param socketData
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void handlerSocketData(SocketData socketData) throws Exception;
	
	/**
	 * 
	 * @Title: handlerTimerData   
	 * @Description: 处理TimerData
	 * @param: @param timerData
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void handlerTimerData(TimerData timerData) throws Exception;
	
}
