jingtum-sdk
------- 
JingtongRequstConstants. URL_HEAD， 可以配置生产环境或测试环境

调用的话，创建对应JingtongRequest，并且赋值对应的参数
然后调用 JingtongRequestUtils.sendRequest(JingtongRequest)，
返回对应的JingtongResponse。

如：查询余额，创建了查询余额`Balances`Request，并且赋值对应数据，返回的是`Balances`Response

BalancesRequest br = new BalancesRequest();

br.setAddress("j4mk8vSKLVhto6RArmQKKGCMW6kc4x68xa");

BalancesResponse jr = (BalancesResponse) JingtongRequestUtils.sendRequest(br);

System.out.println(jr);

assertEquals(true, jr.isSuccess());

		
jingtum-monitordata
------- 
1.socket监听钱包数据，做到业务能实时触发

2.定时任务监听数据，做到验证socket监听的数据，和补发socket未监听到的数据

3.监听钱包数据，根据定义的规则，通过MQ分发数据到业务系统

4.通过MQ获取上链数据，并提交上链

5.数据提交上链之后，通过监听，确认数据上链成功或失败

6.数据上链确认成功或失败，响应到业务系统

![设计](https://github.com/swtcpro/pieces-of-silver/blob/master/jingtum-monitordata/sql/%E7%9B%91%E5%90%AC%E7%B3%BB%E7%BB%9F.png)


commondata
----
封装了一些公共方法和数据，如：

CommitchainMqData，数据上链的消息格式

DistributeMqData 分发监听到的消息格式

CommitChainRespMqData 上链结果反馈的消息格式

MQUtils 发送消息和解析消息的一些公共方法


business-sponsor-sendback
---
业务逻辑，赞赏业务，和回退业务。
