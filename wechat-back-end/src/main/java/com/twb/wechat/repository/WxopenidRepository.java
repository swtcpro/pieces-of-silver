package com.twb.wechat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twb.wechat.entity.Wxopenid;


//继承JpaRepository来完成对数据库的操作
public interface WxopenidRepository extends JpaRepository<Wxopenid,Integer>{
	
	
	@Query(value="select o from Wxopenid o")
	public List<Wxopenid> getAllWxopenid() throws Exception;
	
	
	  
}
