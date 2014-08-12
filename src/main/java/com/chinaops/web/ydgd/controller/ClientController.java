package com.chinaops.web.ydgd.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author liqiang.zhang@china-ops.com
 * 2014年8月11日
 *
 */
@Controller
public class ClientController {
	
	private Log                        logger = LogFactory.getLog(this.getClass());
	
	@RequestMapping("/client.htm")
    public String loginForm() {
        return "client/client";
    }
	
	
}
