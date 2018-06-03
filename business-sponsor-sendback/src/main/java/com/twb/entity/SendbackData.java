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


@Entity
@Table(name = "sendback_data") // 赞赏数据表
public class SendbackData 
{

	public static final String HANDLE_FLAG_TODO = "1";// 1待处理
	public static final String HANDLE_FLAG_SUCCESS = "2";// 2.处理成功
	public static final String HANDLE_FLAG_FAIL = "3";// 3 处理失败

	
	
	@Id // 这是一个主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
	private Integer id;

	private String bussinessid ;
	
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
	
	
	private String messageId ="";//消息id
	private String messageTopic ="";// 主题
	private String messageTag  ="";//标签
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "message_date")
	private Date messageDate ; // 消息队列发送时间
	
	private String handleFlag ="";//处理状态 1.待处理 2.处理成功3.处理失败
	private String handleMsg ="";//处理信息
	private String commitchainMessageId ="";//上链消息id

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

	public String getHandleFlag()
	{
		return handleFlag;
	}

	public void setHandleFlag(String handleFlag)
	{
		this.handleFlag = handleFlag;
	}

	public String getHandleMsg()
	{
		return handleMsg;
	}

	public void setHandleMsg(String handleMsg)
	{
		this.handleMsg = handleMsg;
	}

	public String getCommitchainMessageId()
	{
		return commitchainMessageId;
	}

	public void setCommitchainMessageId(String commitchainMessageId)
	{
		this.commitchainMessageId = commitchainMessageId;
	}

	public String getBussinessid()
	{
		return bussinessid;
	}

	public void setBussinessid(String bussinessid)
	{
		this.bussinessid = bussinessid;
	}

	
	
}
