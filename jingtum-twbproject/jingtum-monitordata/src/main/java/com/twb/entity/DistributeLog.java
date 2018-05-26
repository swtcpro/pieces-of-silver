package com.twb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jingtongsdk.bean.Jingtong.Exclude;

//使用JPA注解配置映射关系
@Entity // 告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "distribute_log") // @Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class DistributeLog implements Cloneable
{

	public static final String DISTRIBUTETYPE_SOCKET = "1";// 1socket
	public static final String DISTRIBUTETYPE_TIMER = "2";// 2.定时任务

	public static final String SENDRESULT_FAIL = "0";// 2.定时任务
	public static final String SENDRESULT_SUCCESS = "1";// 2.定时任务
	
	
	@Id // 这是一个主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date; // 交易时间

	private String hash=""; // 交易hash
	private String type=""; // 类型
	private String result=""; // 结果
	private String fee=""; // 交易费用，井通计价
	private String memos=""; // 交易备注
	private String counterparty=""; // 交易对家
	private String amountvalue=""; // 交易金额
	private String amountcurrency=""; // 货币类型
	private String amountissuer=""; // 货币发行方
	
//	private String flag; // 1.待分发 2.已分发
	
	private String distributeType="";// 1.socket数据分发 2.定时任务数据分发
	@Exclude
	private String messageid ="";//消息id
	@Exclude
	private String topic="";// 主题
	@Exclude
	private String tag ="";//标签
	
	@Exclude
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "message_date")
	private Date messageDate ; // 消息队列发送时间
	
	@Exclude
	private String sendResult="";//发送结果

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

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getFee()
	{
		return fee;
	}

	public void setFee(String fee)
	{
		this.fee = fee;
	}

	public String getMemos()
	{
		return memos;
	}

	public void setMemos(String memos)
	{
		
		this.memos = memos;
	}

	public String getCounterparty()
	{
		return counterparty;
	}

	public void setCounterparty(String counterparty)
	{
		this.counterparty = counterparty;
	}

	public String getAmountvalue()
	{
		return amountvalue;
	}

	public void setAmountvalue(String amountvalue)
	{
		this.amountvalue = amountvalue;
	}

	public String getAmountcurrency()
	{
		return amountcurrency;
	}

	public void setAmountcurrency(String amountcurrency)
	{
		this.amountcurrency = amountcurrency;
	}

	public String getAmountissuer()
	{
		return amountissuer;
	}

	public void setAmountissuer(String amountissuer)
	{
		this.amountissuer = amountissuer;
	}

	public String getDistributeType()
	{
		return distributeType;
	}

	public void setDistributeType(String distributeType)
	{
		this.distributeType = distributeType;
	}

	public String getMessageid()
	{
		return messageid;
	}

	public void setMessageid(String messageid)
	{
		this.messageid = messageid;
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

	public Date getMessageDate()
	{
		return messageDate;
	}

	public void setMessageDate(Date messageDate)
	{
		this.messageDate = messageDate;
	}

	public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
    }

	public String getSendResult()
	{
		return sendResult;
	}

	public void setSendResult(String sendResult)
	{
		this.sendResult = sendResult;
	}
	
	
}
