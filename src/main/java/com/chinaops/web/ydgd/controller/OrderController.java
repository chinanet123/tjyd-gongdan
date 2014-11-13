package com.chinaops.web.ydgd.controller;
/**
 * China-Ops Inc. All Rights Reserved.
 * Author:liuyajuan
 * 2014/8/12
 */
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaops.web.ydgd.entity.Order;
import com.chinaops.web.ydgd.entity.Ticket;
import com.chinaops.web.ydgd.service.OrderService;
import com.chinaops.web.ydgd.service.TicketService;



/**
 * Description:
 * ticket
 */
@Controller
public class OrderController {
	@SuppressWarnings("unused")
	private static final Log  log=LogFactory.getLog(OrderController.class);
	
	private TicketService ticketService;
	
	private OrderService orderService;
	
	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Autowired
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	/**
	 * 跳转到订单列表页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/order.htm")
	public String clientPageShow( HttpServletRequest request, HttpServletResponse response){
		return "order/order";
	}
	
	/**
	 * 根据客户ID获得该客户所有的产品类型
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/order_get_product_type_by_ustomer_id.do", method = RequestMethod.POST)
	public @ResponseBody
	List<Order> getProductTypes(@RequestParam String customerId) {
		//查询属于某一个客户的开通工单
		List<Ticket> ticketList=ticketService.getOpenTicketByCustomerId(customerId);
		String  ticketIdString= new String();
		if (ticketList.size()>0) {
			for (Ticket ticket : ticketList) {
				ticketIdString+="'"+ticket.getTicketId()+"',";
				
			}
		}
		List<Order> productTypes=new ArrayList<Order>() ;
		if (!ticketIdString.equals("")) {
			productTypes = orderService.getProductTypes(ticketIdString.substring(0, ticketIdString.length()-1));
		}
	
		return  productTypes;
	}

	/**
	 * 根据客户ID和产品类型获得的订单信息
	 * @param customerId
	 * @param productType
	 * @return
	 */
	@RequestMapping(value = "/order_get_by_productType_and_customerId.do", method = RequestMethod.POST)
	public @ResponseBody
	List<Order> getProductByCustomerAndProductType(@RequestParam String customerId,@RequestParam String productType) {
		List<Ticket> ticketList = ticketService.getTicketsByCustomerId(customerId);
		String ticketIdString = new String();
		if (ticketList.size() > 0) {
			for (Ticket ticket : ticketList) {
				ticketIdString += "'" + ticket.getTicketId() + "',";
			}
		}
		List<Order> orderlist=new ArrayList<Order>() ;
		if (!ticketIdString.equals("")) {
			orderlist=orderService.getOrderByProductType(ticketIdString.substring(0, ticketIdString.length()-1), productType);
		}
		return orderlist;
	}

}	
