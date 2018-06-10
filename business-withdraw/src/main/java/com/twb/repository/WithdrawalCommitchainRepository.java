package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.twb.entity.WithdrawalCommitchain;


//继承JpaRepository来完成对数据库的操作
public interface WithdrawalCommitchainRepository extends JpaRepository<WithdrawalCommitchain ,Integer>{
	
	@Query(value="select o from WithdrawalCommitchain o where commitchainState=:commitchainState")
	public List<WithdrawalCommitchain> getWithdrawalCommitchainByState(@Param("commitchainState") String commitchainState) throws Exception;
	
	
	
	  
}
