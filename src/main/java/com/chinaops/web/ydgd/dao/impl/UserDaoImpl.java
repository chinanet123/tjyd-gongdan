package com.chinaops.web.ydgd.dao.impl;

import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;

import com.chinaops.web.ydgd.dao.UserDao;
import com.chinaops.web.ydgd.entity.User;
import com.chinaops.web.ydgd.utils.BaseDao;

public class UserDaoImpl extends BaseDao implements UserDao {
	
	private static String LONGIN = "select * from user where login_name=? and password=?";
	
	@SuppressWarnings("deprecation")
	public User authLogin(User user) {
		try {
			Map<String, Object> map = getSimpleJdbcTemplate().queryForMap(LONGIN,user.getLoginname(),user.getPassword());
	        if (map != null && map.size() > 0) {
	            return mapToUser(map);
	        }
		} catch (EmptyResultDataAccessException e) {
            return null;
        }
        return null;
	}

	
	public boolean updateUser(User user) {
		int ret = getSimpleJdbcTemplate().update("update user set user_name=? ,login_name=?,password=? where id=2", user.getUsername(),user.getLoginname(),user.getPassword());
		if(ret >0 ){
			return true;
		}
		return false;
		
	}
}
