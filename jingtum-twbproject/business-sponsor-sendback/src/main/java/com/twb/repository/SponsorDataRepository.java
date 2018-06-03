package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twb.entity.SponsorData;



//继承JpaRepository来完成对数据库的操作
public interface SponsorDataRepository extends JpaRepository<SponsorData,Integer>{
	
	@Query(value="select o from SponsorData o where handleFlag = '"+SponsorData.HANDLE_FLAG_TODO+"'")
	public List<SponsorData> getTodoSponsorData() throws Exception;
  
	
	
	
	
}
