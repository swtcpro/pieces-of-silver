package com.twb;

import java.util.HashMap;
import java.util.Map;

import org.commondata.data.CommitchainMqData;

import com.jingtongsdk.utils.JingtongRequstConstants;
import com.twb.entity.DistributeLog;

import junit.framework.TestCase;

public class JsonTest extends TestCase
{
	public void testSend() throws Exception
	{
		DistributeLog dl = new DistributeLog();
		System.out.println(JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(dl));
	}
	
	public void testCommitchainMqData() throws Exception
	{
		CommitchainMqData cmd = new CommitchainMqData();
		cmd.setAmountcurrency("amountcurrency");
		cmd.setAmountissuer("setAmountissuer");
		cmd.setAmountvalue(1.00);
		cmd.setBusinessid("businessid");
		cmd.setBusinessTag("businessTag");
		cmd.setBusinessTopic("businessTopic");
		cmd.setCounterparty("counterparty");
		Map memos = new HashMap();
		memos.put("aa", "11");
		cmd.setMemos(memos);
		
		System.out.println(JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(cmd));
		System.out.println(JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(memos));
	}
	
	public void testCommitchainMqDataFrom() throws Exception
	{
		String msg ="{\"counterparty\":\"counterparty\",\"amountvalue\":\"amountvalue\",\"amountcurrency\":\"setAmountcurrency\",\"amountissuer\":\"setAmountissuer\",\"memos\":{\"aa\":\"11\"},\"businessid\":\"businessid\",\"business_topic\":\"businessTopic\",\"business_tag\":\"businessTag\"}";
		CommitchainMqData cmd = JingtongRequstConstants.PRETTY_PRINT_GSON.fromJson(msg, CommitchainMqData.class);
		
		System.out.println(cmd);
		
		msg ="{\"counterparty1\":\"counterparty\",\"amountvalue1\":\"amountvalue\",\"amountcurrency\":\"setAmountcurrency\",\"amountissuer\":\"setAmountissuer\",\"memos\":{\"aa\":\"11\"},\"businessid\":\"businessid\",\"business_topic\":\"businessTopic\",\"business_tag\":\"businessTag\"}";
		cmd = JingtongRequstConstants.PRETTY_PRINT_GSON.fromJson(msg, CommitchainMqData.class);
		
		System.out.println(cmd);
		
		msg ="{\"counterparty1\":\"counterparty\",\"amountvalue1\":\"amountvalue\",\"amountcurrency\":\"setAmountcurrency\",\"amountissuer\":\"setAmountissuer\",\"memos1\":{\"aa\":\"11\"},\"businessid\":\"businessid\",\"business_topic\":\"businessTopic\",\"business_tag\":\"businessTag\"}";
		cmd = JingtongRequstConstants.PRETTY_PRINT_GSON.fromJson(msg, CommitchainMqData.class);
		
		System.out.println(cmd);
	}
}
