package com.chinaops.web.ydgd.dao.Impl;

/**
 * China-Ops Inc. All Rights Reserved.
 * Author:liuyajuan
 * 2014/8/12
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinaops.web.ydgd.entity.Suite;
import com.chinaops.web.ydgd.utils.JDBCUtils;

/**
 * 
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年8月26日 下午3:02:17
 */
public class SuiteDaoImpl  {
	
	private static final Log log = LogFactory.getLog(SuiteDaoImpl.class);
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	/**
	 * 根据工单所对应订单的套餐集合
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @param orderId
	 * @return
	 */
	public List<Suite> getSuiteListByTicketId(String ticketId,int orderId){
		List<Suite> suites = new ArrayList<Suite>();
		String sql = "select * from suite where is_use=1 and ticket_id='"+ ticketId+"' and order_id='" + orderId + "' ;";
		log.debug("getSuiteListByTicketId-sql:"+sql);
		try{
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Suite suite = new Suite();
				suite.setId(rs.getInt("id"));
				suite.setTicketId(rs.getString("ticket_id"));
				suite.setOrderId(rs.getString("order_id"));
				suite.setName(rs.getString("name"));
				suite.setCount(rs.getString("count"));
				suite.setDiscount(rs.getString("discount"));
				suite.setDesc(rs.getString("description"));
				suite.setIsUse(rs.getInt("is_use"));
				suites.add(suite);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return suites;
	}
	/**
	 * 添加工单所对应订单的套餐
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param suite
	 */
	public void addSuites(Suite suite){
		try{
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			String sql = "insert into suite(ticket_id,order_id,name,discount,count,description,create_time) values('"+suite.getTicketId()+"','"+suite.getOrderId()+"','"+suite.getName()+"','"+suite.getDiscount()+"','"+suite.getCount()+"','"+suite.getDesc()+"',sysdate());";
			log.debug("addSuites:"+sql);
			stmt.execute(sql);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	
	/**
	 * 修改已存在工单和订单所对应的套餐为不可用
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param suite
	 */
	public void modifySuitesState(Suite suite){
		try{
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update suite set ");
			sql.append("is_use=0 ");
			sql.append("where ticket_id='" + suite.getTicketId() + "' ");
			sql.append("and order_id='" + suite.getOrderId() +"' ;");
			log.debug("modifySuitesState-sql:"+sql.toString());
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
}
