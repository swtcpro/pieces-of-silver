SendBackTask
sponsorTask

	DROP TABLE IF EXISTS sponsor_data;
    CREATE TABLE sponsor_data
    (
     			 id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增', 
			bussinessid varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '业务主键', 
     			 date datetime NOT NULL COMMENT '交易时间',
     			 hash varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '交易hash',  
			type  varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',  
			result varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '结果',  
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

			handle_flag varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 处理状态 1.待处理 2.处理成功3.处理失败 ',
			handle_msg varchar(500) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 处理信息',
commitchain_message_id varchar(50) COLLATE utf8_unicode_ci  COMMENT '上链消息id'
    ) ENGINE = InnoDB COMMENT ' 赞赏数据表';
   
 ALTER TABLE sponsor_data ADD CONSTRAINT uc_sponsor_data  UNIQUE (`hash`) ;


DROP TABLE IF EXISTS sendback_data;
    CREATE TABLE sendback_data
    (
     			 id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增', 
			bussinessid varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '业务主键', 
     			 date datetime NOT NULL COMMENT '交易时间',
     			 hash varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '交易hash',  
			type  varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',  
			result varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '结果',  
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

			handle_flag varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 处理状态 1.待处理 2.处理成功3.处理失败',
			handle_msg varchar(500) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 处理信息',
commitchain_message_id varchar(50) COLLATE utf8_unicode_ci  COMMENT '上链消息id'
    ) ENGINE = InnoDB COMMENT ' 无业务处理，井通回退表';
   
 ALTER TABLE sendback_data ADD CONSTRAINT uc_sendback_data  UNIQUE (`hash`) ;



    