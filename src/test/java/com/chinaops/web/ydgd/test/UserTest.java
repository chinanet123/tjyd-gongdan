/*
 * $Id$
 *
 * All Rights Reserved 2014 China OPS Information Technology Co.,Ltd.
 */
package com.chinaops.web.ydgd.test;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chinaops.web.ydgd.dao.UserDao;
import com.chinaops.web.ydgd.entity.User;

/**
 *
 * @author hiumin_angle
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-dao.xml" })
public class UserTest {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Test
	public void authUser(){
		User user = new User();
		user.setLoginname("admin");
		user.setPassword("admin");
		User u = userDao.authLogin(user);
		Assert.assertEquals("cfm", u.getRoletype());
	}
	
	@Test
	public void updateUser(){
		User user = new User();
		user.setId(2);
		user.setLoginname("admin");
		user.setUsername("admin");
		user.setPassword("admin");
		boolean flag = userDao.updateUser(user);
		System.out.println(flag);
	}
    // ========================== Attributes ============================

    // ========================= Constructors ===========================

    // ======================= Getters & Setters ========================

    // ======================== Public methods ==========================

    // ==================== Private utility methods =====================

    // ========================== main method ===========================
}
