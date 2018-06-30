package com.twb.wechat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPayUtil;
import com.twb.wechat.bean.InData;
import com.twb.wechat.bean.OutData;
import com.twb.wechat.entity.OrderPay;
import com.twb.wechat.repository.OrderPayRepository;
import com.twb.wechat.service.OrderPayService;
import com.twb.wechat.utils.CommonUtils;
import com.twb.wechat.utils.TransferUtil;
import com.twb.wechat.utils.jingtum.CheckUtils;

@Service
public class OrderPayServiceImp implements OrderPayService
{

	private static final Logger logger = LoggerFactory.getLogger(OrderPayServiceImp.class);

	@Autowired
	OrderPayRepository orderPayRepository;
	
	
	
	@Value("${wechat.mp.appId}")
    private String appid;
	 
	public static Map<String,Integer> AMOUT_MAP = new ConcurrentHashMap();

	@Value("${wechat.maxAmout}")
    private int maxAmout;
	
	@Override
	public OutData submitOrderPay(InData indata,String ip) throws Exception
	{
		OutData od = new OutData();
		if (indata == null || indata.getInmap() == null)
		{
			od.setReturncode("false");
			od.setReturnmsg("数据为空");
			return od;
		}
		Map inMap = indata.getInmap();
		String address = (String) inMap.get("address");
		if(!CheckUtils.isValidAddress(address))
		{
			od.setReturncode("false");
			od.setReturnmsg("请输入正确的钱包地址！");
			return od;
		}
		String openid = (String) inMap.get("openid");
		String total_fee = (String) inMap.get("total_fee");
		if(StringUtils.isEmpty(openid)||StringUtils.isEmpty(total_fee)||!validateNumber(total_fee))
		{
			od.setReturncode("false");
			od.setReturnmsg("参数错误！");
			return od;
		}
		int totalFeeInt = Integer.parseInt(total_fee);
		if(totalFeeInt>5000||totalFeeInt<=1)
		{
			od.setReturncode("false");
			od.setReturnmsg("金额错误！");
			return od;
		}
		Integer amount = AMOUT_MAP.get(openid);
		if(amount!=null)
		{
			int remain = maxAmout- amount;
			if(totalFeeInt>remain)
			{
				od.setReturncode("false");
				od.setReturnmsg("超过每日限额"+(maxAmout/100)+"元,今日剩余额度"+(remain/100)+"元");
				return od;
			}
		}
		
		
		String out_trade_no = WXPayUtil.generateNonceStr();
		String attach =address;
		String body = "碎银子-购买CNT-"+address;
		Map<String, String> data = new HashMap<String, String>();
		data.put("body", body);
		data.put("total_fee", total_fee);
		data.put("trade_type", "JSAPI");
		data.put("openid", openid);
		data.put("attach", attach);
		data.put("out_trade_no", out_trade_no);
		data.put("spbill_create_ip", ip);

		Map<String, String> map = TransferUtil.wxpay.unifiedOrder(data);
		
		String return_code = map.get("return_code");
		String result_code = map.get("result_code");
		if("SUCCESS".equals(return_code)&&"SUCCESS".equals(result_code))
		{
			
			
			OrderPay op = new OrderPay();
			op.setBody(body);
//			op.setCommitchainHash(commitchainHash);
//			op.setReturnMsg(returnMsg);
			op.setOutTradeNo(out_trade_no);
			op.setCreateTime(new Date());
			op.setJingtumaddress(address);
			op.setOpenid(openid);
			String prepay_id = map.get("prepay_id");
			op.setPrepayId(prepay_id);
			op.setState(OrderPay.STATE_PREPAY);
			op.setTotalFee(total_fee);
			orderPayRepository.save(op);
			
			String timeStamp = String.valueOf(System.currentTimeMillis()/1000);
			String nonceStr = WXPayUtil.generateNonceStr();
			Map outMap = new HashMap();
			outMap.put("package", "prepay_id="+prepay_id);
			outMap.put("appId", appid);
			outMap.put("timeStamp", timeStamp);
			outMap.put("nonceStr", nonceStr);
			outMap.put("signType", "MD5");
			String paySign = TransferUtil.generateSignature(outMap);
			outMap.put("paySign", paySign);
			
			od.setOutmap(outMap);
			od.setReturncode("true");
			od.setReturnmsg("订单生成成功");
			
			Integer amountMap = AMOUT_MAP.get(openid);
			if(amountMap!=null)
			{
				AMOUT_MAP.put(openid, amountMap+totalFeeInt);
			}
			else
			{
				AMOUT_MAP.put(openid, totalFeeInt);
			}
			
			return od;
		}
		else
		{
			od.setReturncode("false");
			od.setReturnmsg("订单生成失败");
			return od;
		}
		
		
	}
	
	
	/**
	 * 
	 * @Title: validateNumber
	 * @Description: 检查是否全数字
	 * @param @param number
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean validateNumber(String number)
	{
		boolean flag = false;
		if (number != null)
		{
			Matcher m = null;
			Pattern p = Pattern.compile("^[0-9]+$");
			m = p.matcher(number);
			flag = m.matches();
		}

		return flag;

	}


	@Override
	public OutData getOrderPay(Map inMap) throws Exception
	{
		OutData od = new OutData();
		String openid = (String) inMap.get("openid");
		
		if (StringUtils.isEmpty(openid))
		{
			od.setReturncode("false");
			od.setReturnmsg("openid为空！");
			return od;
		}

		String pageStr = (String) inMap.get("page");
		String pagesize = (String) inMap.get("pagesize");

		int pageInt = 0;
		int pageSize = 10;
		if (CommonUtils.validateNumber(pageStr))
		{
			pageInt = CommonUtils.string2Int(pageStr, 0);
		}
		if (CommonUtils.validateNumber(pagesize))
		{
			pageSize = CommonUtils.string2Int(pagesize, 10);
		}

		
		Pageable pageable =  new PageRequest(pageInt, pageSize, Direction.DESC,"createTime");
		Page<OrderPay> page = orderPayRepository.findAll(new Specification<OrderPay>()
		{
			@Override
			public Predicate toPredicate(Root<OrderPay> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder)
			{
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(criteriaBuilder.equal(root.get("openid").as(String.class), openid));
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);
		List<OrderPay> list = page.getContent();
		List outlist = new ArrayList();
		for (OrderPay op : list)
		{
			Map map = new HashMap();
			
			map.put("hash", CommonUtils.toString(op.getCommitchainHash()));
			map.put("outTradeNo", CommonUtils.toString(op.getOutTradeNo()));
			map.put("createTime", CommonUtils.toString(op.getCreateTime()));
			map.put("state", CommonUtils.toString(op.getState()));
			map.put("stateName", CommonUtils.toString(op.getStateName()));
			map.put("body", CommonUtils.toString(op.getBody()));
			outlist.add(map);
		}
		
		
		
		Map map = new HashMap();
		map.put("totalNum", page.getTotalElements()+"");
		od.setReturncode("true");
		od.setReturnmsg("获取成功");
		od.setOutlist(outlist);
		od.setOutmap(map);
		return od;
	}
	

}
