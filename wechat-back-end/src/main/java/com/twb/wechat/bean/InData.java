package com.twb.wechat.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class InData implements Serializable{
	
	private static final long serialVersionUID = 7754908913247118188L;
	
	//单行参数
	private Map<String,Object> inmap;
	//多行数数
	private List<Map<String,Object>> inlist;
	
	public Map<String, Object> getInmap() {
		return inmap;
	}
	public void setInmap(Map<String, Object> inmap) {
		this.inmap = inmap;
	}
	public List<Map<String, Object>> getInlist() {
		return inlist;
	}
	public void setInlist(List<Map<String, Object>> inlist) {
		this.inlist = inlist;
	}
}
