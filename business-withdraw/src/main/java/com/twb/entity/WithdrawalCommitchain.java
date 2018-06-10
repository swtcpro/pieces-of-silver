package com.twb.entity;


import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "withdrawal_commitchain") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class WithdrawalCommitchain  {

	
	public static final String COMMITCHAIN_STATE_TODO = "1";//1待响应
	public static final String COMMITCHAIN_STATE_EXCEPTION = "2";//2处理异常
	public static final String COMMITCHAIN_STATE_SUCCESS = "3";//3处理成功
	public static final String COMMITCHAIN_STATE_FAIL = "4";//4处理失败
	
	
    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;
    
    @Column(name = "withdrawal_id")
    private Integer withdrawalId;

    @Column(name = "withdrawal_deallog_id")
    private Integer withdrawalDeallogId;
    
    private String amountvalue; //交易金额
    private String amountcurrency; //货币类型
    private String amountissuer; //货币发行方
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
    private Date date; //数据插入时间
	
	private String reason ;//回退原因
//	
	private String hash; //交易hash
	
	private String counterparty; //	交易对家
	
	private String commitchainState;//响应状态，1待上链 2处理异常3处理成功4处理失败
	private String commitchainMessageId;//上链消息id
	@Temporal(TemporalType.TIMESTAMP)
	private Date commitchainData;// 上链提交时间
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getWithdrawalId()
	{
		return withdrawalId;
	}
	public void setWithdrawalId(Integer withdrawalId)
	{
		this.withdrawalId = withdrawalId;
	}
	public Integer getWithdrawalDeallogId()
	{
		return withdrawalDeallogId;
	}
	public void setWithdrawalDeallogId(Integer withdrawalDeallogId)
	{
		this.withdrawalDeallogId = withdrawalDeallogId;
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
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public String getCounterparty()
	{
		return counterparty;
	}
	public void setCounterparty(String counterparty)
	{
		this.counterparty = counterparty;
	}
	public String getCommitchainState()
	{
		return commitchainState;
	}
	public void setCommitchainState(String commitchainState)
	{
		this.commitchainState = commitchainState;
	}
	public String getCommitchainMessageId()
	{
		return commitchainMessageId;
	}
	public void setCommitchainMessageId(String commitchainMessageId)
	{
		this.commitchainMessageId = commitchainMessageId;
	}
	public Date getCommitchainData()
	{
		return commitchainData;
	}
	public void setCommitchainData(Date commitchainData)
	{
		this.commitchainData = commitchainData;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	public String getHash()
	{
		return hash;
	}
	public void setHash(String hash)
	{
		this.hash = hash;
	}
    
    

}
