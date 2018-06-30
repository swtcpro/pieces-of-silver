package com.twb.wechat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.twb.wechat.entity.OrderPay;


//继承JpaRepository来完成对数据库的操作
public interface OrderPayRepository extends JpaRepository<OrderPay,Integer>,JpaSpecificationExecutor<OrderPay>{
	
	
	@Query(value="select o from OrderPay o where openid=:openid")
	public List<OrderPay> getOrderPayByOpenid(@Param("openid") String openid) throws Exception;
	
	
	  
}
