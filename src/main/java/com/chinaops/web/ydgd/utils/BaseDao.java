package com.chinaops.web.ydgd.utils;

import java.util.Map;

import com.chinaops.web.ydgd.entity.User;


public abstract class BaseDao extends UtilDao {
	
	public User mapToUser(Map<String, Object> map) {
		if (map == null || map.size() == 0)
			return null;
		User user = new User();
		user.setId((Integer) map.get("id"));
		user.setUsername((String) map.get("user_name"));
		user.setLoginname((String) map.get("login_name"));
		user.setPassword((String) map.get("password"));
		user.setRoletype((String) map.get("role_type"));
		return user;
	}
}
