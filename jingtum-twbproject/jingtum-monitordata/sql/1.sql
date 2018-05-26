分发通道表
distribute_channel
id,
type sent出账 received 入账
flag 检查条件 all所有，others_none其他业务系统都没有发送，其他都是以memos某个开头为标志
topic 阿里云消息队列主题
tag  阿里云消息队列标签
state 开关，0关闭，1.打开 ，会有缓存，要重启服务生效

DROP TABLE IF EXISTS distribute_channel;
    CREATE TABLE distribute_channel
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY, 
      type varchar(20) COLLATE utf8_unicode_ci NOT NULL,  
flag varchar(50) COLLATE utf8_unicode_ci NOT NULL,  
topic varchar(50) COLLATE utf8_unicode_ci NOT NULL,  
tag varchar(50) COLLATE utf8_unicode_ci ,  
state  varchar(1) COLLATE utf8_unicode_ci NOT NULL
    ) ENGINE = InnoDB;
   


分发记录表
distribute_log 分发记录表
id，
date，	交易时间
hash，	交易hash
type  ,  类型
result ，结果
fee，	交易费用，井通计价
memos，	交易备注
counterparty，	交易对家
amountvalue，	交易金额
amountcurrency，	货币类型
amountissuer，	货币发行方
//flag 1.待分发 2.已分发
distribute_type 1.socket数据分发 2.定时任务数据分发
messageid  消息id
topic 消息队列主题
tag 消息队列标签
message_date  消息发送时间
send_result 发送结果


DROP TABLE IF EXISTS distribute_log ;
    CREATE TABLE distribute_log 
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY, 
      date datetime NOT NULL,
      hash varchar(64) COLLATE utf8_unicode_ci NOT NULL,  
type  varchar(20) COLLATE utf8_unicode_ci NOT NULL,  
result varchar(20) COLLATE utf8_unicode_ci NOT NULL,  
      fee float(10,4),
      memos varchar(1000) COLLATE utf8_unicode_ci ,
counterparty varchar(34) COLLATE utf8_unicode_ci ,
amountvalue  float(16,6) ,
amountcurrency varchar(20) COLLATE utf8_unicode_ci ,
amountissuer varchar(34) COLLATE utf8_unicode_ci ,
distribute_type varchar(2) COLLATE utf8_unicode_ci NOT NULL,
messageid varchar(50) COLLATE utf8_unicode_ci ,
topic varchar(50) COLLATE utf8_unicode_ci ,
tag varchar(50) COLLATE utf8_unicode_ci ,
message_date datetime ,
send_result varchar(2) COLLATE utf8_unicode_ci 
    ) ENGINE = InnoDB;
   





socket_data  socket记录表
id，
date，	交易时间
hash，	交易hash
type  ,  类型
result ，结果
fee，	交易费用，井通计价
memos，	交易备注
counterparty，	交易对家
amountvalue，	交易金额
amountcurrency，	货币类型
amountissuer，	货币发行方
flag 1.待分发 2.已分发 3.无需分发

DROP TABLE IF EXISTS socket_data;
    CREATE TABLE socket_data
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY, 
      date datetime NOT NULL,
      hash varchar(64) COLLATE utf8_unicode_ci NOT NULL,  
type  varchar(20) COLLATE utf8_unicode_ci NOT NULL,  
result varchar(20) COLLATE utf8_unicode_ci NOT NULL,  
      fee float(10,4),
      memos varchar(1000) COLLATE utf8_unicode_ci ,
counterparty varchar(34) COLLATE utf8_unicode_ci ,
amountvalue  float(16,6) ,
amountcurrency varchar(20) COLLATE utf8_unicode_ci ,
amountissuer varchar(34) COLLATE utf8_unicode_ci ,
flag varchar(2) COLLATE utf8_unicode_ci NOT NULL

    ) ENGINE = InnoDB;
   
 ALTER TABLE socket_data ADD CONSTRAINT uc_socket_data  UNIQUE (`hash`) ;




timer_data  交易记录表
id，
date，	交易时间
hash，	交易hash
type  ,  类型
result ，结果
fee，	交易费用，井通计价
memos，	交易备注
counterparty，	交易对家
amountvalue，	交易金额
amountcurrency，	货币类型
amountissuer，	货币发行方
checkflag  定时任务验证socket 1.待比对验证 2.验证成功3socket没有，待确认 4.socket没有，已分发5.socket没有，无需分发6.无需比对7异常
checkmsg; // 比对信息
DROP TABLE IF EXISTS timer_data;
    CREATE TABLE timer_data
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY, 
      date datetime NOT NULL,
      hash varchar(64) COLLATE utf8_unicode_ci NOT NULL,  
type  varchar(20) COLLATE utf8_unicode_ci NOT NULL,  
result varchar(20) COLLATE utf8_unicode_ci NOT NULL,  
      fee float(10,4),
      memos varchar(1000) COLLATE utf8_unicode_ci ,
counterparty varchar(34) COLLATE utf8_unicode_ci ,
amountvalue  float(16,6) ,
amountcurrency varchar(20) COLLATE utf8_unicode_ci ,
amountissuer varchar(34) COLLATE utf8_unicode_ci ,
checkflag varchar(2) COLLATE utf8_unicode_ci NOT NULL,
checkmsg varchar(300) COLLATE utf8_unicode_ci NOT NULL
    ) ENGINE = InnoDB;
   
 ALTER TABLE timer_data ADD CONSTRAINT uc_timer_data  UNIQUE (`hash`) ;
