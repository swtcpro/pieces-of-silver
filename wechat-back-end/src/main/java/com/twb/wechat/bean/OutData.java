package com.twb.wechat.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OutData implements Serializable{

	private static final long serialVersionUID = -4316416092215090910L;
	//返回状态
	String returncode;
	//返回状态信息
	String returnmsg;
	//单行参数
	private Map<String,Object> outmap;
	//多行数数
	private List<Map<String,Object>> outlist;
	
	public String getReturncode() {
		return returncode;
	}
	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}
	public String getReturnmsg() {
		return returnmsg;
	}
	public void setReturnmsg(String returnmsg) {
		this.returnmsg = returnmsg;
	}
	public Map<String, Object> getOutmap() {
		return outmap;
	}
	public void setOutmap(Map<String, Object> outmap) {
		this.outmap = outmap;
	}
	public List<Map<String, Object>> getOutlist() {
		return outlist;
	}
	public void setOutlist(List<Map<String, Object>> outlist) {
		this.outlist = outlist;
	}
	
	
}
