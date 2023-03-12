package com.radovan.spring.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name = "customer_orders")
public class CustomerOrderEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer customerOrderId;

	@OneToOne
	@JoinColumn(name = "cart_id")
	private CartEntity cart;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;

	
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<OrderItemEntity> orderedItems;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id")
	private OrderAddressEntity address;
	
	

	public Integer getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(Integer customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	

	public List<OrderItemEntity> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<OrderItemEntity> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public OrderAddressEntity getAddress() {
		return address;
	}

	public void setAddress(OrderAddressEntity address) {
		this.address = address;
	}
	
	

	

	
	
	
	
}
