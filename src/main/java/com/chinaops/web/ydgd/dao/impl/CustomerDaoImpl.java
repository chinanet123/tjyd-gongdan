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

import com.chinaops.web.common.entity.Page;
import com.chinaops.web.ydgd.entity.Customer;
import com.chinaops.web.ydgd.utils.JDBCUtils;

/**
 * 
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年8月26日 下午3:02:17
 */
public class CustomerDaoImpl  {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	/**
	 * 获取客户列表（分页获取，并且根据客户名称模糊查询）
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param pageNo
	 * @param pageSize
	 * @param fuzzySearchValue
	 * @return
	 */
	public Page getCustomerPages(int pageNo, int pageSize, String fuzzySearchValue) {
		Page page = new Page();
		int total = 0;
		List<Customer> lists = new ArrayList<Customer>();
		int currentPage = (pageNo - 1) * pageSize;
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			String countSql = "";
			String listSql = "";
			if (fuzzySearchValue.equals("") || fuzzySearchValue == null) {
				listSql = "select * from customer where is_use=1 " + " order by id desc limit " + (currentPage) + "," + pageSize + ";";
				countSql = "select count(*) from customer where is_use=1 "; 
			} else {
				listSql = "select * from customer  where is_use=1 and customer_name like '%" + fuzzySearchValue + "%' order by id desc limit " + (currentPage) + "," + pageSize + ";";
				countSql = "select count(*) from customer  where is_use=1 and customer_name like '%" + fuzzySearchValue + "%'";
			}
			
			rs = stmt.executeQuery(countSql);
			while (rs.next()) {
				total = rs.getInt(1);
			}

			rs = stmt.executeQuery(listSql);
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setCustomerId(rs.getString("customer_id"));
				customer.setCustomerName(rs.getString("customer_name"));
				customer.setCustomerAddress(rs.getString("customer_address"));
				customer.setPostalCode(rs.getString("postal_code"));
				customer.setCategory(rs.getString("category"));
				customer.setB_contact(rs.getString("b_contact"));
				customer.setB_phone(rs.getString("b_phone"));
				customer.setB_email(rs.getString("b_email"));
				customer.setT_contact(rs.getString("t_contact"));
				customer.setT_phone(rs.getString("t_phone"));
				customer.setT_email(rs.getString("t_email"));
				customer.setC_contact(rs.getString("c_contact"));
				customer.setC_phone(rs.getString("c_phone"));
				customer.setC_email(rs.getString("c_email"));
				lists.add(customer);
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
			page.setTotalPage(page.getTotalNumber() % pageSize == 0 ? page.getTotalNumber() / pageSize : page.getTotalNumber() / pageSize + 1);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
		return page;
	}
	/**
	 * 根据客户ID获取客户信息
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param customerId
	 * @return
	 */
	public Customer getCustomerBycustomerId(String customerId) {
		Customer customer = new Customer();
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from customer where is_use=1 and customer_id='"+ customerId+"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				customer.setId(rs.getInt("id"));
				customer.setCustomerId(rs.getString("customer_id"));
				customer.setCustomerName(rs.getString("customer_name"));
				customer.setCustomerAddress(rs.getString("customer_address"));
				customer.setPostalCode(rs.getString("postal_code"));
				customer.setCategory(rs.getString("category"));
				customer.setB_contact(rs.getString("b_contact"));
				customer.setB_phone(rs.getString("b_phone"));
				customer.setB_email(rs.getString("b_email"));
				customer.setT_contact(rs.getString("t_contact"));
				customer.setT_phone(rs.getString("t_phone"));
				customer.setT_email(rs.getString("t_email"));
				customer.setC_contact(rs.getString("c_contact"));
				customer.setC_phone(rs.getString("c_phone"));
				customer.setC_email(rs.getString("c_email"));
			}
			return customer;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	/**
	 * 根据移动发送的信息添加客户信息
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param customer
	 */
	public void addCustomer(Customer customer) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into customer(customer_id,customer_name,customer_address,postal_code,category,b_contact,b_phone,b_email,t_contact,t_phone,t_email,c_contact,c_phone,c_email) values('");
			sql.append(customer.getCustomerId()+"','");
			sql.append(customer.getCustomerName()+"','");
			sql.append(customer.getCustomerAddress()+"','");
			sql.append(customer.getPostalCode()+"','");
			sql.append(customer.getCategory()+"','");
			sql.append(customer.getB_contact()+"','");
			sql.append(customer.getB_phone()+"','");
			sql.append(customer.getB_email()+"','");
			sql.append(customer.getT_contact()+"','");
			sql.append(customer.getT_phone()+"','");
			sql.append(customer.getT_email()+"','");
			sql.append(customer.getC_contact()+"','");
			sql.append(customer.getC_phone()+"','");
			sql.append(customer.getC_email());
			sql.append("')");
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	/**
	 * 根据移动发送的信息修改客户信息
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param customer
	 */
	public void modifyCustomer(Customer customer) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update customer set ");
			sql.append("customer_id='");
			sql.append(customer.getCustomerId());
			sql.append("',customer_name='");
			sql.append(customer.getCustomerName());
			sql.append("',customer_address='");
			sql.append(customer.getCustomerAddress());
			sql.append("',postal_code='");
			sql.append(customer.getPostalCode());
			sql.append("',category='");
			sql.append(customer.getCategory());
			sql.append("',b_contact='");
			sql.append(customer.getB_contact());
			sql.append("',b_phone='");
			sql.append(customer.getB_phone());
			sql.append("',b_email='");
			sql.append(customer.getB_email());
			sql.append("',t_contact='");
			sql.append(customer.getT_contact());
			sql.append("',t_phone='");
			sql.append(customer.getT_phone());
			sql.append("',t_email='");
			sql.append(customer.getT_email());
			sql.append("',c_contact='");
			sql.append(customer.getC_contact());
			sql.append("',c_phone='");
			sql.append(customer.getC_phone());
			sql.append("',c_email='");
			sql.append(customer.getC_email());
			sql.append("' where customer_id='");
			sql.append(customer.getCustomerId()+"';");
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	public void modifyCustomerSetIsUse(Customer customer) {
		try {
			conn = JDBCUtils.getConnection();
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("update customer set ");
			sql.append("is_use='");
			sql.append(0);
			sql.append("' where customer_id='");
			sql.append(customer.getCustomerId()+"';");
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResource(conn, stmt, rs);
		}
	}
	
}
