/**
 * 
 */
package com.chinaops.web.ydgd.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaops.web.ydgd.dao.Impl.LogDaoImpl;

/**
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年9月16日 下午7:30:23
 */
public class LogService {
	private LogDaoImpl logDaoImpl;

	@Autowired
	public void setLogDaoImpl(LogDaoImpl logDaoImpl) {
		this.logDaoImpl = logDaoImpl;
	}
	
	public void writeRequestLog(String xml){
		this.logDaoImpl.writeRequestLog(xml);
	}
	
	public void writeResposeLog(String xml){
		this.logDaoImpl.writeResposeLog(xml);
	}
}
