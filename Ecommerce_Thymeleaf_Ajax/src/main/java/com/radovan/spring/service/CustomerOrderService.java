package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.CustomerOrderDto;


public interface CustomerOrderService {

	
	
	CustomerOrderDto addCustomerOrder();
	
	List<CustomerOrderDto> listAll();
	
	Double calculateOrderTotal(Integer orderId);
	
	CustomerOrderDto getOrder(Integer orderId);
	
	void deleteOrder(Integer orderId);
	
	List<CustomerOrderDto> listAllByCustomerId(Integer customerId);
	
	
	
}
