package com.twb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twb.entity.CommitchainData;



//继承JpaRepository来完成对数据库的操作
public interface CommitchainDataRepository extends JpaRepository<CommitchainData,Integer>{
	
}
