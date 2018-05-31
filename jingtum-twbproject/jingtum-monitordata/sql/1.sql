
DROP TABLE IF EXISTS commitchain_data;
    CREATE TABLE commitchain_data
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY  COMMENT '主键自增',  
business_id varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '业务id，用来保证消息唯一性去重复',  
      memos varchar(1000) COLLATE utf8_unicode_ci COMMENT '交易备注',  
counterparty varchar(34) COLLATE utf8_unicode_ci COMMENT '交易对家',  
amountvalue   varchar(20) COLLATE utf8_unicode_ci  COMMENT '交易金额',
amountcurrency varchar(20) COLLATE utf8_unicode_ci COMMENT '货币类型',
amountissuer varchar(34) COLLATE utf8_unicode_ci COMMENT '货币发行方',

commitchain_flag varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '1.待上链 2.准备上链 3.上链成功 4.上链失败5.数据校验失败',
commitchain_msg varchar(200) COLLATE utf8_unicode_ci COMMENT '上链成功失败等信息',
commitchain_hash varchar(64) COLLATE utf8_unicode_ci COMMENT '上链的hash',
commitchain_date datetime COMMENT '上链提交时间',

check_flag varchar(2) COLLATE utf8_unicode_ci COMMENT '1.待验证 2.已验证成功 3.已验证失败4.验证异常',
check_date datetime COMMENT '验证完成时间，如果成功，是上链成功时间否则',

business_topic  varchar(50) COLLATE utf8_unicode_ci COMMENT '结果反馈业务系统MQ主题',
business_tag  varchar(50) COLLATE utf8_unicode_ci COMMENT '结果反馈业务系统TAG',
business_flag  varchar(2) COLLATE utf8_unicode_ci COMMENT '1.待反馈 2.已反馈 成功3.已反馈失败4.无需反馈',
business_mqdate datetime COMMENT '反馈时间',
business_mqid  varchar(50) COLLATE utf8_unicode_ci  COMMENT '反馈消息id'
    ) ENGINE = InnoDB  COMMENT '上链数据表';

 ALTER TABLE commitchain_data ADD CONSTRAINT uc_commitchain_data  UNIQUE (`business_id`) ;

ALTER TABLE commitchain_data  ADD INDEX index_commitchain_data_businessflag     ( `business_flag` ) ;


DROP TABLE IF EXISTS commitchain_verify_data;
    CREATE TABLE commitchain_verify_data
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增', 
cid int(11) COMMENT '上链数据表主键', 
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
checkflag varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 验证 1.待比对验证 2.验证成功3验证异常',
checkmsg varchar(300) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 比对信息'
    ) ENGINE = InnoDB COMMENT ' 上链数据验证表';
   
 ALTER TABLE commitchain_verify_data ADD CONSTRAINT uc_commitchain_verify_data  UNIQUE (`hash`) ;
 


DROP TABLE IF EXISTS distribute_channel;
    CREATE TABLE distribute_channel
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增', 
      type varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'sent出账 received 入账',  
flagtype varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '发送消息条件类型 1所有，2其他业务系统都没处理才发送，3 memos开头为flag',  
flag varchar(50) COLLATE utf8_unicode_ci  COMMENT '发送消息条件 ',  
topic varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 阿里云消息队列主题',  
tag varchar(50) COLLATE utf8_unicode_ci  COMMENT '阿里云消息队列标签',  
state  varchar(1) COLLATE utf8_unicode_ci NOT NULL COMMENT '开关，0关闭，1.打开 ，会有缓存，要重启服务生效',
dest_system varchar(200) COLLATE utf8_unicode_ci COMMENT '目的系统',
remark varchar(200) COLLATE utf8_unicode_ci COMMENT ' 备注'
    ) ENGINE = InnoDB COMMENT '分发通道表';

 INSERT INTO distribute_channel VALUES ('1', 'sent', '3', '响应', 'jingtum', 'allsent', '1', '消息响应系统', '所有的出账，推送给消息响应系统');
INSERT INTO distribute_channel VALUES ('2', 'received', '2', null, 'jingtum', 'response', '1', '消息响应系统', '入账，没有业务系统响应，推送给消息响应系统，原路返回');
INSERT INTO distribute_channel VALUES ('3', 'received', '3', '提取', 'jingtum', 'withdrawal', '1', '零花钱提现', '入账，并且是提现业务，推送给零花钱提现系统');

 DROP TABLE IF EXISTS distribute_log ;
    CREATE TABLE distribute_log
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增',
      date datetime NOT NULL COMMENT '交易时间',
      hash varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '交易hash',
type  varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 类型',
result varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '结果',
      fee varchar(20) COLLATE utf8_unicode_ci  COMMENT '交易费用，井通计价',
      memos varchar(1000) COLLATE utf8_unicode_ci  COMMENT '交易备注',
counterparty varchar(34) COLLATE utf8_unicode_ci  COMMENT '交易对家',
amountvalue varchar(20) COLLATE utf8_unicode_ci   COMMENT '交易金额',
amountcurrency varchar(20) COLLATE utf8_unicode_ci  COMMENT '货币类型',
amountissuer varchar(34) COLLATE utf8_unicode_ci  COMMENT '货币发行方',
distribute_type varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '1.socket数据分发 2.定时任务数据分发',
messageid varchar(50) COLLATE utf8_unicode_ci  COMMENT '消息id',
topic varchar(50) COLLATE utf8_unicode_ci  COMMENT '消息队列主题',
tag varchar(50) COLLATE utf8_unicode_ci  COMMENT '消息队列标签',
message_date datetime  COMMENT '消息发送时间',
send_result varchar(2) COLLATE utf8_unicode_ci COMMENT ' 发送结果'
    ) ENGINE = InnoDB COMMENT ' 分发记录表';


DROP TABLE IF EXISTS socket_data;
    CREATE TABLE socket_data
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增',
      date datetime NOT NULL COMMENT '交易时间',
      hash varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '交易hash',
type  varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
result varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '结果',
      fee varchar(20) COLLATE utf8_unicode_ci  COMMENT '交易费用，井通计价',
      memos varchar(1000) COLLATE utf8_unicode_ci  COMMENT '交易备注',
counterparty varchar(34) COLLATE utf8_unicode_ci  COMMENT '交易对家',
amountvalue  varchar(20) COLLATE utf8_unicode_ci   COMMENT '交易金额',
amountcurrency varchar(20) COLLATE utf8_unicode_ci  COMMENT '货币类型',
amountissuer varchar(34) COLLATE utf8_unicode_ci  COMMENT '货币发行方',
flag varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '1.待分发 2.已分发 3.无需分发'

    ) ENGINE = InnoDB COMMENT 'socket记录表';
 ALTER TABLE socket_data ADD CONSTRAINT uc_socket_data  UNIQUE (`hash`) ;


DROP TABLE IF EXISTS timer_data;
    CREATE TABLE timer_data
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY COMMENT '主键自增',
      date datetime NOT NULL COMMENT '交易时间',
      hash varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '交易hash',
type  varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
result varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '结果',
      fee varchar(20) COLLATE utf8_unicode_ci  COMMENT '交易费用，井通计价',
      memos varchar(1000) COLLATE utf8_unicode_ci  COMMENT '交易备注',
counterparty varchar(34) COLLATE utf8_unicode_ci  COMMENT '	交易对家',
amountvalue  varchar(20) COLLATE utf8_unicode_ci   COMMENT '交易金额',
amountcurrency varchar(20) COLLATE utf8_unicode_ci  COMMENT '	货币类型',
amountissuer varchar(34) COLLATE utf8_unicode_ci  COMMENT '货币发行方',
checkflag varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '定时任务验证socket 1.待比对验证 2.验证成功3socket没有，待确认 4.socket没有，已分发6.无需比对7异常',
checkmsg varchar(300) COLLATE utf8_unicode_ci COMMENT '比对信息'
    ) ENGINE = InnoDB COMMENT '交易记录表';
 ALTER TABLE timer_data ADD CONSTRAINT uc_timer_data  UNIQUE (`hash`) ;
