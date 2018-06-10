package com.twb.service;

import com.aliyun.openservices.ons.api.Message;
import com.twb.commondata.data.DistributeMqData;
import com.twb.entity.SponsorData;

public interface SponsorDataService
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
	void doingBusiness(SponsorData sd) throws Exception;

	/**
	 * 
	 * @Title: saveData   
	 * @Description:  保存数据
	 * @param: @param dma
	 * @param: @param message
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: SponsorData      
	 * @throws
	 */
	SponsorData saveData(DistributeMqData dmd, Message message) throws Exception;
}
