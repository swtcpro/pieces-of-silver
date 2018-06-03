package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.Exclude;
import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

//查询支付选择
public class PaymentsChoicesRequest extends JingtongBaseRequest
{
	@Exclude
	private String source_address;//支付方的井通地址
	@Exclude
	private String destination_address;//接收方的井通地址
	@Exclude
	private String amount;//数值+货币+货币发行方，例如：1.00+AAA+jMhLAPaNFo288
//							PNo5HMC37kg6ULjJg8vPf注意：支付SWT不需要选择，所
//							以查询SWT的支付选择是不存在的
	
	public String requestUrl()
	{
		return "/v2/accounts/{:source_address}/payments/choices/{:destination_address}/{:amount}";
	}
	
	public String requestType()
	{
		return JingtongRequstConstants.GET_TYPE;
	}

	public String getSource_address()
	{
		return source_address;
	}

	public void setSource_address(String source_address)
	{
		this.source_address = source_address;
	}

	public String getDestination_address()
	{
		return destination_address;
	}

	public void setDestination_address(String destination_address)
	{
		this.destination_address = destination_address;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	
	
	
}
