package com.twb.wechat.entity;


import java.util.Date;

import javax.persistence.*;

//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "wxuser") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class Wxuser  {


	
	
    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;

	
    
    private String subscribe; //用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。1关注，0未关注
    private String openid; //用户的标识，对当前公众号唯一
    private String nickname	; //用户的昵称
    private String sex	; //		用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String city	; //用户所在城市
    private String country; //用户所在国家
    private String province	; //用户所在省份
    
    private String language	; //用户的语言，简体中文为zh_CN
    private String headimgurl	; //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "subscribe_time")
    private Date subscribeTime	; //用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    private String remark ; //公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
    private String groupid ; //用户所在的分组ID（兼容旧的用户分组接口）
    private String tagids ; //	用户被打上的标签ID列表
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getSubscribe()
	{
		return subscribe;
	}
	public void setSubscribe(String subscribe)
	{
		this.subscribe = subscribe;
	}
	public String getOpenid()
	{
		return openid;
	}
	public void setOpenid(String openid)
	{
		this.openid = openid;
	}
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getProvince()
	{
		return province;
	}
	public void setProvince(String province)
	{
		this.province = province;
	}
	public String getLanguage()
	{
		return language;
	}
	public void setLanguage(String language)
	{
		this.language = language;
	}
	public String getHeadimgurl()
	{
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl)
	{
		this.headimgurl = headimgurl;
	}
	public Date getSubscribeTime()
	{
		return subscribeTime;
	}
	public void setSubscribeTime(Date subscribeTime)
	{
		this.subscribeTime = subscribeTime;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public String getGroupid()
	{
		return groupid;
	}
	public void setGroupid(String groupid)
	{
		this.groupid = groupid;
	}
	public String getTagids()
	{
		return tagids;
	}
	public void setTagids(String tagids)
	{
		this.tagids = tagids;
	}
	
    
    
    
    
    

    
    
    

}
