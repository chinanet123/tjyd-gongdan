package com.chinaops.web.ydgd.service;
/**
 * China-Ops Inc. All Rights Reserved.
 * Author:liuyajuan
 * 2014/8/12
 */
import java.util.List;

import com.chinaops.web.ydgd.bean.ProductFromBean;
import com.chinaops.web.ydgd.dao.Impl.OrderDaoImpl;
import com.chinaops.web.ydgd.entity.Order;

/**
 * Description:定单
 */
public class OrderService {

   private OrderDaoImpl orderDaoImpl;

	public OrderDaoImpl getOrderDaoImpl() {
		return orderDaoImpl;
	}

	public void setOrderDaoImpl(OrderDaoImpl orderDaoImpl) {
		this.orderDaoImpl = orderDaoImpl;
	}


	//查询某个客户的产品类型
	/**
	 * 根据工单编号（ticketId）获取产品类型
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketIdS
	 * @return
	 */
	public List<Order> getProductTypes(String ticketIdS ) {
		return this.orderDaoImpl.getProductType(ticketIdS);

	}
	//查询某个客户某个产品的订单信息
	/**
	 * 根据工单编号和产品类型
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketIdS
	 * @param productType
	 * @return
	 */
	public List<Order> getOrderByProductType(String ticketIdS,String productType){
		return this.orderDaoImpl.getOrderByProductType(ticketIdS, productType);

	}

	public Order getOrderByTicketId(String ticketId) {
		return this.orderDaoImpl.getOrderByTicketId(ticketId);
	}

	public Order addOrder(Order order) {
		return this.orderDaoImpl.addOrder(order);
	}

	public Order modifyOrder(Order order) {
		return this.orderDaoImpl.modifyOrder(order);
	}

	/**
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param  product
	 */
	public boolean editOrderByOpenTicketType(ProductFromBean product) {
		return orderDaoImpl.editOrderByOpenTicketType(product);
	}
	/**
	 * 
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param order
	 */
	public void closeTicket(Order order) {
		this.orderDaoImpl.closeTicket(order);
	}
}
