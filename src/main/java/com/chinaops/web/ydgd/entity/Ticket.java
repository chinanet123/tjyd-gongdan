/*
 * $Id$
 * 
 * All Rights Reserved 2012 China OPS Information Technology Co.,Ltd.
 */
package com.chinaops.web.ydgd.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author liu
 */
public class Ticket implements IsSerializable {
	// ========================== Attributes ============================
	private int id;
	// 工单编号
	private String ticketId;
	// 单工类型
	private String ticketType;
	// 工单状态
	private String ticketState;
	// 工单接收时间
	private String receiveTime;
	// 是否在使用
	private int isUse;
	// 备注
	private String remark;
	// 是否可编辑 0 不可编辑 1可编辑
	private int isModify;
	// 发送时间
	private String sendTime;
	// 客户编号
	private String customerId;
	// 客户名称
	private String customerName;
	// 客户地址
	private String customerAddress;
	// 客户邮编
	private String postalCode;
	// 所属行业
	private String category;

	// 业务联系人
	private BusinessContact businessContact;
	// 技术联系人
	private TechnicalContact technicalContact;
	// 客户经理
	private CustomerManager customerManager;
	// 订单实体对象（接收参数用）
	private Order order;
	
	
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
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getTicketState() {
		return ticketState;
	}
	public void setTicketState(String ticketState) {
		this.ticketState = ticketState;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getIsModify() {
		return isModify;
	}
	public void setIsModify(int isModify) {
		this.isModify = isModify;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public BusinessContact getBusinessContact() {
		return businessContact;
	}
	public void setBusinessContact(BusinessContact businessContact) {
		this.businessContact = businessContact;
	}
	public TechnicalContact getTechnicalContact() {
		return technicalContact;
	}
	public void setTechnicalContact(TechnicalContact technicalContact) {
		this.technicalContact = technicalContact;
	}
	public CustomerManager getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	
}
