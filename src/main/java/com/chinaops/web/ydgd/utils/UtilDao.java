package com.chinaops.web.ydgd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.KeyHolder;

public abstract class UtilDao extends SimpleJdbcDaoSupport {

	protected int saveObjAndReturnIncId(final String sql, KeyHolder kh, final Object... args) {
		return getSimpleJdbcTemplate().getJdbcOperations().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				int idx = 1;
				for (Object arg : args) {
					ps.setObject(idx++, arg);
				}
				return ps;
			}
		}, kh);
	}
}
