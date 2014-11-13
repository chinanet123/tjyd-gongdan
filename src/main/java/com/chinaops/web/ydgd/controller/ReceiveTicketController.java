package com.chinaops.web.ydgd.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author maningtao
 */
@Controller
public class ReceiveTicketController {
    // ========================== Attributes ============================
	@SuppressWarnings("unused")
	private Log                        logger = LogFactory.getLog(this.getClass());
	
    // ======================== Public methods ==========================
    @RequestMapping("/receiveAuTicket.htm")
    public String receiveAuTicket() {
        return "receiveAuTicket";
    }
    @RequestMapping("/receiveTbTicket.htm")
    public String receiveTbTicket() {
        return "receiveTbTicket";
    }
    
}
