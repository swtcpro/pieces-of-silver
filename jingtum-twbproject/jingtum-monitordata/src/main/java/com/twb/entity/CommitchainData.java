package com.twb.entity;

import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity // 告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "commitchain_data") // @Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class CommitchainData
{

	public static final String RESPONSE_FLAG_TODO = "1";// 1.待上链
	public static final String RESPONSE_FLAG_SUCCESS = "2";// 2.上链成功
	public static final String RESPONSE_FLAG_FAIL = "3";// 3.上链失败
	
	public static final String CHECK_FLAG_TODO = "1";// 1.待验证
	public static final String CHECK_FLAG_SUCCESS = "2";// 2.已验证成功 
	
	public static final String BUSINESS_FLAG_TODO = "1";// 1.待反馈
	public static final String BUSINESS_FLAG_DONE = "2";// 2.已反馈
	public static final String BUSINESS_FLAG_NONE = "3";// 3.无需反馈

	@Id // 这是一个主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
	private Integer id;

	private String businessId = ""; // 交易备注
	private String memos = ""; // 交易备注
	private String counterparty = ""; // 交易对家
	private String amountvalue = ""; // 交易金额
	private String amountcurrency = ""; // 货币类型
	private String amountissuer = ""; // 货币发行方

	private String responseFlag = "";// 1.待响应 2.响应成功 3.响应失败
	private String responseMsg = "";//响应成功失败等信息
	private String responseHash = "";//响应的hash
	@Temporal(TemporalType.TIMESTAMP)
	private Date responseData;//响应完成的时间
	
	private String checkFlag = "";//1.待验证 2.已验证成功 
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkData;// 验证完成时间
	private String businessTopic = "";//结果反馈业务系统MQ主题
	private String businessTag = "";//结果反馈业务系统TAG
	private String businessFlag = "";//1.待反馈 2.已反馈3.无需反馈
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getBusinessId()
	{
		return businessId;
	}
	public void setBusinessId(String businessId)
	{
		this.businessId = businessId;
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
	public String getResponseFlag()
	{
		return responseFlag;
	}
	public void setResponseFlag(String responseFlag)
	{
		this.responseFlag = responseFlag;
	}
	public String getResponseMsg()
	{
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg)
	{
		this.responseMsg = responseMsg;
	}
	public String getResponseHash()
	{
		return responseHash;
	}
	public void setResponseHash(String responseHash)
	{
		this.responseHash = responseHash;
	}
	
	public String getCheckFlag()
	{
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag)
	{
		this.checkFlag = checkFlag;
	}
	
	public Date getResponseData()
	{
		return responseData;
	}
	public void setResponseData(Date responseData)
	{
		this.responseData = responseData;
	}
	public Date getCheckData()
	{
		return checkData;
	}
	public void setCheckData(Date checkData)
	{
		this.checkData = checkData;
	}
	public String getBusinessTopic()
	{
		return businessTopic;
	}
	public void setBusinessTopic(String businessTopic)
	{
		this.businessTopic = businessTopic;
	}
	public String getBusinessTag()
	{
		return businessTag;
	}
	public void setBusinessTag(String businessTag)
	{
		this.businessTag = businessTag;
	}
	public String getBusinessFlag()
	{
		return businessFlag;
	}
	public void setBusinessFlag(String businessFlag)
	{
		this.businessFlag = businessFlag;
	}
	
	

}
