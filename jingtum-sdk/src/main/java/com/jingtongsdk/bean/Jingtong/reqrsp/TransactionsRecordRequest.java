package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

public class TransactionsRecordRequest extends JingtongBaseRequest
{
	private String address;//井通钱包地址
	private Integer results_per_page=10; //返回的每页数据量，默认每页10项
	private Marker marker;
	
	public String requestUrl()
	{
		return "/v2/accounts/{:address}/transactions?results_per_page={:results_per_page}&marker={:marker}";
	}
	
	public String requestType()
	{
		return JingtongRequstConstants.GET_TYPE;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Integer getResults_per_page()
	{
		return results_per_page;
	}

	public void setResults_per_page(Integer results_per_page)
	{
		this.results_per_page = results_per_page;
	}

	public Marker getMarker()
	{
		return marker;
	}

	public void setMarker(Marker marker)
	{
		this.marker = marker;
	}

	
}
