package com.twb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twb.entity.WithdrawalDeallog;


//继承JpaRepository来完成对数据库的操作
public interface WithdrawalDeallogRepository extends JpaRepository<WithdrawalDeallog,Integer>{
	
}
