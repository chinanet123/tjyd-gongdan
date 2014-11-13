package com.chinaops.web.ydgd.service;
/**
 * China-Ops Inc. All Rights Reserved.
 * Author:liuyajuan
 * 2014/8/12
 */
import java.util.List;

import com.chinaops.web.common.entity.Page;
import com.chinaops.web.ydgd.bean.ProductFromBean;
import com.chinaops.web.ydgd.dao.Impl.TicketDaoImpl;
import com.chinaops.web.ydgd.entity.Ticket;

/**
 * Description:工单
 */
public class TicketService {

   private TicketDaoImpl ticketDaoImpl;

	public TicketDaoImpl getTicketDaoImpl() {
		return ticketDaoImpl;
	}

	public void setTicketDaoImpl(TicketDaoImpl ticketDaoImpl) {
		this.ticketDaoImpl = ticketDaoImpl;
	}
/*
	// 工单按名称查询
	public Page selectTicketByName(int pageNo, int pageSize, String fuzzySearchValue,String customerId) {
		return this.ticketDaoImpl.selectTicketByName(pageNo, pageSize, fuzzySearchValue,customerId);
	}
*/
	/**
	 * 根据客户编号查询所有开通资源的工单
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param customerId
	 * @return
	 */
	public List<Ticket> getOpenTicketByCustomerId(String customerId ) {
		return this.ticketDaoImpl.getOpenTicketByCustomerId(customerId);

	}
	/**
	 * 根据客户编号获取该客户所有的工单
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param customerId
	 * @return
	 */
	public List<Ticket> getTicketsByCustomerId(String customerId ) {
		return this.ticketDaoImpl.getTicketsByCustomerId(customerId);
	
	}
	/**
	 * 获取某客户的所有工单（根据工单类型模糊查询）
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param pageNo
	 * @param pageSize
	 * @param ticketType
	 * @param customerId
	 * @return
	 */
	public Page getTicketPageByCustomerId(int pageNo, int pageSize, String ticketType, String customerId) {
		return this.ticketDaoImpl.getTicketPageByCustomerId(pageNo, pageSize, ticketType,customerId);
	}
	/**
	 * 获取所有工单（根据工单类型或客户名称模糊查询）
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param pageNo
	 * @param pageSize
	 * @param ticketType
	 * @param searchValue
	 * @return
	 */
	public Page getTicketPage(int pageNo, int pageSize, String ticketType, String searchValue) {
		return this.ticketDaoImpl.getTicketPage(pageNo, pageSize, ticketType,searchValue);
	}
	/**
	 * 根据工单编号（ticketId）获取该工单的详情
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @return
	 */
	public Ticket getTicketByTicketId(String ticketId) {
		return this.ticketDaoImpl.getTicketByTicketId(ticketId);
	}
	/**
	 * 获取移动的数据保存如本地库
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param ticket
	 */
	public void addTicket(Ticket ticket) {
	   this.ticketDaoImpl.addTicket(ticket);
	}
	/**
	 * 获取移动的数据保存如本地库
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param ticket
	 */
	public void modifyTicket(Ticket ticket) {
		this.ticketDaoImpl.modifyTicket(ticket);
	}
	/**
	 * 获取移动的数据保存如本地库，原ticket置为不可用
	 * @author 马宁涛
	 * @email  ningtao.ma@china-ops.com
	 * @param ticket
	 */
	public void modifyTicketSetIsUser(Ticket ticket) {
		this.ticketDaoImpl.modifyTicketSetIsUser(ticket);
	}

	/**
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @param customerId
	 * @return
	 */
	public boolean editTicketForTicketTypeOpen(ProductFromBean product) {
		return this.ticketDaoImpl.editTicketForTicketTypeOpen(product);
	}

	/**
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @param remark
	 * @return
	 */
	public boolean editTicketForTicketTypeClose(ProductFromBean product) {
		return this.ticketDaoImpl.editTicketForTicketTypeClose(product);
	}

	/**
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param pageNo
	 * @param pageSize
	 * @param ticketType
	 * @param searchValue
	 * @return
	 */
	public Page getTicketPageIsModify(int pageNo, int pageSize, String ticketType, String searchValue) {
		return this.ticketDaoImpl.getTicketPageIsModify(pageNo,pageSize,ticketType,searchValue);
	}

	
	
}
