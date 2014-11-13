/*
 * $Id$
 * 
 * All Rights Reserved 2012 China OPS Information Technology Co.,Ltd.
 */
package com.chinaops.web.ydgd.entity;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 *
 * @author liu
 */
public class Order implements IsSerializable {
    // ========================== Attributes ============================
    private int    id;
   //工单编号
    private String ticketId;
   //同合编号
    private String contractId;
   //合同起始时间
    private String contractSignedDate;
    //合同截止时间
    private String contractExpiredDate;
    //资源开通时间
    private String availableAt;
    //产品类型:专享云,共享云,云存储
    private String productType;
    //是否做网站
    private String forWebsite;
    //开放端口
    private String openPorts;
    //要求开放端口,如有多个端口，请用英文半角的逗号分隔
    private String portsNeedToOpen;
//  套餐列表
    private List<Suite> suites;
    //带宽
    private String bandwidth;
    //ip
    private String ip;
    //ip个数
    private String ipCount;
    //存储
    private Storage storage;
    
    private String sizeInGB;
    private String discount;
    
    //快照
    private String snapshot;
    //密钥
    private String keypairs;
    //安全规则组
    private String securitys;
    //主机保护
    private String ha;
    //负载均衡
    private String elb;
    //备案号
    private String filingNo;
    //备案IP
    private String filingIp;
    //备案域名
    private String filingDomain;
    //备注
    private String memo;
    //是否欠费
    private String isFeesOwed;
    //撤销时间
    private String closeTime;
    //撤销原因
    private String  reason;
    //详细原因
    private String  detailedReason;
    //登录云平台的URL
    private String loginUrl;
    //所属云平台
    private String cloudPlatform;
    //登录云平台用户名
    private String loginUsername;
    //登陆云平台密码
    private String loginPassword;
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
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractSignedDate() {
		return contractSignedDate;
	}
	public void setContractSignedDate(String contractSignedDate) {
		this.contractSignedDate = contractSignedDate;
	}
	public String getContractExpiredDate() {
		return contractExpiredDate;
	}
	public void setContractExpiredDate(String contractExpiredDate) {
		this.contractExpiredDate = contractExpiredDate;
	}
	public String getAvailableAt() {
		return availableAt;
	}
	public void setAvailableAt(String availableAt) {
		this.availableAt = availableAt;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getForWebsite() {
		return forWebsite;
	}
	public void setForWebsite(String forWebsite) {
		this.forWebsite = forWebsite;
	}
	public String getOpenPorts() {
		return openPorts;
	}
	public void setOpenPorts(String openPorts) {
		this.openPorts = openPorts;
	}
	public String getPortsNeedToOpen() {
		return portsNeedToOpen;
	}
	public void setPortsNeedToOpen(String portsNeedToOpen) {
		this.portsNeedToOpen = portsNeedToOpen;
	}
	public List<Suite> getSuites() {
		return suites;
	}
	public void setSuites(List<Suite> suites) {
		this.suites = suites;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIpCount() {
		return ipCount;
	}
	public void setIpCount(String ipCount) {
		this.ipCount = ipCount;
	}
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
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
	public String getSnapshot() {
		return snapshot;
	}
	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}
	public String getKeypairs() {
		return keypairs;
	}
	public void setKeypairs(String keypairs) {
		this.keypairs = keypairs;
	}
	public String getSecuritys() {
		return securitys;
	}
	public void setSecuritys(String securitys) {
		this.securitys = securitys;
	}
	public String getHa() {
		return ha;
	}
	public void setHa(String ha) {
		this.ha = ha;
	}
	public String getElb() {
		return elb;
	}
	public void setElb(String elb) {
		this.elb = elb;
	}
	public String getFilingNo() {
		return filingNo;
	}
	public void setFilingNo(String filingNo) {
		this.filingNo = filingNo;
	}
	public String getFilingIp() {
		return filingIp;
	}
	public void setFilingIp(String filingIp) {
		this.filingIp = filingIp;
	}
	public String getFilingDomain() {
		return filingDomain;
	}
	public void setFilingDomain(String filingDomain) {
		this.filingDomain = filingDomain;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getIsFeesOwed() {
		return isFeesOwed;
	}
	public void setIsFeesOwed(String isFeesOwed) {
		this.isFeesOwed = isFeesOwed;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDetailedReason() {
		return detailedReason;
	}
	public void setDetailedReason(String detailedReason) {
		this.detailedReason = detailedReason;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String getCloudPlatform() {
		return cloudPlatform;
	}
	public void setCloudPlatform(String cloudPlatform) {
		this.cloudPlatform = cloudPlatform;
	}
	public String getLoginUsername() {
		return loginUsername;
	}
	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
    
}
