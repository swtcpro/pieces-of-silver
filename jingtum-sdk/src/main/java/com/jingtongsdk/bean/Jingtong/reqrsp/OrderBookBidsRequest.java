package com.jingtongsdk.bean.Jingtong.reqrsp;

import com.jingtongsdk.bean.Jingtong.JingtongBaseRequest;
import com.jingtongsdk.utils.JingtongRequstConstants;

//获得货币对的买单列表
public class OrderBookBidsRequest  extends JingtongBaseRequest
{
	private String base;//基准货币（currency+counterparty），兼容swt+counterparty
	private String counter;//目标货币（currency+counterparty），兼容swt+counterparty
	private Integer results_per_page;//返回的每页数据量，默认每页买卖单各10项）
	private Integer page;//页码，默认从第一页开始
	
	
	@Override
	public String requestUrl()
	{
		return "/v2/order_book/bids/{:base}/{:counter}";
	}
	@Override
	public String requestType()
	{
		
		return JingtongRequstConstants.GET_TYPE;
	}
	public String getBase()
	{
		return base;
	}
	public void setBase(String base)
	{
		this.base = base;
	}
	public String getCounter()
	{
		return counter;
	}
	public void setCounter(String counter)
	{
		this.counter = counter;
	}
	public Integer getResults_per_page()
	{
		return results_per_page;
	}
	public void setResults_per_page(Integer results_per_page)
	{
		this.results_per_page = results_per_page;
	}
	public Integer getPage()
	{
		return page;
	}
	public void setPage(Integer page)
	{
		this.page = page;
	}
	
	
}
