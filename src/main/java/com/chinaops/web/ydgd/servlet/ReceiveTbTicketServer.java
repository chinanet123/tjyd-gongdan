package com.chinaops.web.ydgd.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import com.chinaops.web.ydgd.entity.Customer;
import com.chinaops.web.ydgd.entity.Order;
import com.chinaops.web.ydgd.entity.Ticket;
import com.chinaops.web.ydgd.entity.TicketTypeEnum;
import com.chinaops.web.ydgd.parser.ReceiveTbTicketParser;
import com.chinaops.web.ydgd.service.CustomerService;
import com.chinaops.web.ydgd.service.LogService;
import com.chinaops.web.ydgd.service.OrderService;
import com.chinaops.web.ydgd.service.TicketService;
import com.chinaops.web.ydgd.utils.EmailUtil;
/**
 * 
 * @author maningtao 
 */
public class ReceiveTbTicketServer extends HttpServlet {
	private static final Log log = LogFactory.getLog(ReceiveTbTicketServer.class);
    ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
    TicketService ticketService = (TicketService) context.getBean("ticketService") ;
    CustomerService customerService = (CustomerService) context.getBean("customerService") ;
    OrderService orderService = (OrderService) context.getBean("orderService") ;
    LogService logService = (LogService) context.getBean("logService");
    
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	String xml = request.getParameter("xml");
    	log.debug("request:xml="+ xml);
    	ReceiveTbTicketParser receiveTbTicketParser = new ReceiveTbTicketParser();
    	Ticket tickets = new Ticket();
    	try {
    		tickets = receiveTbTicketParser.parse(xml);
    	} catch (SAXException e) {
    		e.printStackTrace();
    	}
    	
    	Ticket ticket = new Ticket();
    	if(tickets.getReceiveTime() != null){
    		ticket.setReceiveTime(tickets.getReceiveTime());
    	}
    	if(tickets.getTicketId() != null){
    		ticket.setTicketId(tickets.getTicketId());
    	}
    	if(tickets.getTicketType() != null){
    		if(tickets.getTicketType().equals("开通工单")){
    			ticket.setTicketType(TicketTypeEnum.OPEN.toString());
    		}
    		if(tickets.getTicketType().equals("变更工单")){
    			ticket.setTicketType(TicketTypeEnum.MODIFY.toString());
    		}
    		if(tickets.getTicketType().equals("业务撤销工单")){
    			ticket.setTicketType(TicketTypeEnum.CLOSE.toString());
    		}
    	}
    	if(tickets.getCustomerId() != null){
    		ticket.setCustomerId(tickets.getCustomerId());
    	}
    	
    	
    	Customer customer = new Customer();
    	if(tickets.getCustomerId() != null){
    		customer.setCustomerId(tickets.getCustomerId());
    	}
    	if(tickets.getCustomerName() != null){
    		customer.setCustomerName(tickets.getCustomerName());
    	}
    	if(tickets.getCustomerAddress() != null){
    		customer.setCustomerAddress(tickets.getCustomerAddress());
    	}
        if(tickets.getPostalCode() != null){
    		customer.setPostalCode(tickets.getPostalCode());
    	}
        if(tickets.getCategory() != null){
    		customer.setCategory(tickets.getCategory());
    	}
        
        if(tickets.getBusinessContact().getName() != null){
        	customer.setB_contact(tickets.getBusinessContact().getName());
    	}
        if(tickets.getBusinessContact().getPhone() != null){
        	customer.setB_phone(tickets.getBusinessContact().getPhone());
        }
        if(tickets.getBusinessContact().getEmail() != null){
        	customer.setB_email(tickets.getBusinessContact().getEmail());
        }
        
        if(tickets.getTechnicalContact().getName() != null){
        	customer.setT_contact(tickets.getTechnicalContact().getName());
        }
        if(tickets.getTechnicalContact().getPhone() != null){
        	customer.setT_phone(tickets.getTechnicalContact().getPhone());
        }
        if(tickets.getTechnicalContact().getEmail() != null){
        	customer.setT_email(tickets.getTechnicalContact().getEmail());
        }
        
        if(tickets.getCustomerManager().getName() != null){
        	customer.setC_contact(tickets.getCustomerManager().getName());
        }
        if(tickets.getCustomerManager().getPhone() != null){
        	customer.setC_phone(tickets.getCustomerManager().getPhone());
        }
        if(tickets.getCustomerManager().getEmail() != null){
        	customer.setC_email(tickets.getCustomerManager().getEmail());
        }
        
        
        Order order = orderService.getOrderByTicketId(tickets.getTicketId());
        if(tickets.getOrder().getIsFeesOwed() != null){
        	order.setIsFeesOwed(tickets.getOrder().getIsFeesOwed());
        }
        if(tickets.getOrder().getCloseTime() != null){
        	order.setCloseTime(tickets.getOrder().getCloseTime());
        }
        if(tickets.getOrder().getReason() != null){
        	order.setReason(tickets.getOrder().getReason());
        }
        if(tickets.getOrder().getDetailedReason() != null){
        	order.setDetailedReason(tickets.getOrder().getDetailedReason());
        }
        if(tickets.getOrder().getMemo() != null){
        	order.setMemo(tickets.getOrder().getMemo());
        }
        
        Ticket ticket2 = ticketService.getTicketByTicketId(ticket.getTicketId());
    	if(ticket.getTicketId().equals(ticket2.getTicketId())){
    		ticketService.modifyTicketSetIsUser(ticket);
    	}
    	ticketService.addTicket(ticket);
        	
    	Customer customer2 = customerService.getCustomerBycustomerId(tickets.getCustomerId());
        if(tickets.getCustomerId().equals(customer2.getCustomerId())){
        	customerService.modifyCustomerSetIsUse(customer);
        }
        customerService.addCustomer(customer);
       
        orderService.closeTicket(order);
        
        logService.writeRequestLog(xml);
    	//处理完成  返回
        String xmlContent="";
        Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element rootElement = document.addElement("ticketResponse");
		rootElement.addElement("code").addText("0");
		rootElement.addElement("message").addText("ticket received");
		xmlContent = document.asXML();
		response.getWriter().write(xmlContent);
		response.getWriter().flush();
		response.getWriter().close();
		
		
		String title = tickets.getTicketType();
		EmailUtil.sendEmail(title,ticket.getTicketId(),customer.getCustomerName(),ticket.getReceiveTime());
		
        /*HttpRequester requester = new HttpRequester();
		Map<String,String> map = new HashMap<String,String>();
		String ticketResponseXML = "<ticketResponse><code>0</code><message>ticket received</message></ticketResponse>";
		map.put("xml",ticketResponseXML);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
		Configuration configuration = (Configuration) applicationContext.getBean("ticketResponseConfiguration");
		String ticketResponseURL = configuration.getString("ticketResponse_url");
		HttpRespons hr = requester.sendPost(ticketResponseURL,map);*/
        
    }
    
    // 不提供get的处理方式
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
    }
}