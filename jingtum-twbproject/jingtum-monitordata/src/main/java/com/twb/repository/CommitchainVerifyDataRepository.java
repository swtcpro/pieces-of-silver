package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twb.entity.CommitchainVerifyData;



//继承JpaRepository来完成对数据库的操作
public interface CommitchainVerifyDataRepository extends JpaRepository<CommitchainVerifyData,Integer>{
	
//	@Query(value="select o from CommitchainVerifyData o where state = '"+CommitchainVerifyData.STATE_OPEN+"'")
//	public List<CommitchainVerifyData> getAllCommitchainVerifyData() throws Exception;
	
}
