package com.twb.wechat.entity;


import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "wxopenid") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class Wxopenid {

	
    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
    private Date date; //创建时间
	
    private String openidxff ; //新风范openid
    private String openiddsb ; //云大商帮openid
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public String getOpenidxff()
	{
		return openidxff;
	}
	public void setOpenidxff(String openidxff)
	{
		this.openidxff = openidxff;
	}
	public String getOpeniddsb()
	{
		return openiddsb;
	}
	public void setOpeniddsb(String openiddsb)
	{
		this.openiddsb = openiddsb;
	}
   
    
    

    
    
    

}
