package com.twb.service;

import java.util.Date;
import java.util.List;

import com.twb.entity.CommitchainVerifyData;
import com.twb.entity.TimerData;



public interface CommitchainVerifyService {
	
	/**
	 * 
	 * @Title: getCommitchainVerifyData   
	 * @Description: 从定时监听的数据中，取出需要校验的数据
	 * @param: @param list
	 * @param: @return      
	 * @return: List<CommitchainVerifyData>      
	 * @throws
	 */
	List<CommitchainVerifyData> getCommitchainVerifyData(List<TimerData> list)throws Exception;
	
	/**
	 * 
	 * @Title: addChainVerifydataQueue   
	 * @Description: 添加到上链数据校验队列
	 * @param: @param list
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void addChainVerifydataQueue(List<CommitchainVerifyData> list)throws Exception;
	
	/**
	 * 
	 * @Title: getTocheckCVD   
	 * @Description: 获取所有待比对的上链数据
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: List<CommitchainVerifyData>      
	 * @throws
	 */
	List<CommitchainVerifyData> getTocheckCVD()throws Exception;
	
	/**
	 * 
	 * @Title: doingTocheckCVD   
	 * @Description: 检查上链数据
	 * @param: @param cvd
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void doingTocheckCVD(CommitchainVerifyData cvd)throws Exception;
	
	/**
	 * 
	 * @Title: getLastDate   
	 * @Description: 获取最后一条数据的时间
	 * @param: @return      
	 * @return: Date      
	 * @throws
	 */
	Date getLastDate()throws Exception;

	/**
	 * 
	 * @Title: commitChainFailCheck   
	 * @Description: 上链失败检查
	 * @param: @param lastDate      
	 * @return: void      
	 * @throws
	 */
	void commitChainFailCheck(Date lastDate)  throws Exception;
}
