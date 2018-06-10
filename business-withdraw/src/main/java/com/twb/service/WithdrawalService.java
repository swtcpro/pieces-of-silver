package com.twb.service;

import java.util.List;

import com.aliyun.openservices.ons.api.Message;
import com.twb.commondata.data.DistributeMqData;
import com.twb.entity.Withdrawal;
import com.twb.entity.WithdrawalCommitchain;

public interface WithdrawalService {
	
	/**
	 * 
	 * @Title: getTodoWithdrawal   
	 * @Description: 取出待提现数据
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: List<Withdrawal>      
	 * @throws
	 */
	List<Withdrawal> getTodoWithdrawal() throws Exception;
	
	
	
	List<WithdrawalCommitchain> getTodoWithdrawalCommitchain() throws Exception;

	
	/**
	 * @return 
	 * 
	 * @Title: doingWithdrawal   
	 * @Description: 处理提现
	 * @param: @param Withdrawal
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	WithdrawalCommitchain doingWithdrawal(Withdrawal withdrawal) throws Exception;

	/**
	 * 
	 * @Title: saveWithdrawal   
	 * @Description: 保存提现信息
	 * @param: @param dmd
	 * @param: @throws Exception      
	 * @return: Withdrawal      
	 * @throws
	 */
	Withdrawal saveWithdrawal(DistributeMqData dmd,Message message) throws Exception;

	/**
	 * 
	 * @Title: withdrawalCommitChain   
	 * @Description: 提现结果上链
	 * @param: @param wc      
	 * @return: void      
	 * @throws
	 */
	void withdrawalCommitChain(WithdrawalCommitchain wc) throws Exception;
}
