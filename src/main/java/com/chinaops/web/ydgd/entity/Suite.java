/**
 * 
 */
package com.chinaops.web.ydgd.entity;

/**
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年9月2日 上午8:28:53
 */
public class Suite {
	
	private int id;
	private String ticketId;
	private String orderId;
	private String name;
	private String price;
	private String count;
	private String discount;
	private String desc;
	private int isUse;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	
	
	public static String[] suiteShare(Suite o){
		String[] suite = new String[3];
		if(o.getName().equals("套餐1")){
			suite[0] = "0.5";
			suite[1] = "134";
			suite[2] = "25";
		}else if(o.getName().equals("套餐2")){
			suite[0] = "1";
			suite[1] = "260";
			suite[2] = "50";
		}else if(o.getName().equals("套餐3")){
			suite[0] = "1.5";
			suite[1] = "378";
			suite[2] = "75";
		}else if(o.getName().equals("套餐4")){
			suite[0] = "2";
			suite[1] = "489";
			suite[2] = "100";
		}else if(o.getName().equals("套餐5")){
			suite[0] = "3 ";
			suite[1] = "713";
			suite[2] = "150";
		}else if(o.getName().equals("套餐6")){
			suite[0] = "4 ";
			suite[1] = "923";
			suite[2] = "200";
		}else if(o.getName().equals("套餐7")){
			suite[0] = "6 ";
			suite[1] = "1344";
			suite[2] = "300";
		}else if(o.getName().equals("套餐8")){
			suite[0] = "8 ";
			suite[1] = "1739";
			suite[2] = "400";
		}else if(o.getName().equals("套餐9")){
			suite[0] = "12 ";
			suite[1] = "2522";
			suite[2] = "600";
		}else if(o.getName().equals("套餐10")){
			suite[0] = "16 ";
			suite[1] = "3279 ";
			suite[2] = "800";
		}
		return suite;
	}
	
	public static String[] suiteExclusive(Suite o){
		String[] suite = new String[3];
		if(o.getName().equals("套餐1")){
			suite[0] = "32";
			suite[1] = "6365";
			suite[2] = "1600";
		}else if(o.getName().equals("套餐2")){
			suite[0] = "64";
			suite[1] = "12360";
			suite[2] = "3200";
		}else if(o.getName().equals("套餐3")){
			suite[0] = "256";
			suite[1] = "49600";
			suite[2] = "12800";
		}else if(o.getName().equals("ECU定制包")){
			suite[0] = "1";
			suite[1] = "260";
			suite[2] = "50";
		}else if(o.getName().equals("增值服务套餐1")){
			suite[0] = "0";
			suite[1] = "0";
			suite[2] = "0";
		}else if(o.getName().equals("增值服务套餐2")){
			suite[0] = "0";
			suite[1] = "0";
			suite[2] = "0";
		}else if(o.getName().equals("增值服务套餐3")){
			suite[0] = "0";
			suite[1] = "0";
			suite[2] = "0";
		}
		return suite;
	}
}
