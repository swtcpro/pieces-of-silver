package com.twb.service;

import java.util.List;

import com.twb.entity.TimerData;

public interface TimerDataService {
	
	/**
	 * 
	 * @Title: getTranFromJingtong   
	 * @Description: 根据地址，去井通拿数据直到lastTran这条
	 * @param: @param address
	 * @param: @param tran
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: List      
	 * @throws
	 */
	List<TimerData> getTranFromJingtong(String address,String lastHash) throws Exception;
	
	/**
	 * 
	 * @Title: getLastTranHash   
	 * @Description: 拿到最后一条hash
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: String      
	 * @throws
	 */
	
	String getLastTranHash() throws Exception;
	
	/**
	 * 
	 * @Title: getTobeCheckTran   
	 * @Description:  重启服务后，没有验证过的数据，放入待验证List
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: List      
	 * @throws
	 */
	List<TimerData> getTobeCheckTran() throws Exception;;
	
	
	/**
	 * @return 
	 * 
	 * @Title: checkSocketData   
	 * @Description: 检查SocketData
	 * @param: @param List<TimerData>
	 * @param: @throws Exception      
	 * @return: List      
	 * @throws
	 */
	List<TimerData> checkSocketData(List<TimerData> list) throws Exception;;

}
