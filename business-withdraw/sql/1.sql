	DROP TABLE IF EXISTS withdrawal ;
    CREATE TABLE withdrawal 
    (
     			 id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增', 
			bussinessid varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '业务主键', 
     			 date datetime NOT NULL COMMENT '交易时间',
     			 hash varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '交易hash',
 		        fee varchar(20) COLLATE utf8_unicode_ci  COMMENT '交易费用，井通计价',
     			 memos varchar(1000) COLLATE utf8_unicode_ci  COMMENT '交易备注',
			counterparty varchar(34) COLLATE utf8_unicode_ci  COMMENT '交易对家',
			amountvalue varchar(20) COLLATE utf8_unicode_ci   COMMENT '交易金额',
			amountcurrency varchar(20) COLLATE utf8_unicode_ci  COMMENT '货币类型',
			amountissuer varchar(34) COLLATE utf8_unicode_ci  COMMENT '货币发行方',
			
  			 message_id varchar(50) COLLATE utf8_unicode_ci  COMMENT '消息id',
			message_topic varchar(50) COLLATE utf8_unicode_ci  COMMENT '消息队列主题',
			message_tag varchar(50) COLLATE utf8_unicode_ci  COMMENT '消息队列标签',
			message_date datetime  COMMENT '消息接收时间',

			state varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 提现状态，1待提现，2返现成功3.回退4返现失败回退5返现异常'


    ) ENGINE = InnoDB COMMENT ' 提现数据表';

ALTER TABLE withdrawal ADD CONSTRAINT uc_withdrawal   UNIQUE (`hash`) ;

DROP TABLE IF EXISTS withdrawal_deallog ;
    CREATE TABLE withdrawal_deallog 
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增',  
      withdrawal_id int(11) NOT NULL COMMENT '主键自增',     
payee_account varchar(50) COLLATE utf8_unicode_ci  COMMENT '提现账户', 
      amount   varchar(20)  NOT NULL COMMENT '提现金额,单位，分',
      time datetime COMMENT '提现处理时间',
return_code varchar(20) COLLATE utf8_unicode_ci  COMMENT '处理结果',  
return_msg varchar(200) COLLATE utf8_unicode_ci  COMMENT '处理结果msg',
pay_date datetime COMMENT '支付时间',
order_id varchar(64) COLLATE utf8_unicode_ci COMMENT '商户订单号',
order_wxid varchar(64) COLLATE utf8_unicode_ci COMMENT '微信订单号'
    ) ENGINE = InnoDB COMMENT ' 提现日志表';


DROP TABLE IF EXISTS withdrawal_commitchain ;
    CREATE TABLE withdrawal_commitchain
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增', 
      withdrawal_id int(11) NOT NULL COMMENT '提现数据表主键',
     withdrawal_deallog_id int(11)  COMMENT '提现日志表主键',
counterparty varchar(34) COLLATE utf8_unicode_ci  COMMENT '交易对家', 
amountvalue varchar(20) COLLATE utf8_unicode_ci   NOT NULL COMMENT '金额',
amountcurrency varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '货币',
amountissuer varchar(34) COLLATE utf8_unicode_ci  COMMENT '货币发行方',  
hash varchar(64) COLLATE utf8_unicode_ci  COMMENT 'hash',  
date datetime COMMENT '数据插入时间',  
reason  varchar(200) COLLATE utf8_unicode_ci  COMMENT '上链原因',  
commitchain_state varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '上链状态，1待上链 2处理异常3处理成功4处理失败',  
commitchain_data datetime COMMENT '上链提交时间',   
commitchain_message_id varchar(50) COLLATE utf8_unicode_ci  COMMENT '上链消息id'
    ) ENGINE = InnoDB  COMMENT ' 提现上链表';

ALTER TABLE withdrawal_commitchain ADD CONSTRAINT uc_withdrawal_commitchain   UNIQUE (`hash`) ;
