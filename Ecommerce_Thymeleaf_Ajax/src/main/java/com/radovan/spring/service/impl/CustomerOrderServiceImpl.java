package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.CustomerOrderDto;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.CustomerOrderEntity;
import com.radovan.spring.entity.OrderAddressEntity;
import com.radovan.spring.entity.OrderItemEntity;
import com.radovan.spring.entity.ProductEntity;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.InsufficientStockException;
import com.radovan.spring.repository.CartItemRepository;
import com.radovan.spring.repository.CustomerOrderRepository;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.repository.OrderAddressRepository;
import com.radovan.spring.repository.OrderItemRepository;
import com.radovan.spring.repository.ProductRepository;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerOrderService;

@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {

	@Autowired
	private CustomerOrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TempConverter tempConverter;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderAddressRepository orderAddressRepository;

	@Override
	public CustomerOrderDto addCustomerOrder() {
		// TODO Auto-generated method stub
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity customerEntity = customerRepository.findByUserId(authUser.getId());
		ShippingAddressEntity shippingAddress = customerEntity.getShippingAddress();
		CartEntity cartEntity = customerEntity.getCart();
		CustomerOrderEntity orderEntity = new CustomerOrderEntity();
		List<OrderItemEntity> orderedItems = new ArrayList<OrderItemEntity>();

		Optional<List<CartItemEntity>> cartItems = Optional.ofNullable(cartEntity.getCartItems());
		if (!cartItems.isEmpty()) {
			for (CartItemEntity item : cartItems.get()) {
				Optional<ProductEntity> productEntity = Optional.ofNullable(item.getProduct());
				if (productEntity.isPresent()) {
					if (productEntity.get().getUnitStock() < item.getQuantity()) {
						Error error = new Error("Not enough stock");
						throw new InsufficientStockException(error);
					} else {
						ProductEntity tempProduct = productEntity.get();
						Integer newStock = tempProduct.getUnitStock() - item.getQuantity();
						tempProduct.setUnitStock(newStock);
						productRepository.saveAndFlush(tempProduct);
					}
				}
			}
		}

		if (!cartItems.isEmpty()) {
			for (CartItemEntity item : cartItems.get()) {
				OrderItemEntity orderedItem = tempConverter.cartItemToOrderItemEntity(item);
				OrderItemEntity storedOrderedItem = orderItemRepository.save(orderedItem);
				orderedItems.add(storedOrderedItem);
			}
		}

		OrderAddressEntity orderAddress = tempConverter.shippingAddressToOrderAddress(shippingAddress);
		OrderAddressEntity storedOrderAddress = orderAddressRepository.save(orderAddress);

		orderEntity.setCart(cartEntity);
		orderEntity.setCustomer(customerEntity);
		orderEntity.setOrderedItems(orderedItems);
		orderEntity.setAddress(storedOrderAddress);

		CustomerOrderEntity storedOrder = orderRepository.save(orderEntity);

		storedOrderAddress.setOrder(storedOrder);
		orderAddressRepository.saveAndFlush(storedOrderAddress);

		CustomerOrderDto returnValue = tempConverter.orderEntityToDto(storedOrder);

		for (OrderItemEntity item : storedOrder.getOrderedItems()) {
			item.setOrder(storedOrder);
			orderItemRepository.saveAndFlush(item);
		}

		cartItemRepository.removeAllByCartId(cartEntity.getCartId());
		cartService.refreshCartState(cartEntity.getCartId());
		return returnValue;
	}

	@Override
	public List<CustomerOrderDto> listAll() {
		// TODO Auto-generated method stub
		List<CustomerOrderEntity> allOrders = orderRepository.findAll();
		List<CustomerOrderDto> returnValue = new ArrayList<>();

		for (CustomerOrderEntity order : allOrders) {
			CustomerOrderDto orderDto = tempConverter.orderEntityToDto(order);
			returnValue.add(orderDto);
		}
		return returnValue;
	}

	@Override
	public Double calculateOrderTotal(Integer orderId) {
		// TODO Auto-generated method stub
		Optional<Double> orderTotal = Optional.ofNullable(orderItemRepository.calculateGrandTotal(orderId));
		Double returnValue = 0d;

		if (orderTotal.isPresent()) {
			returnValue = orderTotal.get();
		}

		return returnValue;
	}

	@Override
	public CustomerOrderDto getOrder(Integer orderId) {
		// TODO Auto-generated method stub
		CustomerOrderEntity orderEntity = orderRepository.getById(orderId);
		CustomerOrderDto returnValue = tempConverter.orderEntityToDto(orderEntity);
		return returnValue;
	}

	@Override
	public void deleteOrder(Integer orderId) {
		// TODO Auto-generated method stub
		orderRepository.deleteById(orderId);
		orderRepository.flush();

	}

	@Override
	public List<CustomerOrderDto> listAllByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		List<CustomerOrderDto> returnValue = new ArrayList<CustomerOrderDto>();
		Optional<List<CustomerOrderEntity>> allOrders = 
				Optional.ofNullable(orderRepository.findAllByCustomerId(customerId));
		if(!allOrders.isEmpty()) {
			for(CustomerOrderEntity order : allOrders.get()) {
				CustomerOrderDto orderDto = tempConverter.orderEntityToDto(order);
				returnValue.add(orderDto);
			}
		}
		return returnValue;
	}

	

}
