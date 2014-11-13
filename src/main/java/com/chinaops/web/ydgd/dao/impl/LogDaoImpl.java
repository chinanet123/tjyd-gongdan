/**
 * 
 */
package com.chinaops.web.ydgd.dao.Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinaops.web.ydgd.utils.JDBCUtils;

/**
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年9月16日 下午7:24:51
 */
public class LogDaoImpl {
	private static final Log log = LogFactory.getLog(SuiteDaoImpl.class);
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public void writeRequestLog(String xml){
		String sql = "insert into logs(xml,request_time) values('"+xml+"',sysdate())";
		try{
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			log.debug("writeRequestLog:"+sql);
			stmt.execute(sql);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	public void writeResposeLog(String xml){
		String sql = "insert into logs(xml,response_time) values('"+xml+"',sysdate())";
		try{
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			log.debug("writeResposeLog:"+sql);
			stmt.execute(sql);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
}
