package com.twb;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aliyun.openservices.ons.api.SendResult;
import com.twb.commondata.data.CommitchainMqData;
import com.twb.service.MqProductService;

@SpringBootTest(classes = JingtongMonitordataApplication.class) 
@RunWith(SpringJUnit4ClassRunner.class)
public class MqProductTest 
{
	
	@Value("${COMMITCHAIN_TOPIC}")
	private String topic;

	@Value("${COMMITCHAIN_TAG}")
	private String tag;
	
	@Autowired
	MqProductService mqProductServiceImp;
	
	/**
	 * 
	 * @Title: testMqCommitChain   
	 * @Description: 测试数据上链
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	@Test
	public void testMqCommitChain() throws Exception
	{
		System.out.println("123");
//		SendResult sr1 = mqProductServiceImp.sendMQ(topic, tag, "test");
//		SendResult sr2 = mqProductServiceImp.sendMQ(topic, tag, "");
//		SendResult sr3 = mqProductServiceImp.sendMQ(topic, tag, "{}");
//		SendResult sr4 = mqProductServiceImp.sendMQ(topic, tag, "{\"deptid\":\"7D32F48DCC6E4C32B122336C5450C342\"}");
//		SendResult sr5 = mqProductServiceImp.sendMQ(topic, tag, "{\"id\":\"7D32F48DCC6E4C32B122336C5450C342\"}");
//		
//		SendResult sr6 = mqProductServiceImp.sendMQ(topic, tag, "{\"businessid\":\"7D32F48DCC6E4C32B122336C5450C342\"}");
//		
//		SendResult sr7 = mqProductServiceImp.sendMQ(topic, tag, "{\"businessid\":\"7D32F48DCC6E4C32B122336C5450C342\",\"id\":\"7D32F48DCC6E4C32B122336C5450C342\"}");
		
//		SendResult sr8 = mqProductServiceImp.sendMQ(topic, tag, "{\"id\":\"7D32F48DCC6E4C32B122336C5450C342\"}");
		Thread.sleep(600000);
	}
	
	@Test
	public void testMqCommitChain2() throws Exception
	{
		CommitchainMqData cmd = new CommitchainMqData();
		cmd.setAmountcurrency("CNY");
		cmd.setAmountissuer("setAmountissuer");
		cmd.setAmountvalue(1.000001);
		cmd.setBusinessid("businessid");
		cmd.setBusinessTag("businessTag");
		cmd.setBusinessTopic("businessTopic");
		cmd.setCounterparty("counterparty");
		Map memos = new HashMap();
		memos.put("aa", "11");
		cmd.setMemos(memos);
//		SendResult sr = mqProductServiceImp.sendMQ(topic, tag, cmd);
		Thread.sleep(600000);
	}
	
	@Test
	public void testMqCommitChain3() throws Exception
	{
		for(int i=1;i<10;i++)
		{
			CommitchainMqData cmd = new CommitchainMqData();
			cmd.setAmountcurrency("SWT");
			cmd.setAmountissuer("");
			cmd.setAmountvalue(Math.random());
			cmd.setBusinessid("testdaa"+i);
			cmd.setBusinessTag("testback");
			cmd.setBusinessTopic("jingtum");
			cmd.setCounterparty("jGkxobBPv8Wc4o65Asq9WfTYKEpui1JY9t");
			Map memos = new HashMap();
			memos.put("test", "test");
			cmd.setMemos(memos);
//			SendResult sr = mqProductServiceImp.sendMQ(topic, tag, cmd);
		}
		
		Thread.sleep(6000000);
	}
}
