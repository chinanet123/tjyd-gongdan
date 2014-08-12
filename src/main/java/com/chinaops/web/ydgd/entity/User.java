/*
 * $Id$
 *
 * All Rights Reserved 2014 China OPS Information Technology Co.,Ltd.
 */
package com.chinaops.web.ydgd.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author hiumin_angle
 */
public class User implements IsSerializable {
    
	 // ========================== Attributes ============================
	private int id;
    private String username;
    private String loginname;
    private String password;
    private String roletype;
    
 // ======================= Getters & Setters ========================
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
    
    
   

    // ========================= Constructors ===========================


    // ======================== Public methods ==========================

    // ==================== Private utility methods =====================

    // ========================== main method ===========================
}
