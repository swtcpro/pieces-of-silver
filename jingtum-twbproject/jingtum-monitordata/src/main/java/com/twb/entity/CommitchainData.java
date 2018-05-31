package com.twb.entity;

import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity // 告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "commitchain_data") // @Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class CommitchainData
{

	public static final String COMMITCHAIN_FLAG_TODO = "1";// 1.待上链
	public static final String COMMITCHAIN_FLAG_DOING = "2";// 2.准备上链
	public static final String COMMITCHAIN_FLAG_SUCCESS = "3";// 3.上链成功
	public static final String COMMITCHAIN_FLAG_FAIL = "4";// 4.上链失败
	public static final String COMMITCHAIN_FLAG_CHECKFAIL = "5";// 4.数据验证失败
	
	public static final String CHECK_FLAG_TODO = "1";// 1.待验证
	public static final String CHECK_FLAG_SUCCESS = "2";// 2.已验证上链成功
	public static final String CHECK_FLAG_FAIL = "3";// 3.已验证上链失败
	public static final String CHECK_FLAG_ERROR = "4";// 4.验证异常
	
	public static final String BUSINESS_FLAG_TODO = "1";// 1.待反馈
	public static final String BUSINESS_FLAG_SUCCESS = "2";// 2.已反馈,成功
	public static final String BUSINESS_FLAG_FAIL = "3";// 3.已反馈，失败
	public static final String BUSINESS_FLAG_NONE = "4";// 4.无需反馈

	@Id // 这是一个主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
	private Integer id;

	private String businessId = ""; // 业务id
	private String memos = ""; // 交易备注
	private String counterparty = ""; // 交易对家
	private String amountvalue = ""; // 交易金额
	private String amountcurrency = ""; // 货币类型
	private String amountissuer = ""; // 货币发行方

	private String commitchainFlag = "";// 1.待上链 2.准备上链 3.上链成功 4.上链失败
	private String commitchainMsg = "";//上链成功失败等信息
	private String commitchainHash = "";//上链的hash
	@Temporal(TemporalType.TIMESTAMP)
	private Date commitchainDate;//上链的时间
	
	private String checkFlag = "";//1.待验证 2.已验证成功 3.已验证失败4.已验证异常
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;// 验证完成时间
	private String businessTopic = "";//结果反馈业务系统MQ主题
	private String businessTag = "";//结果反馈业务系统TAG
	private String businessFlag = "";//1.待反馈 2.已反馈3.无需反馈
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date  businessMqdate;
	private String business_mqid   = "";
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
	
	public String getCheckFlag()
	{
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag)
	{
		this.checkFlag = checkFlag;
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
	public Date getCheckDate()
	{
		return checkDate;
	}
	public void setCheckDate(Date checkDate)
	{
		this.checkDate = checkDate;
	}
	public String getCommitchainFlag()
	{
		return commitchainFlag;
	}
	public void setCommitchainFlag(String commitchainFlag)
	{
		this.commitchainFlag = commitchainFlag;
	}
	public String getCommitchainMsg()
	{
		return commitchainMsg;
	}
	public void setCommitchainMsg(String commitchainMsg)
	{
		this.commitchainMsg = commitchainMsg;
	}
	public String getCommitchainHash()
	{
		return commitchainHash;
	}
	public void setCommitchainHash(String commitchainHash)
	{
		this.commitchainHash = commitchainHash;
	}
	public Date getCommitchainDate()
	{
		return commitchainDate;
	}
	public void setCommitchainDate(Date commitchainDate)
	{
		this.commitchainDate = commitchainDate;
	}
	public Date getBusinessMqdate()
	{
		return businessMqdate;
	}
	public void setBusinessMqdate(Date businessMqdate)
	{
		this.businessMqdate = businessMqdate;
	}
	public String getBusiness_mqid()
	{
		return business_mqid;
	}
	public void setBusiness_mqid(String business_mqid)
	{
		this.business_mqid = business_mqid;
	}
	
	

}
