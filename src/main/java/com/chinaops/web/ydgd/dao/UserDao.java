/*
 * $Id$
 *
 * All Rights Reserved 2014 China OPS Information Technology Co.,Ltd.
 */
package com.chinaops.web.ydgd.dao;

import com.chinaops.web.ydgd.entity.User;

/**
 *
 * @author hiumin_angle
 */
public interface UserDao {
    // ========================== Attributes ============================

	public User authLogin(User user);
	
	public boolean updateUser(User user);
    // ========================= Constructors ===========================

    // ======================= Getters & Setters ========================

    // ======================== Public methods ==========================

    // ==================== Private utility methods =====================

    // ========================== main method ===========================
}
