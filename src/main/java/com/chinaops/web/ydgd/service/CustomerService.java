package com.chinaops.web.ydgd.service;

/**
 * China-Ops Inc. All Rights Reserved.
 * Author:liuyajuan
 * 2014/8/12
 */
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaops.web.common.entity.Page;
import com.chinaops.web.ydgd.dao.Impl.CustomerDaoImpl;
import com.chinaops.web.ydgd.entity.Customer;

/**
 * Description:客户
 */
public class CustomerService {

	
	private CustomerDaoImpl customerDaoImpl;

	@Autowired
	public void setCustomerDaoImpl(CustomerDaoImpl customerDaoImpl) {
		this.customerDaoImpl = customerDaoImpl;
	}

	public Page getCustomerPages(int pageNo, int pageSize, String fuzzySearchValue) {
		return this.customerDaoImpl.getCustomerPages(pageNo, pageSize, fuzzySearchValue);
	}

	public Customer getCustomerBycustomerId(String customerId) {
		return this.customerDaoImpl.getCustomerBycustomerId(customerId);

	}

	public void addCustomer(Customer customer) {
		this.customerDaoImpl.addCustomer(customer);
	}

	public void modifyCustomer(Customer customer) {
		this.customerDaoImpl.modifyCustomer(customer);
	}

	public void modifyCustomerSetIsUse(Customer customer) {
		this.customerDaoImpl.modifyCustomerSetIsUse(customer);
	}
}
