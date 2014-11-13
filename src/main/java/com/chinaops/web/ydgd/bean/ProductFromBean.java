/**
 * 
 */
package com.chinaops.web.ydgd.bean;

/**
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年8月27日 上午10:10:53
 */
public class ProductFromBean {
	//工单编号
    private String ticketId;
    private String ticketType;
    private String customerId;
    private String ticketState;
    //所属云平台
    private String cloudPlatform;
    //登录云平台的URL
    private String loginUrl;
    //登录云平台用户名
    private String loginUsername;
    //登陆云平台密码
    private String loginPassword;
	//开放端口
    private String openPorts;
	//ip
    private String ip;
	//密钥
    private String keyparis;
    //安全规则组
    private String securitys;
    //备注
    private String remark;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTicketState() {
		return ticketState;
	}
	public void setTicketState(String ticketState) {
		this.ticketState = ticketState;
	}
	public String getKeyparis() {
		return keyparis;
	}
	public void setKeyparis(String keyparis) {
		this.keyparis = keyparis;
	}
	public String getCloudPlatform() {
		return cloudPlatform;
	}
	public void setCloudPlatform(String cloudPlatform) {
		this.cloudPlatform = cloudPlatform;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
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
	public String getOpenPorts() {
		return openPorts;
	}
	public void setOpenPorts(String openPorts) {
		this.openPorts = openPorts;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getKeypairs() {
		return keyparis;
	}
	public void setKeypairs(String keyparis) {
		this.keyparis = keyparis;
	}
	public String getSecuritys() {
		return securitys;
	}
	public void setSecuritys(String securitys) {
		this.securitys = securitys;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
}
