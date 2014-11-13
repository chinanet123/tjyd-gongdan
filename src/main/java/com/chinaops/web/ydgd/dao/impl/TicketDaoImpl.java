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
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinaops.web.common.entity.Page;
import com.chinaops.web.ydgd.bean.ProductFromBean;
import com.chinaops.web.ydgd.entity.Ticket;
import com.chinaops.web.ydgd.utils.JDBCUtils;
import com.chinaops.web.ydgd.utils.StringHelper;

/**
 * 
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年8月26日 下午3:02:46
 */
public class TicketDaoImpl {
	
	private static final Log log = LogFactory.getLog(TicketDaoImpl.class);
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
/*
	public Page selectTicketByName(int pageNo, int pageSize,
			String fuzzySearchValue,String customerId) {
		Page page = new Page();
		int total = 0;
		List<Ticket> lists = new ArrayList<Ticket>();
		int currentPage = (pageNo - 1) * pageSize;
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			String countSql = "";
			String listSql = "";
			if (fuzzySearchValue.equals("") || fuzzySearchValue == null) {
				listSql = "select ticket.* ,customer.customer_name from ticket join customer on ticket.customer_id=customer.customer_id "
						+"where customer.customer_id= '"+customerId
						+"'"
						+ " order by id desc limit " + (currentPage) + ","
						+ pageSize + ";";
				countSql = "select count(*) from ticket join customer on ticket.customer_id=customer.customer_id "
						+"where customer.customer_id= '"+customerId
						+"'"
						;
								
			} else {
				listSql = "select ticket.* ,customer.customer_name from ticket join customer on ticket.customer_id=customer.customer_id ";
				listSql +="where customer.customer_id= '"+customerId+"'";
				listSql += "and customer.customer_name like '%" + fuzzySearchValue
						+ "%'";
				listSql += " order by id desc limit " + (currentPage) + ","
						+ pageSize + ";";
				countSql = "select count(*) from ticket join customer on ticket.customer_id=customer.customer_id ";
				countSql += "where customer.customer_id= '"+customerId+"'";
				countSql += " and customer.customer_name like '%" + fuzzySearchValue
						+ "%'";
			}
			//countSql="select count(*) from ticket join customer on ticket.customer_id=customer.customer_id and  "+" customer.customer_name like '%" + fuzzySearchValue
				//	+ "%'";
			rs = stmt.executeQuery(countSql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs = stmt.executeQuery(listSql);
			while (rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getInt("id"));
				ticket.setTicketId(rs.getString("ticket_id"));
				ticket.setCustomerId(rs.getString("customer_id"));
				ticket.setTicketType(rs.getString("ticket_type"));
				ticket.setTicketState(rs.getString("ticket_state"));
				if(rs.getTimestamp("receive_time") != null){
					String receiveTime = rs.getTimestamp("receive_time").toString();
					ticket.setReceiveTime(receiveTime.substring(0,receiveTime.length()-2));
				}
				ticket.setIsUse(rs.getInt("is_use"));
				ticket.setRemark(rs.getString("remark"));
				if(rs.getTimestamp("send_time") != null){
					String sendTime = rs.getTimestamp("send_time").toString();
					ticket.setSendTime(sendTime.substring(0,sendTime.length()-2));
				}
				ticket.setCustomerName(rs.getString("customer_name"));
				ticket.setIsModify(rs.getInt("is_modify"));
				lists.add(ticket);
			}

			// 总记录数
			page.setTotalNumber(total);
			// 当前页
			page.setCurrentPage(pageNo);
			//按多少条记录进行分页
			page.setPageSize(pageSize);
			// 分页的数据
			page.setList(lists);
			// 总页数
			page.setTotalPage(page.getTotalNumber() % pageSize == 0 ? page
					.getTotalNumber() / pageSize : page.getTotalNumber()
					/ pageSize + 1);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return page;
	}
*/
	/**
	 * 页面跳转至【资源概况】页面：显示部分客户信息（提供按钮可展开显示全部客户信息）；
	 * 根据产品类型分为3个标签（共享云、专享云、云存储，仅显示开通资源的产品的标签），
	 * 每个产品下根据产品所属云平台进行资源汇总。
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param customerId
	 * @return
	 */
	public List<Ticket> getOpenTicketByCustomerId(String customerId) {
		List<Ticket> lists = new ArrayList<Ticket>();
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			String listSql = "";
			listSql = "select * from ticket where customer_id= '"+customerId+"' and is_use='1' and (ticket_type='open' or ticket_type='modify' ) order by receive_time desc";
			rs = stmt.executeQuery(listSql);
			log.debug("getOpenTicketByCustomerId:"+listSql);
			while (rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getInt("id"));
				ticket.setTicketId(rs.getString("ticket_id"));
				ticket.setCustomerId(rs.getString("customer_id"));
				ticket.setTicketType(rs.getString("ticket_type"));
				ticket.setTicketState(rs.getString("ticket_state"));
				if(rs.getTimestamp("receive_time") != null){
					String receiveTime = rs.getTimestamp("receive_time").toString();
					ticket.setReceiveTime(receiveTime.substring(0,receiveTime.length()-2));
				}
				ticket.setIsUse(rs.getInt("is_use"));
				ticket.setRemark(rs.getString("remark"));
				if(rs.getTimestamp("send_time") != null){
					String sendTime = rs.getTimestamp("send_time").toString();
					ticket.setSendTime(sendTime.substring(0,sendTime.length()-2));
				}
				ticket.setIsModify(rs.getInt("is_modify"));
				lists.add(ticket);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return lists;
	}
	/**
	 * 根据客户编号获取该客户所有工单
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param customerId
	 * @return
	 */
	public List<Ticket> getTicketsByCustomerId(String customerId) {
		List<Ticket> lists = new ArrayList<Ticket>();
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			String listSql = "";
			listSql = "select * from ticket where is_use='1' and customer_id= '"+customerId+"' order by receive_time desc";
			log.debug("getTicketsByCustomerId:"+listSql);
			rs = stmt.executeQuery(listSql);
			while (rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getInt("id"));
				ticket.setTicketId(rs.getString("ticket_id"));
				ticket.setCustomerId(rs.getString("customer_id"));
				ticket.setTicketType(rs.getString("ticket_type"));
				ticket.setTicketState(rs.getString("ticket_state"));
				if(rs.getTimestamp("receive_time") != null){
					String receiveTime = rs.getTimestamp("receive_time").toString();
					ticket.setReceiveTime(receiveTime.substring(0,receiveTime.length()-2));
				}
				ticket.setIsUse(rs.getInt("is_use"));
				ticket.setRemark(rs.getString("remark"));
				if(rs.getTimestamp("send_time") != null){
					String sendTime = rs.getTimestamp("send_time").toString();
					ticket.setSendTime(sendTime.substring(0,sendTime.length()-2));
				}
				ticket.setIsModify(rs.getInt("is_modify"));
				lists.add(ticket);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return lists;
	}
	/**
	 * 根据客户ID获取该客户所有工单（根据工单类型筛选查询该客户的工单）
	 * @author liqiang.zhang@china-ops.com
	 * @param pageNo
	 * @param pageSize
	 * @param ticketType
	 * @param customerId
	 * @return
	 */
	public Page getTicketPageByCustomerId(int pageNo, int pageSize, String ticketType, String customerId){
		Page page = new Page();
	    int total = 0;
	    int  currentPage = (pageNo - 1) * pageSize;
	    List<Ticket> lists = new ArrayList<Ticket>();
	    String where = " where t.is_use='1' and c.is_use='1' and  t.customer_id = c.customer_id ";
	    String sql = "select c.customer_name ,t.* from ticket t , customer c ";
	    String sqlCount = "select count(*) from ticket t , customer c ";
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			if(ticketType != null && !ticketType.equals("")){
				where += " and t.ticket_type = '" + ticketType +"' and c.customer_id = '"+ customerId +"' ";
			}else{
				where += " and c.customer_id = '"+ customerId +"' ";
			}
			
			sql += where + " " +  "order by t.receive_time DESC limit " + (currentPage) + "," + pageSize + ";";
			sqlCount += where;
			log.debug("getTicketPageByCustomerId-sql:"+sql);
			log.debug("getTicketPageByCustomerId-sqlCount:"+sqlCount);
			rs = stmt.executeQuery(sqlCount);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getInt("id"));
				ticket.setTicketId(rs.getString("ticket_id"));
				ticket.setCustomerId(rs.getString("customer_id"));
				ticket.setTicketType(rs.getString("ticket_type"));
				ticket.setTicketState(rs.getString("ticket_state"));
				if(rs.getTimestamp("receive_time") != null){
					String receiveTime = rs.getTimestamp("receive_time").toString();
					ticket.setReceiveTime(receiveTime.substring(0,receiveTime.length()-2));
				}
				ticket.setIsUse(rs.getInt("is_use"));
				ticket.setRemark(rs.getString("remark"));
				if(rs.getTimestamp("send_time") != null){
					String sendTime = rs.getTimestamp("send_time").toString();
					ticket.setSendTime(sendTime.substring(0,sendTime.length()-2));
				}
				ticket.setCustomerName(rs.getString("customer_name"));
				ticket.setIsModify(rs.getInt("is_modify"));
				lists.add(ticket);
			}
	        // 总记录数
			page.setTotalNumber(total);
			// 当前页
			page.setCurrentPage(pageNo);
			//按多少条记录进行分页
			page.setPageSize(pageSize);
			// 分页的数据
			page.setList(lists);
			// 总页数
			page.setTotalPage(page.getTotalNumber() % pageSize == 0 ? page.getTotalNumber() / pageSize : page.getTotalNumber()/pageSize + 1);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return page;
	}
	
	/**
	 * 查询所有工单（根据工单类型或客户名称筛选查询）
	 * @author liqiang.zhang@china-ops.com
	 * @param pageNo
	 * @param pageSize
	 * @param ticketType
	 * @param searchValue(customerName)
	 * @return
	 */
	public Page getTicketPage(int pageNo, int pageSize, String ticketType, String searchValue) {
		Page page = new Page();
	    List<Ticket> lists = new ArrayList<Ticket>();
	    int  currentPage = (pageNo - 1) * pageSize;
	    int total = 0;
	    String where = " where t.is_use='1' and c.is_use='1' and  t.customer_id = c.customer_id ";
	    String sql = "select c.customer_name ,t.* from ticket t , customer c ";
	    String sqlCount = "select count(*) from ticket t , customer c ";
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			if(ticketType != null && !ticketType.equals("")){
				if(searchValue != null && !searchValue.equals("")){
					where += " and t.ticket_type = '" + ticketType +"' and c.customer_name like '%"+ searchValue +"%' ";
				}else {
					where += " and t.ticket_type = '" + ticketType +"' ";
				}
			}else{
				if(searchValue != null && !searchValue.equals("")){
					where += " and c.customer_name like '%"+ searchValue +"%' ";
				}
			}
			
			sql += where + " " +  "order by t.receive_time desc limit " + (currentPage) + "," + pageSize + ";";
			sqlCount += where;
			log.debug("getTicketPage-sql:"+sql);
			log.debug("getTicketPage-sqlCount="+sqlCount);
			rs = stmt.executeQuery(sqlCount);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getInt("id"));
				ticket.setTicketId(rs.getString("ticket_id"));
				ticket.setCustomerId(rs.getString("customer_id"));
				ticket.setTicketType(rs.getString("ticket_type"));
				ticket.setTicketState(rs.getString("ticket_state"));
				if(rs.getTimestamp("receive_time") != null){
					String receiveTime = rs.getTimestamp("receive_time").toString();
					ticket.setReceiveTime(receiveTime.substring(0,receiveTime.length()-2));
				}
				ticket.setIsUse(rs.getInt("is_use"));
				ticket.setRemark(rs.getString("remark"));
				if(rs.getTimestamp("send_time") != null){
					String sendTime = rs.getTimestamp("send_time").toString();
					ticket.setSendTime(sendTime.substring(0,sendTime.length()-2));
				}
				ticket.setCustomerName(rs.getString("customer_name"));
				ticket.setIsModify(rs.getInt("is_modify"));
				lists.add(ticket);
			}
	        // 总记录数
			page.setTotalNumber(total);
			// 当前页
			page.setCurrentPage(pageNo);
			//按多少条记录进行分页
			page.setPageSize(pageSize);
			// 分页的数据
			page.setList(lists);
			// 总页数
			page.setTotalPage(page.getTotalNumber() % pageSize == 0 ? page.getTotalNumber() / pageSize : page.getTotalNumber()/pageSize + 1);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return page;
	}
	/**
	 * 未处理工单查询
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param pageNo
	 * @param pageSize
	 * @param ticketType
	 * @param searchValue
	 * @return
	 */
	public Page getTicketPageIsModify(int pageNo, int pageSize, String ticketType, String searchValue) {
		Page page = new Page();
	    List<Ticket> lists = new ArrayList<Ticket>();
	    int  currentPage = (pageNo - 1) * pageSize;
	    int total = 0;
	    String where = " where t.is_modify='1' and t.is_use='1' and c.is_use='1' and t.customer_id = c.customer_id ";
	    String sql = "select c.customer_name ,t.* from ticket t,customer c ";
	    String sqlCount = "select count(*) from ticket t , customer c  ";
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			if(ticketType != null && !ticketType.equals("")){
				if(searchValue != null && !searchValue.equals("")){
					where += " and t.ticket_type = '" + ticketType +"' and c.customer_name like '%"+ searchValue +"%' ";
				}else {
					where += " and t.ticket_type = '" + ticketType +"' ";
				}
			}else{
				if(searchValue != null && !searchValue.equals("")){
					where += " and c.customer_name like '%"+ searchValue +"%' ";
				}
			}
			
			sql += where + " " +  "order by t.receive_time limit " + (currentPage) + "," + pageSize + ";";
			sqlCount += where;
			log.debug("sql = "+sql +"\nsqlCount="+sqlCount);
			rs = stmt.executeQuery(sqlCount);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getInt("id"));
				ticket.setTicketId(rs.getString("ticket_id"));
				ticket.setCustomerId(rs.getString("customer_id"));
				ticket.setTicketType(rs.getString("ticket_type"));
				ticket.setTicketState(rs.getString("ticket_state"));
				if(rs.getTimestamp("receive_time") != null){
					String receiveTime = rs.getTimestamp("receive_time").toString();
					ticket.setReceiveTime(receiveTime.substring(0,receiveTime.length()-2));
				}
				ticket.setIsUse(rs.getInt("is_use"));
				ticket.setRemark(rs.getString("remark"));
				if(rs.getTimestamp("send_time") != null){
					String sendTime = rs.getTimestamp("send_time").toString();
					ticket.setSendTime(sendTime.substring(0,sendTime.length()-2));
				}
				ticket.setCustomerName(rs.getString("customer_name"));
				ticket.setIsModify(rs.getInt("is_modify"));
				lists.add(ticket);
			}
	        // 总记录数
			page.setTotalNumber(total);
			// 当前页
			page.setCurrentPage(pageNo);
			//按多少条记录进行分页
			page.setPageSize(pageSize);
			// 分页的数据
			page.setList(lists);
			// 总页数
			page.setTotalPage(page.getTotalNumber() % pageSize == 0 ? page.getTotalNumber() / pageSize : page.getTotalNumber()/pageSize + 1);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return page;
	}
	/**
	 * 根据工单ID查询该工单
	 * @author liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @return
	 */
	public Ticket getTicketByTicketId(String ticketId) {
		Ticket ticket = new Ticket();
		String sql = "select * from ticket where is_use=1 and ticket_id = '" + ticketId + "';";
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			log.debug("getTicketByTicketId"+sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ticket.setId(rs.getInt("id"));
				ticket.setTicketId(rs.getString("ticket_id"));
				ticket.setCustomerId(rs.getString("customer_id"));
				ticket.setTicketType(rs.getString("ticket_type"));
				ticket.setTicketState(rs.getString("ticket_state"));
				if(rs.getTimestamp("receive_time") != null){
					String receiveTime = rs.getTimestamp("receive_time").toString();
					ticket.setReceiveTime(receiveTime.substring(0,receiveTime.length()-2));
				}
				ticket.setIsUse(rs.getInt("is_use"));
				ticket.setRemark(rs.getString("remark"));
				if(rs.getTimestamp("send_time") != null){
					String sendTime = rs.getTimestamp("send_time").toString();
					ticket.setSendTime(sendTime.substring(0,sendTime.length()-2));
				}
				ticket.setIsModify(rs.getInt("is_modify"));
			}
			return ticket;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	/**
	 * 接收移动传来的数据写入数据库
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param ticket
	 */
	public void addTicket(Ticket ticket) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into ticket(ticket_id,ticket_type,ticket_state,customer_id,receive_time) values('");
			sql.append(ticket.getTicketId()+"','");
			sql.append(ticket.getTicketType()+"','");
			sql.append(ticket.getTicketState()+"','");
			sql.append(ticket.getCustomerId()+"','");
			sql.append(ticket.getReceiveTime());
			sql.append("')");
			log.debug("addTicket:"+sql.toString());
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	
	/**
	 * 接收移动传来的数据修改
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param ticket
	 */
	public void modifyTicket(Ticket ticket) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update ticket set ");
			sql.append("ticket_id='");
			sql.append(ticket.getTicketId());
			sql.append("',ticket_type='");
			sql.append(ticket.getTicketType());
			sql.append("',ticket_state='");
			sql.append(ticket.getTicketState());
			sql.append("',customer_id='");
			sql.append(ticket.getCustomerId());
			sql.append("',receive_time='");
			sql.append(ticket.getReceiveTime());
			sql.append("' where ticket_id='");
			sql.append(ticket.getTicketId()+"';");
			log.debug("modifyTicket:"+sql.toString());
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	public void modifyTicketSetIsUser(Ticket ticket) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update ticket set ");
			sql.append("is_use='");
			sql.append(0);
			sql.append("' where ticket_id='");
			sql.append(ticket.getTicketId()+"';");
			log.debug("modifyTicketSetIsUser:"+sql.toString());
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	/**
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @return
	 */
	public boolean editTicketForTicketTypeOpen(ProductFromBean product) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update ticket set ");
			sql.append("ticket_state='"+product.getTicketState()+"', ");
			sql.append("remark='"+product.getRemark()+"', ");
			sql.append("send_time='"+StringHelper.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")+"', ");
			sql.append("is_modify='0' ");
			sql.append(" where is_use=1 and ticket_id='" + product.getTicketId() + "' ;");
			log.debug("editTicketForTicketTypeOpen:"+sql.toString());
			int ret = stmt.executeUpdate(sql.toString());
			if(ret > 0){
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return false;
	}
	/**
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @param remark
	 * @return
	 */
	public boolean editTicketForTicketTypeClose(ProductFromBean product) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update ticket set ");
			sql.append("remark='" + product.getRemark() + "', ");
			sql.append("is_modify='0' ");
			sql.append(" where is_use=1 and ticket_id='" + product.getTicketId() + "' ;");
			log.debug("editTicketForTicketTypeClose:"+sql.toString());
			int ret = stmt.executeUpdate(sql.toString());
			if(ret > 0){
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return false;
	}
	
}
