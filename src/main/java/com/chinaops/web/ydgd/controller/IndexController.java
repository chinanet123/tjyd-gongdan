/*
 * $Id$
 * 
 * All Rights Reserved 2012 China OPS Information Technology Co.,Ltd.
 */
package com.chinaops.web.ydgd.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinaops.web.common.entity.EcloudUserDetails;

/**
 * 
 * @author Harley Ren
 */
@Controller
public class IndexController extends BaseController {
	// ========================== Attributes ============================
	

	private static final Log log = LogFactory.getLog(IndexController.class);

	
	// ======================== Public methods ==========================
	@RequestMapping("/index.htm")
	public String index(HttpSession session) {
		// if(!verifySession(session)) {
		// return "redirect:login";
		// }
		// return "/bucket";
		// return "redirect:instances.htm";
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		EcloudUserDetails userDetail = (EcloudUserDetails) principal;
		/*User user = toUser(userDetail);
		List<Privilege> privileges = privilegeManager.getUserPrivileges(user,
				PrivilegeCategory.ElasticInstance.toString());
		Privilege p = null;
		if (privileges != null) {
			p = privileges.get(0); // privileges.get(0)
									// 公司管理员登陆显示的是控制台，最终用户登录是云主机。
			log.debug(p.getId() + "-" + p.getName() + "-" + p.getUrl());
		}
		if (p.getUrl().equals("/dashboard.htm")) { // privileges.get(0)
													// 公司管理员登陆显示的是控制台,所以取第二个privileges.get(1)才是公司管理。
			int companyid;
			Company company = null;
			try {
				companyid = userManager.getUserById(user.getId()).getCompanyId();
				company = companyManager.getCompanyById(companyid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(company.getIsopendept() !=1 ){
				p.setUrl("/instances.htm");
			}else{
				log.debug("privileges.get(1):"+privileges.get(1).getUrl());
				p = privileges.get(1);
			}
		}
		return "redirect:" + p.getUrl() + "";
		*/
        return null;
	}

	
/*	private User toUser(EcloudUserDetails userDetail) {
		User user = new User();
		user.setId(userDetail.getId());
		user.setUsername(userDetail.getUsername());
		user.setCompanyId(userDetail.getCompanyId());
		user.setDepartmentId(userDetail.getDepartmentId());
		user.setLoginId(userDetail.getUsername());
		return user;
	}
	*/
	
	
	// ==================== Private utility methods =====================

	// ========================== main method ===========================
	// ======================= Getters & Setters ========================
}
