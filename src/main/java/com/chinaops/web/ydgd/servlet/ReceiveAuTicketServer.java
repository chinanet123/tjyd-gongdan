package com.chinaops.web.ydgd.servlet;

import java.io.IOException;
import java.util.List;

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
import com.chinaops.web.ydgd.entity.ForWebSiteEnum;
import com.chinaops.web.ydgd.entity.Order;
import com.chinaops.web.ydgd.entity.ProductTypeEnum;
import com.chinaops.web.ydgd.entity.Storage;
import com.chinaops.web.ydgd.entity.Suite;
import com.chinaops.web.ydgd.entity.Ticket;
import com.chinaops.web.ydgd.entity.TicketTypeEnum;
import com.chinaops.web.ydgd.parser.ReceiveAuTicketParser;
import com.chinaops.web.ydgd.service.CustomerService;
import com.chinaops.web.ydgd.service.LogService;
import com.chinaops.web.ydgd.service.OrderService;
import com.chinaops.web.ydgd.service.SuiteService;
import com.chinaops.web.ydgd.service.TicketService;
import com.chinaops.web.ydgd.utils.EmailUtil;
/**
 * 
 * @author maningtao 
 */
public class ReceiveAuTicketServer extends HttpServlet {
	private static final Log log = LogFactory.getLog(ReceiveAuTicketServer.class);
    ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
    TicketService ticketService = (TicketService) context.getBean("ticketService") ;
    CustomerService customerService = (CustomerService) context.getBean("customerService") ;
    OrderService orderService = (OrderService) context.getBean("orderService") ;
    SuiteService suiteService = (SuiteService) context.getBean("suiteService");
    LogService logService = (LogService) context.getBean("logService");
    
	private static final long serialVersionUID = 1L;

    /*
     * 代码逻辑分以下三部分:
     * 1.获得请求报文
     * 2.根据请求报文的信息去做业务逻辑，然后封装返回报文
     * 3.输出相应报文
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	String xml = request.getParameter("xml");
    	log.debug("request:xml="+ xml);
    	ReceiveAuTicketParser receiveAuTicketParser = new ReceiveAuTicketParser();
    	Ticket tickets = new Ticket();
    	try {
    		tickets = receiveAuTicketParser.parse(xml);
    		
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
    		if(tickets.getTicketType().equals("撤销工单")){
    			ticket.setTicketType(TicketTypeEnum.CLOSE.toString());
    		}
    	}
    	if(tickets.getTicketState() != null){
    		ticket.setTicketState(tickets.getTicketState());
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
        
        
        Order order = new Order();
        if(tickets.getOrder().getContractId() !=null){
        	order.setContractId(tickets.getOrder().getContractId());
        }
        if(tickets.getOrder().getContractSignedDate() !=null){
        	order.setContractSignedDate(tickets.getOrder().getContractSignedDate());
        }
        if(tickets.getOrder().getContractExpiredDate() != null){
        	order.setContractExpiredDate(tickets.getOrder().getContractExpiredDate());
        }
        if(tickets.getOrder().getAvailableAt() != null){
        	order.setAvailableAt(tickets.getOrder().getAvailableAt());
        }
        if(tickets.getOrder().getProductType() != null){
        	if(tickets.getOrder().getProductType().equals("专享云")){
        		order.setProductType(ProductTypeEnum.EXCLUSIVE.toString());
        	}
        	if(tickets.getOrder().getProductType().equals("共享云")){
        		order.setProductType(ProductTypeEnum.SHARE.toString());
        	}
        	if(tickets.getOrder().getProductType().equals("云存储")){
        		order.setProductType(ProductTypeEnum.STORAGE.toString());
        	} 	
        }
        if(tickets.getOrder().getForWebsite() != null){
        	if(tickets.getOrder().getForWebsite().equals("yes")){
        		order.setForWebsite(ForWebSiteEnum.YES.toString());
        	}
        	if(tickets.getOrder().getForWebsite().equals("no")){
        		order.setForWebsite(ForWebSiteEnum.NO.toString());
        	}
        }
        if(tickets.getOrder().getPortsNeedToOpen() != null){
        	order.setPortsNeedToOpen(tickets.getOrder().getPortsNeedToOpen());
        }
        
        if(tickets.getOrder().getSuites() != null && tickets.getOrder().getSuites().size()>0){
        	order.setSuites(tickets.getOrder().getSuites());
        }
        
        if(tickets.getOrder().getBandwidth() != null && !tickets.getOrder().getBandwidth().equals("")){
        	order.setBandwidth(tickets.getOrder().getBandwidth());
        }else{
        	order.setBandwidth("0");
        }
        if(tickets.getOrder().getIpCount() != null && !tickets.getOrder().getIpCount().equals("")){
        	order.setIpCount(tickets.getOrder().getIpCount());
        }else{
        	order.setIpCount("0");
        }
        
        if(tickets.getOrder().getStorage() != null && !tickets.getOrder().getStorage().equals("")){
        	Storage storage = new Storage();
        	if(tickets.getOrder().getStorage().getSizeInGB() != null && !tickets.getOrder().getStorage().getSizeInGB().equals("")){
        		storage.setSizeInGB(tickets.getOrder().getStorage().getSizeInGB());
        	}else {
				storage.setSizeInGB("0");
			}
        	if(tickets.getOrder().getStorage().getDiscount() != null && !tickets.getOrder().getStorage().getDiscount().equals("")){
        		storage.setDiscount(tickets.getOrder().getStorage().getDiscount());
        	}else {
        		storage.setDiscount("0");
        	}
        	order.setStorage(storage);
        }
        
        if(tickets.getOrder().getSnapshot() != null && !tickets.getOrder().getSnapshot().equals("")){
        	order.setSnapshot(tickets.getOrder().getSnapshot());
        }else {
			order.setSnapshot("0");
		}
        if(tickets.getOrder().getHa() != null && !tickets.getOrder().getHa().equals("")){
        	order.setHa(tickets.getOrder().getHa());
        }else{
        	order.setHa("0");
        }
        if(tickets.getOrder().getMemo() != null){
        	order.setMemo(tickets.getOrder().getMemo());
        }
        if(tickets.getOrder().getFilingNo() != null){
        	order.setFilingNo(tickets.getOrder().getFilingNo());
        }
        if(tickets.getOrder().getFilingIp() != null){
        	order.setFilingIp(tickets.getOrder().getFilingIp());
        }
        if(tickets.getOrder().getFilingDomain() != null){
        	order.setFilingDomain(tickets.getOrder().getFilingDomain());
        }
        if(tickets.getTicketId() != null){
        	order.setTicketId(tickets.getTicketId());
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
       
        Order order2 = orderService.getOrderByTicketId(tickets.getTicketId());
        Order order3 = new Order();
        if(order2 != null && ticket.getTicketId().equals(order2.getTicketId())){
        	order3 = orderService.modifyOrder(order);
        }else{
        	order3 = orderService.addOrder(order);
        }
        
        List<Suite> list = suiteService.getSuiteListByTicketId(order3.getTicketId(),order3.getId());
        if(list != null && list.size()>0){
        	for(int i=0;i<list.size();i++){
        		Suite suite = list.get(i);
        		suiteService.modifySuitesState(suite);
        	}
        }
    	if(order.getSuites() != null && order.getSuites().size()>0 ){
    		for(int i=0;i<order.getSuites().size();i++){
    			Suite suite = order.getSuites().get(i);
    			suite.setTicketId(order3.getTicketId());
    			suite.setOrderId(order3.getId()+"");
    			suiteService.addSuites(suite);
    		}
    	}
		
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
		
       /* HttpRequester requester = new HttpRequester();
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