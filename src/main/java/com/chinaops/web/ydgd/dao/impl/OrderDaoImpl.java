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

import com.chinaops.web.ydgd.bean.ProductFromBean;
import com.chinaops.web.ydgd.entity.Order;
import com.chinaops.web.ydgd.utils.JDBCUtils;

/**
 * 
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年8月26日 下午3:02:31
 */
public class OrderDaoImpl {
	
	private static final Log log = LogFactory.getLog(OrderDaoImpl.class);
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	/**
	 * 
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketIdS
	 * @return
	 */
	public List<Order> getProductType(String ticketIdS) {
		List<Order> lists = new ArrayList<Order>();
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			String listSql = "";
			listSql = "select * from orders  where ticket_id in ("+ticketIdS+") group by product_type";
			log.debug("getProductType-listSql:"+listSql);
			rs = stmt.executeQuery(listSql);
			while (rs.next()) {
	            Order order =new Order();
	            order.setProductType(rs.getString("product_type"));
			    lists.add(order);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return lists;
	}
	/**
	 * 
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketIdS
	 * @param productType
	 * @return
	 */
	public List<Order> getOrderByProductType(String ticketIdS,String productType) {
		List<Order> lists = new ArrayList<Order>();
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			String listSql = "";
			listSql = "select * from orders  where ticket_id in ("+ticketIdS+") and product_type='"+productType+"' order by cloud_platform";
			log.debug("getOrderByProductType-listSql:"+listSql);
			rs = stmt.executeQuery(listSql);
			while (rs.next()) {
	            Order order =new Order();
	            order.setId(rs.getInt("id"));
	            order.setTicketId(rs.getString("ticket_id"));
	            order.setContractId(rs.getString("contract_id"));
	            if(rs.getTimestamp("contract_signed_date") != null){
	            	String contractSignedDate = rs.getTimestamp("contract_signed_date").toString();
	            	order.setContractSignedDate(contractSignedDate.substring(0, contractSignedDate.length()-2));
	            }
	            if(rs.getTimestamp("contract_expired_date") != null){
	            	String contractExpiredDate = rs.getTimestamp("contract_expired_date").toString();
	            	order.setContractExpiredDate(contractExpiredDate.substring(0, contractExpiredDate.length()-2));
	            }
	            if(rs.getTimestamp("available_at") != null){
	            	String availableAt = rs.getTimestamp("available_at").toString();
	            	order.setAvailableAt(availableAt.substring(0, availableAt.length()-2));
	            }
	            order.setProductType(rs.getString("product_type"));
	            order.setForWebsite(rs.getString("is_website"));
	            order.setOpenPorts(rs.getString("open_ports"));
	            order.setPortsNeedToOpen(rs.getString("ports_need_to_open"));
	            
	            order.setBandwidth(rs.getString("band_width"));
	            order.setIp(rs.getString("ip"));
	            order.setIpCount(rs.getString("ip_count"));	
	            
	            order.setSizeInGB(rs.getString("sizeInGB"));
	            order.setDiscount(rs.getString("discount"));
	            
	            order.setSnapshot(rs.getString("snapshot"));
	            order.setKeypairs(rs.getString("keypairs"));
	            order.setSecuritys(rs.getString("securitys"));
	            order.setHa(rs.getString("ha"));
	            order.setElb(rs.getString("elb"));
	            order.setFilingNo(rs.getString("filing_no"));
	            order.setFilingIp(rs.getString("filing_ip"));
	            order.setFilingDomain(rs.getString("filing_domain"));
	            order.setMemo(rs.getString("memo"));
	            order.setIsFeesOwed(rs.getString("is_fees_owed"));
	            if(rs.getTimestamp("close_time") != null){
	            	String closeTime = rs.getTimestamp("close_time").toString();
	            	order.setCloseTime(closeTime.substring(0, closeTime.length()-2));
	            }
	            order.setReason(rs.getString("reason"));
	            order.setDetailedReason(rs.getString("detailed_reason"));
	            order.setLoginUrl(rs.getString("login_url"));
	            order.setCloudPlatform(rs.getString("cloud_platform"));
	            order.setLoginUsername(rs.getString("login_username"));
	            order.setLoginPassword(rs.getString("login_password"));
			    lists.add(order);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return lists;
	}
	/**
	 * 
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @return
	 */
	public Order getOrderByTicketId(String ticketId) {
		Order order =new Order();
		String sql = "select * from orders where ticket_id='"+ticketId+"';";
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
	            order.setId(rs.getInt("id"));
	            order.setTicketId(rs.getString("ticket_id"));
	            order.setContractId(rs.getString("contract_id"));
	            if(rs.getTimestamp("contract_signed_date") != null){
	            	String contractSignedDate = rs.getTimestamp("contract_signed_date").toString();
	            	order.setContractSignedDate(contractSignedDate.substring(0, contractSignedDate.length()-2));
	            }
	            if(rs.getTimestamp("contract_expired_date") != null){
	            	String contractExpiredDate = rs.getTimestamp("contract_expired_date").toString();
	            	order.setContractExpiredDate(contractExpiredDate.substring(0, contractExpiredDate.length()-2));
	            }
	            if(rs.getTimestamp("available_at") != null){
	            	String availableAt = rs.getTimestamp("available_at").toString();
	            	order.setAvailableAt(availableAt.substring(0, availableAt.length()-2));
	            }
	            order.setProductType(rs.getString("product_type"));
	            order.setForWebsite(rs.getString("is_website"));
	            order.setOpenPorts(rs.getString("open_ports"));
	            order.setPortsNeedToOpen(rs.getString("ports_need_to_open"));
	            
	            order.setBandwidth(rs.getString("band_width"));
	            order.setIp(rs.getString("ip"));
	            order.setIpCount(rs.getString("ip_count"));	
	            
	            order.setSizeInGB(rs.getString("sizeInGB"));
	            order.setDiscount(rs.getString("discount"));
	            
	            order.setSnapshot(rs.getString("snapshot"));
	            order.setKeypairs(rs.getString("keypairs"));
	            order.setSecuritys(rs.getString("securitys"));
	            order.setHa(rs.getString("ha"));
	            order.setElb(rs.getString("elb"));
	            order.setFilingNo(rs.getString("filing_no"));
	            order.setFilingIp(rs.getString("filing_ip"));
	            order.setFilingDomain(rs.getString("filing_domain"));
	            order.setMemo(rs.getString("memo"));
	            order.setIsFeesOwed(rs.getString("is_fees_owed"));
	            if(rs.getTimestamp("close_time") != null){
	            	String closeTime = rs.getTimestamp("close_time").toString();
	            	order.setCloseTime(closeTime.substring(0, closeTime.length()-2));
	            }
	            order.setReason(rs.getString("reason"));
	            order.setDetailedReason(rs.getString("detailed_reason"));
	            order.setLoginUrl(rs.getString("login_url"));
	            order.setCloudPlatform(rs.getString("cloud_platform"));
	            order.setLoginUsername(rs.getString("login_username"));
	            order.setLoginPassword(rs.getString("login_password"));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return order;
	}

	/**
	 * 接收来自移动的XML，获得产品订单信息写入数据库
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param order
	 */
	public Order addOrder(Order order) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into orders(contract_id,ticket_id,contract_signed_date,contract_expired_date,available_at,product_type,is_website,ports_need_to_open,sizeInGB,discount,band_width,ip_count,snapshot,ha,memo,filing_no,filing_ip,filing_domain) values('");
			sql.append(order.getContractId()+"','");
			sql.append(order.getTicketId()+"','");
			sql.append(order.getContractSignedDate()+"','");
			sql.append(order.getContractExpiredDate()+"','");
			sql.append(order.getAvailableAt()+"','");
			sql.append(order.getProductType()+"','");
			sql.append(order.getForWebsite()+"','");
			sql.append(order.getPortsNeedToOpen()+"','");
			sql.append(order.getStorage().getSizeInGB()+"','");
			sql.append(order.getStorage().getDiscount()+"','");
			sql.append(order.getBandwidth()+"','");
			sql.append(order.getIpCount()+"','");
			sql.append(order.getSnapshot()+"','");
			sql.append(order.getHa()+"','");
			sql.append(order.getMemo()+"','");
			sql.append(order.getFilingNo()+"','");
			sql.append(order.getFilingIp()+"','");
			sql.append(order.getFilingDomain()+"')");
			log.debug("addOrder:"+sql.toString());
			stmt.execute(sql.toString());
			return this.getOrderByTicketId(order.getTicketId());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	
	/**
	 * 接收来自移动的XML，获得产品订单信息修改
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param order
	 */
	public Order modifyOrder(Order order) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update orders set ");
			sql.append("contract_id='" + order.getContractId() + "', ");
			sql.append("ticket_id='" + order.getTicketId() + "', ");
			sql.append("contract_signed_date='" + order.getContractSignedDate() + "', ");
			sql.append("contract_expired_date='" + order.getContractExpiredDate() + "', ");
			sql.append("available_at='" + order.getAvailableAt() + "', ");
			sql.append("product_type='" + order.getProductType() + "', ");
			sql.append("is_website='" + order.getForWebsite() + "', ");
			sql.append("ports_need_to_open='" + order.getPortsNeedToOpen() + "', ");
			sql.append("sizeInGB='" + order.getStorage().getSizeInGB() + "', ");
			sql.append("discount='" + order.getStorage().getDiscount() + "', ");
			sql.append("band_width='" + order.getBandwidth() + "', ");
			sql.append("ip_count='" + order.getIpCount() + "', ");
			sql.append("snapshot='" + order.getSnapshot() + "', ");
			sql.append("ha='" + order.getHa() + "', ");
			sql.append("memo='" + order.getMemo() + "', ");
			sql.append("filing_no='" + order.getFilingNo() + "', ");
			sql.append("filing_ip='" + order.getFilingIp() + "', ");
			sql.append("filing_domain='" + order.getFilingDomain() + "' ");
			sql.append("where ticket_id='" + order.getTicketId()+"';");
			log.debug("modifyOrder:"+sql.toString());
			stmt.execute(sql.toString());
			return this.getOrderByTicketId(order.getTicketId());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	/**
	 * 开通、变更、开通做网站、变更做网站工单处理方法
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param productFromBean
	 * @return
	 */
	public boolean editOrderByOpenTicketType(ProductFromBean productFromBean) {
		try{
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update orders set ");
			sql.append("open_ports='"+productFromBean.getOpenPorts()+"', ");
			sql.append("ip='"+productFromBean.getIp()+"', ");
			sql.append("keypairs='"+productFromBean.getKeypairs()+"', ");
			sql.append("securitys='"+productFromBean.getSecuritys()+"', ");
			sql.append("login_url='"+productFromBean.getLoginUrl()+"', ");
			sql.append("cloud_platform='"+productFromBean.getCloudPlatform()+"', ");
			sql.append("login_username='"+productFromBean.getLoginUsername()+"', ");
			sql.append("login_password='"+productFromBean.getLoginPassword()+"' ");
			sql.append("where ticket_id='"+productFromBean.getTicketId()+"';");
			log.debug("editOrderByOpenTicketType:"+sql.toString());
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
	 * 接收来自移动的XML，撤销订单信息
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param order
	 */
	public void closeTicket(Order order) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update orders set ");
			sql.append("is_fees_owed='");
			sql.append(order.getIsFeesOwed() + "', ");
			sql.append("close_time='");
			sql.append(order.getCloseTime() + "', ");
			sql.append("reason='");
			sql.append(order.getReason() + "', ");
			sql.append("detailed_reason='");
			sql.append(order.getDetailedReason() + "', ");
			sql.append("memo='");
			sql.append(order.getMemo() + "', ");
			sql.append("' where ticket_id='");
			sql.append(order.getTicketId()+"';");
			log.debug("closeTicket:"+sql.toString());
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	
}
