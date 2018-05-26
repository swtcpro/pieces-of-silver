package com.twb.service;

import java.util.List;

import com.twb.entity.CommitchainData;


public interface CommitchainDataService {
	
	
	/**
	 * 
	 * @Title: handlerTodoCommitchainData   
	 * @Description: 处理待上链数据,做数据准备
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: List     
	 * @throws
	 */
	List handlerTodoCommitchainData(List<CommitchainData> list) throws Exception;
	
	
	
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

}
