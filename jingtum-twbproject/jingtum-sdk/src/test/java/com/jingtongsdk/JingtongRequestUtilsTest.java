package com.jingtongsdk;

import java.util.List;

import com.jingtongsdk.bean.JingtongResponse;
import com.jingtongsdk.bean.Jingtong.reqrsp.Amount;
import com.jingtongsdk.bean.Jingtong.reqrsp.BalancesRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.Marker;
import com.jingtongsdk.bean.Jingtong.reqrsp.Order;
import com.jingtongsdk.bean.Jingtong.reqrsp.OrderBookAsksRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.OrderBookBidsRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.OrderBookListRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.OrdersInfoRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.OrdersListRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.OrdersSubmitRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.Payment;
import com.jingtongsdk.bean.Jingtong.reqrsp.PaymentHistoryRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.PaymentInformationRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.PaymentsChoicesRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.PaymentsTransferRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.TransactionsInfoRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.TransactionsRecordRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.WalletNewRequest;
import com.jingtongsdk.bean.Jingtong.reqrsp.WalletNewResponse;
import com.jingtongsdk.utils.JingtongRequestUtils;

import junit.framework.TestCase;

public class JingtongRequestUtilsTest extends TestCase
{

	public void testGetResponseClass() throws Exception
	{
		WalletNewRequest wnr = new WalletNewRequest();
		Class classs = JingtongRequestUtils.getResponseClass(wnr);

		assertEquals(classs.getName(), WalletNewResponse.class.getName());
	}

	public void testObjToString()
	{
		OrdersSubmitRequest or = new OrdersSubmitRequest();
		or.setAddress("1");
		or.setSecret("2");
		Order o = new Order();
		o.setAmount("4");
		o.setPair("3");
		or.setOrder(o);
		System.out.println(or);
	}

	public void testGgetFields() throws Exception
	{
		List list1 = JingtongRequestUtils.getFields("accounts/{:address}/balances");
		assertEquals(1, list1.size());
		System.out.println(list1);
		list1 = JingtongRequestUtils.getFields("a{:address}ccounts/{:address}/{:address}balances");
		assertEquals(3, list1.size());
		System.out.println(list1);
		list1 = JingtongRequestUtils.getFields("accounts/{:address}{:address}/balances");
		assertEquals(2, list1.size());
		System.out.println(list1);

		list1 = JingtongRequestUtils.getFields("accounts/{:}{:addr3ess}/balances");
		assertEquals(1, list1.size());
		System.out.println(list1);

		list1 = JingtongRequestUtils.getFields("accounts/{:}{}/balances");
		assertEquals(0, list1.size());
		System.out.println(list1);
	}

	public void testGetUrl() throws Exception
	{
		BalancesRequest br = new BalancesRequest();
		br.setAddress("123");
		System.out.println(JingtongRequestUtils.getUrl(br));
		assertEquals("https://tapi.jingtum.com/v2/accounts/123/balances", JingtongRequestUtils.getUrl(br));

	}

	// 创建钱包
	public void testWalletNewRequst() throws Exception
	{
		WalletNewRequest wnr = new WalletNewRequest();
		JingtongResponse jr = JingtongRequestUtils.sendRequest(wnr);
		assertEquals(true, jr.isSuccess());
	}

	// 查询余额
	public void testBalances() throws Exception
	{
		BalancesRequest br = new BalancesRequest();
		br.setAddress("jHSvWWZtXS3xdh1YqBHXh2B3Xm2g4PbrSv");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(br);
		assertEquals(true, jr.isSuccess());
	}

	// 转账支付
	public void testPaymentsTransferRequest() throws Exception
	{
		PaymentsTransferRequest ptr = new PaymentsTransferRequest();
		ptr.setSource_address("jpLpucnjfX7ksggzc9Qw6hMSm1ATKJe3AF");
		ptr.setSecret("sha4eGoQujTi9SsRSxGN5PamV3YQ4");
		ptr.setClient_id(System.currentTimeMillis() + "");

		Payment payment = new Payment();
		Amount amount = new Amount();
		amount.setCurrency("SWT");
		amount.setValue("100.00");
		amount.setIssuer("");
		payment.setAmount(amount);
		payment.setDestination("jHSvWWZtXS3xdh1YqBHXh2B3Xm2g4PbrSv");
		payment.setSource("jpLpucnjfX7ksggzc9Qw6hMSm1ATKJe3AF");
		payment.setMemos(new String[]
		{ "hello world", "测试激活钱" });
		ptr.setPayment(payment);

		JingtongResponse jr = JingtongRequestUtils.sendRequest(ptr);
		assertEquals(true, jr.isSuccess());
	}

	// 获得支付信息
	public void testPaymentInformationRequest() throws Exception
	{
		PaymentInformationRequest pir = new PaymentInformationRequest();
		pir.setId("26F75871AADBCB19F30DE62C74D239277DAFF1DDAECF7CC558FBDD63812D79E6");
		pir.setAddress("jm4drm7szaGzpGKt9QWmASEx4eD6d6DR8");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(pir);
		assertEquals(true, jr.isSuccess());

	}

	// 获得支付历史
	public void testPaymentHistoryRequest() throws Exception
	{
		PaymentHistoryRequest pht = new PaymentHistoryRequest();
		pht.setAddress("jHSvWWZtXS3xdh1YqBHXh2B3Xm2g4PbrSv");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(pht);
		assertEquals(true, jr.isSuccess());
	}

	// 查询支付选择
	public void testPaymentsChoicesRequest() throws Exception
	{
		PaymentsChoicesRequest pcr = new PaymentsChoicesRequest();
		pcr.setSource_address("jpLpucnjfX7ksggzc9Qw6hMSm1ATKJe3AF");
		pcr.setAmount("1.00+GTA+jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		pcr.setDestination_address("jwCPxksQzsfdrn3oo8doqb5YmvxFJsFbda");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(pcr);
		assertEquals(true, jr.isSuccess());
	}

	// 提交挂单
	public void testOrdersSubmitRequest() throws Exception
	{
		OrdersSubmitRequest pcr = new OrdersSubmitRequest();
		pcr.setAddress("jHSvWWZtXS3xdh1YqBHXh2B3Xm2g4PbrSv");
		pcr.setSecret("spztfyDzfUSv6NgcshM9LNNhXsXXM");
		Order order = new Order();
		order.setAmount("10");
		order.setPair("SWT/CNY:jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		order.setPrice("0.001");
		order.setType("buy");
		pcr.setOrder(order);
		JingtongResponse jr = JingtongRequestUtils.sendRequest(pcr);
		assertEquals(true, jr.isSuccess());
	}

	// 获取挂单列表
	public void testOrdersListRequest() throws Exception
	{
		OrdersListRequest olr = new OrdersListRequest();
		olr.setAddress("jHSvWWZtXS3xdh1YqBHXh2B3Xm2g4PbrSv");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(olr);
		assertEquals(true, jr.isSuccess());
	}

	// 获取挂单信息
	public void testOrdersInfoRequest() throws Exception
	{
		OrdersInfoRequest oir = new OrdersInfoRequest();
		oir.setAddress("jHSvWWZtXS3xdh1YqBHXh2B3Xm2g4PbrSv");
		oir.setHash("9A421B002E9175A56292B0929B82990F086939EE9A9AB54DB0C6E05102EEA858");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(oir);
		assertEquals(true, jr.isSuccess());
	}
	//取消用户挂单
	public void testOrdersDeleteRequest() throws Exception
	{
	}
	
	
	// 获得货币对的挂单列表
	public void testOrderBookListRequest() throws Exception
	{
		OrderBookListRequest oblr = new OrderBookListRequest();
		oblr.setBase("CNY+janxMdrWE2SUzTqRUtfycH4UGewMMeHa9f");
		oblr.setCounter("SWT");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(oblr);
		assertEquals(true, jr.isSuccess());
	}

	// 获得货币对的买单列表
	public void testOrderBookBidsRequest() throws Exception
	{
		OrderBookBidsRequest oblr = new OrderBookBidsRequest();
		oblr.setBase("CNY+janxMdrWE2SUzTqRUtfycH4UGewMMeHa9f");
		oblr.setCounter("SWT");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(oblr);
		assertEquals(true, jr.isSuccess());
	}

	// 获得货币对的卖单列表
	public void testOrderBookAsksRequest() throws Exception
	{
		OrderBookAsksRequest oblr = new OrderBookAsksRequest();
		oblr.setBase("CNY+janxMdrWE2SUzTqRUtfycH4UGewMMeHa9f");
		oblr.setCounter("SWT");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(oblr);
		assertEquals(true, jr.isSuccess());
	}

	// 查询交易信息
	public void testTransactionsQueryRequest() throws Exception
	{
		TransactionsInfoRequest oblr = new TransactionsInfoRequest();
		oblr.setAddress("jHSvWWZtXS3xdh1YqBHXh2B3Xm2g4PbrSv");
		oblr.setId("9A421B002E9175A56292B0929B82990F086939EE9A9AB54DB0C6E05102EEA858");
		JingtongResponse jr = JingtongRequestUtils.sendRequest(oblr);
		assertEquals(true, jr.isSuccess());
	}

	// 查询交易信息
	public void testTransactionsRecordRequest() throws Exception
	{
		TransactionsRecordRequest oblr = new TransactionsRecordRequest();
		oblr.setAddress("j4ucmztRMNh3y3vkMP2KiJ9vkfbUR4pmpM");
		Marker marker = new Marker();
		marker.setLedger(9057339);
		marker.setSeq(1);
		oblr.setMarker(marker);
		oblr.setResults_per_page(5);
//		 "marker": {
//		    "ledger": 9057339,
//		    "seq": 1
//		  }
		JingtongResponse jr = JingtongRequestUtils.sendRequest(oblr);
		assertEquals(true, jr.isSuccess());
	}
}
