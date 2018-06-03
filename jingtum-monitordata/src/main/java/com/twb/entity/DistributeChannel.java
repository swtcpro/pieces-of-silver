package com.twb.entity;

import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity // 告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "distribute_channel") // @Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class DistributeChannel
{

	public static final String TYPE_SENT = "sent";// sent出账
	public static final String TYPE_RECEIVED  = "received";//received 入账
	
	public static final String FLAGTYPE_ALL  = "1";//所有的都发送
	public static final String FLAGTYPE_OTHERS_NONE  = "2";//其他业务系统没有发送
	public static final String FLAGTYPE_MEMOS_STARTWITH_FLAG  = "3";// memos开头为flag
	
	public static final String STATE_CLOSE  = "0";//关闭
	public static final String STATE_OPEN  = "1";//打开

	@Id // 这是一个主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
	private Integer id;

	private String type; // sent出账 received 入账
	private String flagtype;//发送消息条件类型 1所有，2其他业务系统都没处理才发送，3 memos开头为flag
	private String flag ; // 发送消息条件  
	private String topic ; //  阿里云消息队列主题
	private String tag; // 阿里云消息队列标签
	private String state  ; // 开关，0关闭，1.打开
	
	private String destSystem ;// 目的系统
	private String remark ;// 备注
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getFlag()
	{
		return flag;
	}
	public void setFlag(String flag)
	{
		this.flag = flag;
	}
	public String getTopic()
	{
		return topic;
	}
	public void setTopic(String topic)
	{
		this.topic = topic;
	}
	public String getTag()
	{
		return tag;
	}
	public void setTag(String tag)
	{
		this.tag = tag;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getFlagtype()
	{
		return flagtype;
	}
	public void setFlagtype(String flagtype)
	{
		this.flagtype = flagtype;
	}
	public String getDestSystem()
	{
		return destSystem;
	}
	public void setDestSystem(String destSystem)
	{
		this.destSystem = destSystem;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	
	
}
