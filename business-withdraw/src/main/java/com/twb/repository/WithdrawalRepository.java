package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.twb.entity.Withdrawal;


//继承JpaRepository来完成对数据库的操作
public interface WithdrawalRepository extends JpaRepository<Withdrawal,Integer>{
	
	
	@Query(value="select o from Withdrawal o where state=:state")
	public List<Withdrawal> getAllWithdrawalByState(@Param("state") String state) throws Exception;
	
	
	  
}
