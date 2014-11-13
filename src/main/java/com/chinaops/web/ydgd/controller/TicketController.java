package com.chinaops.web.ydgd.controller;

/**
 * China-Ops Inc. All Rights Reserved.
 * Author:liuyajuan
 * 2014/8/12
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaops.web.common.entity.Page;
import com.chinaops.web.ydgd.bean.ProductFromBean;
import com.chinaops.web.ydgd.entity.Order;
import com.chinaops.web.ydgd.entity.Suite;
import com.chinaops.web.ydgd.entity.Ticket;
import com.chinaops.web.ydgd.service.LogService;
import com.chinaops.web.ydgd.service.OrderService;
import com.chinaops.web.ydgd.service.SuiteService;
import com.chinaops.web.ydgd.service.TicketService;
import com.chinaops.web.ydgd.servlet.HttpRequest;
import com.chinaops.web.ydgd.servlet.HttpRequester;
import com.chinaops.web.ydgd.servlet.HttpRespons;
import com.chinaops.web.ydgd.utils.StringHelper;

/**
 * Description: ticket
 */
@Controller
public class TicketController {
	private static final Log log = LogFactory.getLog(TicketController.class);
	
	private TicketService ticketService;
	
	private OrderService orderService;
	
	private SuiteService suiteService;
	
	private LogService logService;
	
	@Autowired
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Autowired
	public void setSuiteService(SuiteService suiteService) {
		this.suiteService = suiteService;
	}
	
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/**
	 * 跳转到工单列表页
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/ticket.htm")
	public String clientPageShow(HttpServletRequest request, HttpServletResponse response) {
		return "ticket/ticket";
	}

	/**
	 * 根据customerId查询指定客户的工单（根据工单类型条件过滤）
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param stringPageNum
	 * @param stringPageSize
	 * @param ticketType
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/ticket_get_list_by_customer_id.do", method = RequestMethod.POST)
	public @ResponseBody
	Page selectTicketByName(@RequestParam String stringPageNum, @RequestParam String stringPageSize, @RequestParam String ticketType, @RequestParam String customerId) {
		int pageNo = Integer.parseInt(stringPageNum);
		int pageSize = Integer.parseInt(stringPageSize);
		if (pageNo <= 1) {
			pageNo = 1;
		}
		ticketType = StringHelper.decode(ticketType);
		Page page = ticketService.getTicketPageByCustomerId(pageNo, pageSize, ticketType, customerId);
		return page;
	}

	/**
	 *  查询所有工单（根据工单类型或customerName条件过滤）
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param stringPageNum
	 * @param stringPageSize
	 * @param ticketType
	 * @param searchValue
	 * @return
	 */
	@RequestMapping(value = "/ticket_get_ticket_List.do", method = RequestMethod.POST)
	public @ResponseBody
	Page getTicketPage(@RequestParam String stringPageNum, @RequestParam String stringPageSize, @RequestParam String ticketType, @RequestParam String searchValue) {
		int pageNo = Integer.parseInt(stringPageNum);
		int pageSize = Integer.parseInt(stringPageSize);
		if (pageNo <= 1) {
			pageNo = 1;
		}
		ticketType = StringHelper.decode(ticketType);
		searchValue = StringHelper.decode(searchValue);
		Page page = ticketService.getTicketPage(pageNo, pageSize, ticketType, searchValue);
		return page;
	}
	/**
	 * 未处理工单查询（根据工单类型或customerName条件过滤）
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param stringPageNum
	 * @param stringPageSize
	 * @param ticketType
	 * @param searchValue
	 * @return
	 */
	@RequestMapping(value = "/ticket_get_ticket_List_is_modify.do", method = RequestMethod.POST)
	public @ResponseBody
	Page getTicketPageIsModify(@RequestParam String stringPageNum, @RequestParam String stringPageSize, @RequestParam String ticketType, @RequestParam String searchValue) {
		int pageNo = Integer.parseInt(stringPageNum);
		int pageSize = Integer.parseInt(stringPageSize);
		if (pageNo <= 1) {
			pageNo = 1;
		}
		ticketType = StringHelper.decode(ticketType);
		searchValue = StringHelper.decode(searchValue);
		Page page = ticketService.getTicketPageIsModify(pageNo, pageSize, ticketType, searchValue);
		return page;
	}

	/**
	 * 跳转到查看工单页
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @return
	 */
	@RequestMapping("/ticketView.htm")
	public String view_get_ticket_by_customer_id_and_ticket_id(HttpServletRequest request, HttpServletResponse response){
		return "ticket/ticketView";
	}
	
	/**
	 * 根据工单ID获得工单的详细信息
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @param ticketId
	 * @return
	 */
	@RequestMapping(value = "/ticket_get_ticket_detail.json", method = RequestMethod.POST)
	public @ResponseBody Ticket getTicketDetail(@RequestParam String ticketId,HttpServletRequest request, HttpServletResponse response){
		Ticket ticket = ticketService.getTicketByTicketId(ticketId);
		Order order = orderService.getOrderByTicketId(ticketId);
		List<Suite> suites = suiteService.getSuiteListByTicketId(order.getTicketId(), order.getId());
		order.setSuites(suites);
		ticket.setOrder(order);
		return ticket;
	}
	/**
	 * 跳转到编辑工单页
	 * @author 张立强
	 * @email  liqiang.zhang@china-ops.com
	 * @return
	 */
	@RequestMapping("/ticketEdit.htm")
	public String edit_get_ticket_by_customer_id_and_ticket_id(HttpServletRequest request, HttpServletResponse response){
		return "ticket/ticketEdit";
	}
	
	@RequestMapping(value = "/ticket_ticketType_open.json", method = RequestMethod.POST)
	public @ResponseBody String edit_submit_ticketType_open(@RequestBody ProductFromBean product,HttpServletRequest request, HttpServletResponse response){
		boolean ret = orderService.editOrderByOpenTicketType(product);
		if(ret){
			boolean flag = ticketService.editTicketForTicketTypeOpen(product);
			if(flag){
				String xmlContent="";
		        Document document = DocumentHelper.createDocument();
				document.setXMLEncoding("UTF-8");
				Element rootElement = document.addElement("processResult");
				rootElement.addElement("ticketId").addText(product.getTicketId());
				rootElement.addElement("customerId").addText(product.getCustomerId());
				rootElement.addElement("ticketState").addText(product.getTicketState());
				rootElement.addElement("loginUrl").addText(product.getLoginUrl());
				rootElement.addElement("cloudPlatform").addText(product.getCloudPlatform());
				rootElement.addElement("openedPorts").addText(product.getOpenPorts());
				rootElement.addElement("ip").addText(product.getIp());
				rootElement.addElement("username").addText(product.getLoginUsername());
				rootElement.addElement("password").addText(product.getLoginPassword());
				rootElement.addElement("keys").addText(product.getKeypairs());
				rootElement.addElement("secureGroups").addText(product.getSecuritys());
				rootElement.addElement("memo").addText(product.getRemark());
				xmlContent = document.asXML();
				HttpRequester requester = new HttpRequester();
				Map<String,String> map = new HashMap<String,String>();
				map.put("xml",xmlContent);
				ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
				Configuration configuration = (Configuration) applicationContext.getBean("ticketResponseConfiguration");
				String url = configuration.getString("ticketRespose_open_url");

//				try {
					log.debug("sendPost:url="+url+"\nxml="+xmlContent);
					logService.writeResposeLog(xmlContent);
					String string = HttpRequest.sendPost(url, "xml="+xmlContent);
					log.debug("===================="+string+" ====================");
//					HttpRespons hr = requester.sendGet(url,map);
//					if(hr.getCode() == 200){
//						log.debug("Success");
//					}
////					response.getWriter().write(xmlContent);
////					response.getWriter().flush();
////					response.getWriter().close();	
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				
				return "1";
			}
		}
		return "0";
	}
	
	@RequestMapping(value = "/ticket_ticketType_close.json", method = RequestMethod.POST)
	public @ResponseBody String edit_submit_ticketType_close(@RequestBody ProductFromBean product){
//		boolean ret = orderService.editOrderByOpenTicketType(productFromBean);
//		if(ret){
			boolean flag = ticketService.editTicketForTicketTypeClose(product);
			if(flag){
				String xmlContent="";
		        Document document = DocumentHelper.createDocument();
				document.setXMLEncoding("UTF-8");
				Element rootElement = document.addElement("ticket");
				rootElement.addElement("ticketId").addText(product.getTicketId());
				rootElement.addElement("customerId").addText(product.getCustomerId());
				rootElement.addElement("processResult").addText("资源已销毁");
				rootElement.addElement("memo").addText(product.getRemark());
				HttpRequester requester = new HttpRequester();
				Map<String,String> map = new HashMap<String,String>();
				map.put("xml",xmlContent);
				ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
				Configuration configuration = (Configuration) applicationContext.getBean("ticketResponseConfiguration");
				String url = configuration.getString("ticketRespose_close_url");

//				try {
					log.debug("sendPost:url="+url+"\nxml="+xmlContent);
					logService.writeResposeLog(xmlContent);
					String string = HttpRequest.sendPost(url, "xml="+xmlContent);
					log.debug("===================="+string+" ====================");
//					HttpRespons hr = requester.sendGet(url,map);
//					if(hr.getCode() == 200){
//						log.debug("Success");
//					}
////					response.getWriter().write(xmlContent);
////					response.getWriter().flush();
////					response.getWriter().close();	
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				return "1";
			}
//		}
		return "0";
	}
}
