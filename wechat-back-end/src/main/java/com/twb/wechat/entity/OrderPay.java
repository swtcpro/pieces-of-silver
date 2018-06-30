package com.twb.wechat.entity;


import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "order_pay") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class OrderPay  {

	public static String STATE_PREPAY = "1";
	public static String STATE_PAY = "2";
	public static String STATE_PAY_COMMITCHAIN = "3";
	public static String STATE_PAY_COMMITCHAIN_SUCCESS = "4";
	public static String STATE_PAY_COMMITCHAIN_FAIL = "5";
	
	
	
	
    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;
    
    private String openid ; //微信openid
    private String jingtumaddress; //'井通地址
    private String totalFee 	; //订单总金额，单位为分
    private String body 	; //商品简单描述
    private String prepayId  	; //微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
    private String state ; //状态：1.待支付2.已支付3.支付完成已上链4.支付完成上链失败已退款5.已取消
    private String outTradeNo = "";//订单号
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime	; //创建时间

    @Temporal(TemporalType.TIMESTAMP)
    private Date payTime 	; //支付时间
    
    private String commitchainMsg   = "";//返回消息
    private String messageId  = "";//消息id

    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate ;//上链发送时间'
    private String commitchainHash  = "";//上链hash
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageCompleteDate  ;//上链结束时间'
    
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}

	public String getJingtumaddress()
	{
		return jingtumaddress;
	}

	public void setJingtumaddress(String jingtumaddress)
	{
		this.jingtumaddress = jingtumaddress;
	}

	public String getTotalFee()
	{
		return totalFee;
	}

	public void setTotalFee(String totalFee)
	{
		this.totalFee = totalFee;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getPrepayId()
	{
		return prepayId;
	}

	public void setPrepayId(String prepayId)
	{
		this.prepayId = prepayId;
	}

	public String getState()
	{
		return state;
	}
	
	public String getStateName()
	{
//		public static String STATE_PREPAY = "1";
//		public static String STATE_PAY = "2";
//		public static String STATE_PAY_COMMITCHAIN = "3";
//		public static String STATE_PAY_COMMITCHAIN_SUCCESS = "4";
//		public static String STATE_PAY_COMMITCHAIN_FAIL = "5";
		if(STATE_PREPAY.equals(state))
		{
			return "未支付";
		}
		else if(STATE_PAY.equals(state))
		{
			return "支付成功,CNT待转账";
		}
		else if(STATE_PAY_COMMITCHAIN.equals(state))
		{
			return "支付成功,CNT待转账";
		}
		else if(STATE_PAY_COMMITCHAIN_SUCCESS.equals(state))
		{
			return "CNT已转账成功";
		}
		else if(STATE_PAY_COMMITCHAIN_FAIL.equals(state))
		{
			return "支付成功,CNT待转账";
		}
		return "未支付";
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getCommitchainHash()
	{
		return commitchainHash;
	}

	public void setCommitchainHash(String commitchainHash)
	{
		this.commitchainHash = commitchainHash;
	}

	public String getOutTradeNo()
	{
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo)
	{
		this.outTradeNo = outTradeNo;
	}

	public String getCommitchainMsg()
	{
		return commitchainMsg;
	}

	public void setCommitchainMsg(String commitchainMsg)
	{
		this.commitchainMsg = commitchainMsg;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public Date getMessageDate()
	{
		return messageDate;
	}

	public void setMessageDate(Date messageDate)
	{
		this.messageDate = messageDate;
	}

	public Date getPayTime()
	{
		return payTime;
	}

	public void setPayTime(Date payTime)
	{
		this.payTime = payTime;
	}

	public Date getMessageCompleteDate()
	{
		return messageCompleteDate;
	}

	public void setMessageCompleteDate(Date messageCompleteDate)
	{
		this.messageCompleteDate = messageCompleteDate;
	}
    
    
    
    
    
    

    
    
    

}
