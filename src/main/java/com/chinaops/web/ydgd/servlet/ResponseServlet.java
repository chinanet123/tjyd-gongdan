/**
 * 
 */
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

/**
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年9月16日 下午7:43:02
 */
public class ResponseServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(ResponseServlet.class);
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String xml = request.getParameter("xml");
		log.debug("request:xml="+ xml);
		String xmlContent="";
        Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element rootElement = document.addElement("ticketResponse");
		rootElement.addElement("code").addText("200");
		rootElement.addElement("message").addText("ticket received");
		xmlContent = document.asXML();
		response.getWriter().write(xmlContent);
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	
}
