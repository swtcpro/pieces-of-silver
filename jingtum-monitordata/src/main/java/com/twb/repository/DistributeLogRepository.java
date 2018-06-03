package com.twb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twb.entity.DistributeLog;



//继承JpaRepository来完成对数据库的操作
public interface DistributeLogRepository extends JpaRepository<DistributeLog,Integer>{
	
}
