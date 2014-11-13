/**
 * 
 */
package com.chinaops.web.ydgd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaops.web.ydgd.dao.Impl.SuiteDaoImpl;
import com.chinaops.web.ydgd.entity.Suite;

/**
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年9月11日 上午11:02:49
 */
public class SuiteService {
	private SuiteDaoImpl suiteDaoImpl;

	@Autowired
	public void setSuiteDaoImpl(SuiteDaoImpl suiteDaoImpl) {
		this.suiteDaoImpl = suiteDaoImpl;
	}
	
	public List<Suite> getSuiteListByTicketId(String ticketId,int orderId){
		return this.suiteDaoImpl.getSuiteListByTicketId(ticketId, orderId);
	}
	
	public void addSuites(Suite suite){
		this.suiteDaoImpl.addSuites(suite);
	}
	
	public void modifySuitesState(Suite suite){
		this.suiteDaoImpl.modifySuitesState(suite);
	}
}
