package com.chinaops.web.ydgd.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author liqiang.zhang@china-ops.com
 * 2014年8月11日
 *
 */
public class Privilege implements IsSerializable {
	private int               id;
//	权限名称
	private String            name;
//	过滤标示
	private String            token;
//	过滤url
	private String url;
	//序号，用来排序的
	private int    	       sn = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	
}
