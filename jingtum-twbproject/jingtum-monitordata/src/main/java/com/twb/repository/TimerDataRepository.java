package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twb.entity.TimerData;


//继承JpaRepository来完成对数据库的操作
public interface TimerDataRepository extends JpaRepository<TimerData,Integer>{
	@Query(value="select o from TimerData o where checkflag = '"+TimerData.CHECKFLAG_TOCHECK+"' or checkflag = '"+TimerData.CHECKFLAG_TOENSURE+"'")
	public List<TimerData> getCheckTranList() throws Exception;
	
	@Query(value="select o from TimerData o where id = (select max(id) from TimerData)")
	public TimerData getLastTran() throws Exception;
	
	
	  
  
	
}
