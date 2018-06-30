DROP TABLE IF EXISTS order_pay;
    CREATE TABLE order_pay
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY  COMMENT '主键自增',  
openid varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '微信openid', 
jingtumaddress varchar(34) COLLATE utf8_unicode_ci COMMENT '井通地址',  
total_fee int(8)  COMMENT '订单总金额，单位为分',
out_trade_no  varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '订单号',
body varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '商品简单描述',

prepay_id  varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时',
state varchar(2) COLLATE utf8_unicode_ci COMMENT '状态：1.待支付2.已支付3.已支付待上链4.支付完成已上链5.支付完成上链失败',

create_time datetime COMMENT '创建时间',

pay_time datetime COMMENT '支付时间',

commitchain_msg  varchar(128) COLLATE utf8_unicode_ci COMMENT '返回信息',
 message_id varchar(50) COLLATE utf8_unicode_ci  COMMENT '消息id',
message_date datetime  COMMENT '上链发送时间',
commitchain_hash varchar(64) COLLATE utf8_unicode_ci  COMMENT '上链hash',
message_complete_date datetime  COMMENT '上链结束时间'
    ) ENGINE = InnoDB  COMMENT '订单表';

ALTER TABLE order_pay ADD INDEX index_order_pay_openid  ( `openid` ) ;
 ALTER TABLE order_pay ADD CONSTRAINT uc_out_trade_no UNIQUE (`out_trade_no`) ;




wxopenid
id
openidxff 新风范openid
openiddsb 云大商帮openid
date 创建时间

DROP TABLE IF EXISTS wxopenid;
    CREATE TABLE wxopenid
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY,       
      openidxff varchar(36) COLLATE utf8_unicode_ci NOT NULL,  
      openiddsb varchar(36) COLLATE utf8_unicode_ci NOT NULL,  
date datetime NOT NULL
    ) ENGINE = InnoDB;





wxuser 

subscribe	用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。1关注，0未关注
openid	用户的标识，对当前公众号唯一
nickname	用户的昵称
sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
city	用户所在城市
country	用户所在国家
province	用户所在省份
language	用户的语言，简体中文为zh_CN
headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
remark	公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
groupid	用户所在的分组ID（兼容旧的用户分组接口）
tagid_list	用户被打上的标签ID列表
subscribe_scene	返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
qr_scene	二维码扫码场景（开发者自定义）
qr_scene_str	二维码扫码场景描述（开发者自定义）




DROP TABLE IF EXISTS wxuser ;
    CREATE TABLE wxuser 
    (
      id int(11) AUTO_INCREMENT PRIMARY KEY, 
      subscribe	varchar(5) COLLATE utf8_unicode_ci NOT NULL,  
      openid varchar(36) COLLATE utf8_unicode_ci NOT NULL,  
nickname varchar(100) COLLATE utf8_unicode_ci ,  
sex varchar(1) COLLATE utf8_unicode_ci ,
language varchar(10) COLLATE utf8_unicode_ci ,
city varchar(20) COLLATE utf8_unicode_ci ,
province varchar(20) COLLATE utf8_unicode_ci ,
country varchar(20) COLLATE utf8_unicode_ci ,
headImgUrl varchar(300) COLLATE utf8_unicode_ci ,
subscribe_time datetime ,
remark varchar(500) COLLATE utf8_unicode_ci ,
groupid varchar(500) COLLATE utf8_unicode_ci ,
tagids varchar(500) COLLATE utf8_unicode_ci 
    ) ENGINE = InnoDB;

ALTER TABLE wxuser ADD CONSTRAINT uc_wxuser  UNIQUE (`openid`) ;


