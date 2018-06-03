package com.twb.service;

import org.commondata.data.DistributeMqData;

import com.aliyun.openservices.ons.api.Message;
import com.twb.entity.SendbackData;

public interface SendbackDataService
{

	/**
	 * 
	 * @Title: handlerTodoData   
	 * @Description: 待处理数据处理
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void handlerTodoData() throws Exception;

	/**
	 * 
	 * @Title: doingBusiness   
	 * @Description: 处理业务逻辑
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void doingBusiness(SendbackData sd) throws Exception;

	/**
	 * 
	 * @Title: saveData   
	 * @Description:  保存数据
	 * @param: @param dma
	 * @param: @param message
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: SendbackData      
	 * @throws
	 */
	SendbackData saveData(DistributeMqData dma, Message message) throws Exception;
}
