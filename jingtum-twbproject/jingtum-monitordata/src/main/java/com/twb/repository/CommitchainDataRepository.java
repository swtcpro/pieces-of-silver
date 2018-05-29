package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.twb.entity.CommitchainData;



//继承JpaRepository来完成对数据库的操作
public interface CommitchainDataRepository extends JpaRepository<CommitchainData,Integer>{
	
	@Query(value="select o from CommitchainData o where commitchainFlag=:commitchainFlag")
	public List<CommitchainData> getAllCommitchainDataByState(@Param("commitchainFlag") String commitchainFlag) throws Exception;
	
	@Query(value="select o from CommitchainData o where id=:id")
	public CommitchainData getCommitchainDataById(@Param("id") Integer id) throws Exception;
	
}
