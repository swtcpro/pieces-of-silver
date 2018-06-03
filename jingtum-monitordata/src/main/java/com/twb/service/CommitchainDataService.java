package com.twb.service;

import java.util.List;

import com.twb.entity.CommitchainData;


public interface CommitchainDataService {
	
	
	
	/**
	 * 
	 * @Title: CommitchainData   
	 * @Description: 处理上链
	 * @param: @param CommitchainData
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void doingCommitchainData(CommitchainData commitchainData) throws Exception;

	/**
	 * 
	 * @Title: savaCdFromMq   
	 * @Description: 从MQ保存数据到数据库，失败返回null
	 * @param: @param msg
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: CommitchainData      
	 * @throws
	 */
	CommitchainData savaCdFromMq(String msg) throws Exception;



	/**
	 * 
	 * @Title: getTodoCommitchainData   
	 * @Description: 获取所有待上链数据
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: List<CommitchainData>      
	 * @throws
	 */
	List<CommitchainData> getTodoCommitchainData() throws Exception;



	/**
	 * 
	 * @Title: getDoingCommitchainData   
	 * @Description: 获取所有已准备上链数据
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: List<CommitchainData>      
	 * @throws
	 */
	List<CommitchainData> getDoingCommitchainData() throws Exception;


	/**
	 * 
	 * @Title: handlerTodoCommitchainData   
	 * @Description: 处理待上链数据,做数据准备
	 * @param: @param commitchainData
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: CommitchainData      
	 * @throws
	 */
	CommitchainData handlerTodoCommitchainData(CommitchainData commitchainData) throws Exception;
	
	
}
