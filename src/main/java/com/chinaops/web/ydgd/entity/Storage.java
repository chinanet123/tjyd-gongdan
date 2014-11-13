/**
 * 
 */
package com.chinaops.web.ydgd.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年9月12日 下午4:51:30
 */
public class Storage implements IsSerializable {
	
	private String sizeInGB;
	private String discount;
	
	public String getSizeInGB() {
		return sizeInGB;
	}
	public void setSizeInGB(String sizeInGB) {
		this.sizeInGB = sizeInGB;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
}
