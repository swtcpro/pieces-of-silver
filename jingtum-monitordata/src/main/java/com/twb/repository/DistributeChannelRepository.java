package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twb.entity.DistributeChannel;



//继承JpaRepository来完成对数据库的操作
public interface DistributeChannelRepository extends JpaRepository<DistributeChannel,Integer>{
	
	@Query(value="select o from DistributeChannel o where state = '"+DistributeChannel.STATE_OPEN+"'")
	public List<DistributeChannel> getAllDistributeChannel() throws Exception;
	
}
