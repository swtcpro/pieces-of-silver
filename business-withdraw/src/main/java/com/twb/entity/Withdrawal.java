package com.twb.entity;


import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "withdrawal") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class Withdrawal {

	public static final String STATE_WITHDRAWAL_TODO = "1";//1待提现
	public static final String STATE_WITHDRAWAL_SUCCESS = "2";//3提现成功
	public static final String STATE_WITHDRAWAL_BACK = "3";//回退
	public static final String STATE_WITHDRAWAL_FAIL_BACK = "4";//提现失败回退
	public static final String STATE_WITHDRAWAL_EXCEPTION = "5";//提现异常
	
	public static final String WITHDRAWALTYPE_WECHAT = "1";//1微信
	public static final String WITHDRAWALTYPE_ALIPAY = "2";//2支付宝

	public static final String DATASOURCE_SOCKET = "1";//1socket监听 
	public static final String DATASOURCE_SCHEDULING = "2";//2定时任务
	
	public static final String CHECKFLAG_TOBE = "1";//1.待验证
	public static final String CHECKFLAG_SUCCESS = "2";// 2.验证成功
	public static final String CHECKFLAG_FAIL = "3";// 2.验证失败
	
    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
    private Date date; //交易时间
	
    private String hash; //交易hash
    private String fee; //交易费用，井通计价
    private String memos; //交易备注
    private String counterparty; //	交易对家
    private String amountvalue; //交易金额
    private String amountcurrency; //货币类型
    private String amountissuer; //货币发行方

    
    private String bussinessid ;//业务主键
    
    private String messageId;//消息id
    private String messageTopic;//消息队列主题
    private String messageTag;//消息队列标签
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;//消息接收时间
    
    private String state; //提现状态，1待提现，2返现成功3.回退4返现失败回退5返现异常
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
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getBussinessid()
	{
		return bussinessid;
	}
	public void setBussinessid(String bussinessid)
	{
		this.bussinessid = bussinessid;
	}
	public String getMessageId()
	{
		return messageId;
	}
	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}
	public String getMessageTopic()
	{
		return messageTopic;
	}
	public void setMessageTopic(String messageTopic)
	{
		this.messageTopic = messageTopic;
	}
	public String getMessageTag()
	{
		return messageTag;
	}
	public void setMessageTag(String messageTag)
	{
		this.messageTag = messageTag;
	}
	public Date getMessageDate()
	{
		return messageDate;
	}
	public void setMessageDate(Date messageDate)
	{
		this.messageDate = messageDate;
	}
    

    
    
    

}
