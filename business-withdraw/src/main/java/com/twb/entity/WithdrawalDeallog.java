package com.twb.entity;


import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "withdrawal_deallog") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class WithdrawalDeallog {

	
	public static final String RETURNCODE_SUCCESS = "1";
	public static final String RETURNCODE_FAIL = "2";
	
	
    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;

    private Integer withdrawalId; //
    
    private String payeeAccount ; //提现账户
    
    private String amount; //提现金额,单位，元
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time")
    private Date time; //提现处理时间
    
	
	@Column(name = "return_code ")
    private String returnCode; //处理结果，1成功，2失败
	
    private String returnMsg; //处理信息
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date payDate; //支付时间
	
    private String orderId; //商户订单号

    private String orderWxid; //微信订单号
	
		
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


	public String getPayeeAccount()
	{
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount)
	{
		this.payeeAccount = payeeAccount;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(Date time)
	{
		this.time = time;
	}


	public String getReturnCode()
	{
		return returnCode;
	}

	public void setReturnCode(String returnCode)
	{
		this.returnCode = returnCode;
	}

	public String getReturnMsg()
	{
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg)
	{
		this.returnMsg = returnMsg;
	}

	

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getOrderWxid()
	{
		return orderWxid;
	}

	public void setOrderWxid(String orderWxid)
	{
		this.orderWxid = orderWxid;
	}

	public Date getPayDate()
	{
		return payDate;
	}

	public void setPayDate(Date payDate)
	{
		this.payDate = payDate;
	}

	
    
    
    

}
