package com.radovan.spring.dto;

import java.io.Serializable;
import java.util.List;

public class CustomerOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer customerOrderId;

	private Integer cartId;

	private Integer customerId;

	private Integer addressId;

	private List<Integer> orderedItemsIds;

	public Integer getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(Integer customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public CustomerOrderDto initialize() {
		return new CustomerOrderDto();
	}

	public List<Integer> getOrderedItemsIds() {
		return orderedItemsIds;
	}

	public void setOrderedItemsIds(List<Integer> orderedItemsIds) {
		this.orderedItemsIds = orderedItemsIds;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

}
