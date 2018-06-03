package com.twb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twb.entity.SocketData;
import com.twb.entity.TimerData;


//继承JpaRepository来完成对数据库的操作
public interface SocketDataRepository extends JpaRepository<SocketData,Integer>{
	
  
	/**
	 * 
	 * @Title: getSocketDataBefore15   
	 * @Description: 获取15分钟内数据
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: List<SocketData>      
	 * @throws
	 */
	@Query(value="select * from socket_data WHERE date > DATE_SUB(NOW(), INTERVAL 15 MINUTE)",nativeQuery = true)
	public List<SocketData> getSocketDataIn15m() throws Exception;
	
	@Query(value="select * from socket_data WHERE date > DATE_SUB(NOW(), INTERVAL 1 DAY)",nativeQuery = true)
	public List<SocketData> getSocketData1day() throws Exception;
	
	@Query(value="select o from SocketData o where flag = '"+SocketData.FLAG_TODO+"'")
	public List<SocketData> getTodoSocketData() throws Exception;
	
	@Query(value="select o from SocketData o")
	public List<SocketData> getAllSocketData() throws Exception;
	
	
	
}
