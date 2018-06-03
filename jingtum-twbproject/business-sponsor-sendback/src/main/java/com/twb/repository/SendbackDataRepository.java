package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twb.entity.SendbackData;


//继承JpaRepository来完成对数据库的操作
public interface SendbackDataRepository extends JpaRepository<SendbackData,Integer>{
	@Query(value="select o from SendbackData o where handleFlag = '"+SendbackData.HANDLE_FLAG_TODO+"'")
	public List<SendbackData> getTodoSendbackData() throws Exception;
	
	
	
	  
  
	
}
