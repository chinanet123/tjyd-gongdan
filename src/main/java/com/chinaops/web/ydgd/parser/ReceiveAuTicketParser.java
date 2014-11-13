package com.chinaops.web.ydgd.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.chinaops.web.ydgd.entity.BusinessContact;
import com.chinaops.web.ydgd.entity.CustomerManager;
import com.chinaops.web.ydgd.entity.Order;
import com.chinaops.web.ydgd.entity.Storage;
import com.chinaops.web.ydgd.entity.Suite;
import com.chinaops.web.ydgd.entity.TechnicalContact;
import com.chinaops.web.ydgd.entity.Ticket;

/**
 * 
 * @author maningtao 
 */
public class ReceiveAuTicketParser {
	public Ticket parse(String xml)throws IOException, SAXException {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("ticket", Ticket.class);
        digester.addBeanPropertySetter("ticket/receiveTime");
        digester.addBeanPropertySetter("ticket/ticketId");
        digester.addBeanPropertySetter("ticket/ticketType");
        digester.addBeanPropertySetter("ticket/ticketState");
        
        digester.addBeanPropertySetter("ticket/customerId");
        digester.addBeanPropertySetter("ticket/customerName");
        digester.addBeanPropertySetter("ticket/customerAddress");
        digester.addBeanPropertySetter("ticket/postalCode");
        digester.addBeanPropertySetter("ticket/category");
        
        
        digester.addObjectCreate("ticket/businessContact", BusinessContact.class);
        digester.addBeanPropertySetter("ticket/businessContact/name");
        digester.addBeanPropertySetter("ticket/businessContact/phone");
        digester.addBeanPropertySetter("ticket/businessContact/email");
        digester.addSetNext("ticket/businessContact","setBusinessContact");
        
        digester.addObjectCreate("ticket/technicalContact", TechnicalContact.class);
        digester.addBeanPropertySetter("ticket/technicalContact/name");
        digester.addBeanPropertySetter("ticket/technicalContact/phone");
        digester.addBeanPropertySetter("ticket/technicalContact/email");
        digester.addSetNext("ticket/technicalContact","setTechnicalContact");
        
        digester.addObjectCreate("ticket/customerManager", CustomerManager.class);
        digester.addBeanPropertySetter("ticket/customerManager/name");
        digester.addBeanPropertySetter("ticket/customerManager/phone");
        digester.addBeanPropertySetter("ticket/customerManager/email");
        digester.addSetNext("ticket/customerManager","setCustomerManager");
        
        digester.addObjectCreate("ticket/orderInfo", Order.class);
        digester.addBeanPropertySetter("ticket/orderInfo/contractId");
        digester.addBeanPropertySetter("ticket/orderInfo/contractSignedDate");
        digester.addBeanPropertySetter("ticket/orderInfo/contractExpiredDate");
        digester.addBeanPropertySetter("ticket/orderInfo/availableAt");
        digester.addBeanPropertySetter("ticket/orderInfo/productType");
        digester.addBeanPropertySetter("ticket/orderInfo/forWebsite");
        digester.addBeanPropertySetter("ticket/orderInfo/portsNeedToOpen");
        
        digester.addObjectCreate("ticket/orderInfo/suites",ArrayList.class);
        digester.addObjectCreate("ticket/orderInfo/suites/suite", Suite.class);
        digester.addBeanPropertySetter("ticket/orderInfo/suites/suite/name");
        digester.addBeanPropertySetter("ticket/orderInfo/suites/suite/price");
        digester.addBeanPropertySetter("ticket/orderInfo/suites/suite/count");
        digester.addBeanPropertySetter("ticket/orderInfo/suites/suite/discount");
        digester.addBeanPropertySetter("ticket/orderInfo/suites/suite/desc");
        digester.addSetNext("ticket/orderInfo/suites/suite", "add");
        digester.addSetNext("ticket/orderInfo/suites", "setSuites", ArrayList.class.getCanonicalName());
        
        
        digester.addBeanPropertySetter("ticket/orderInfo/bandwidth");
        digester.addBeanPropertySetter("ticket/orderInfo/ipCount");
        
        digester.addObjectCreate("ticket/orderInfo/storage", Storage.class);
        digester.addBeanPropertySetter("ticket/orderInfo/storage/sizeInGB");
        digester.addBeanPropertySetter("ticket/orderInfo/storage/discount");
        digester.addSetNext("ticket/orderInfo/storage","setStorage");
        
        digester.addBeanPropertySetter("ticket/orderInfo/snapshot");
        digester.addBeanPropertySetter("ticket/orderInfo/ha");
        digester.addBeanPropertySetter("ticket/orderInfo/memo");
        digester.addBeanPropertySetter("ticket/orderInfo/filingNo");
        digester.addBeanPropertySetter("ticket/orderInfo/filingIp");
        digester.addBeanPropertySetter("ticket/orderInfo/filingDomain");
        digester.addSetNext("ticket/orderInfo","setOrder");
        
        Ticket  ticket=(Ticket) digester.parse(new StringReader(xml));
		return ticket;
	}
}