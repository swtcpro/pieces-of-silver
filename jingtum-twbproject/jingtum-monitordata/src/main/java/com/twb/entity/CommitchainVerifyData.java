package com.twb.entity;

import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity // 告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "commitchain_verify_data") // @Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class CommitchainVerifyData
{

	public static final String CHECKFLAG_TOCHECK = "1";// 1.待比对验证
	public static final String CHECKFLAG_SUCCESS = "2";// 2.验证成功
	public static final String CHECKFLAG_ERROR = "3";// 3验证异常

	@Id // 这是一个主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
	private Integer id;
	
	private Integer cid;//上链数据表主键

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
	private String checkflag=""; // 验证 1.待比对验证 2.验证成功3验证异常
	private String checkmsg=""; // 比对信息

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

	public String getCheckflag()
	{
		return checkflag;
	}

	public void setCheckflag(String checkflag)
	{
		this.checkflag = checkflag;
	}

	public String getCheckmsg()
	{
		return checkmsg;
	}

	public void setCheckmsg(String checkmsg)
	{
		this.checkmsg = checkmsg;
	}

	public Integer getCid()
	{
		return cid;
	}

	public void setCid(Integer cid)
	{
		this.cid = cid;
	}
	
}
