package com.radovan.spring.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.radovan.spring.dto.BillingAddressDto;
import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.CustomerOrderDto;
import com.radovan.spring.dto.OrderAddressDto;
import com.radovan.spring.dto.OrderItemDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.dto.ReviewMessageDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.exceptions.ImagePathException;
import com.radovan.spring.service.BillingAddressService;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerOrderService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.OrderAddressService;
import com.radovan.spring.service.OrderItemService;
import com.radovan.spring.service.ProductService;
import com.radovan.spring.service.ReviewMessageService;
import com.radovan.spring.service.ShippingAddressService;
import com.radovan.spring.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@Autowired
	private BillingAddressService billingAddressService;

	@Autowired
	private ShippingAddressService shippingAddressService;

	@Autowired
	private ReviewMessageService reviewService;

	@Autowired
	private CustomerOrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderAddressService orderAddressService;

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String adminHome() {
		return "fragments/admin :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/addNewProduct", method = RequestMethod.GET)
	public String renderProductForm(ModelMap map) {

		ProductDto product = new ProductDto();
		map.put("product", product);
		return "fragments/addProduct :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createProduct", method = RequestMethod.POST)
	public String createProduct(@ModelAttribute("product") ProductDto product, ModelMap map,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws Throwable {

		String fileLocation = "C:\\Users\\Radovan\\eclipse-workspace\\Ecommerce_Thymeleaf_Ajax\\src\\main\\resources\\static\\images\\productImages\\";
		String imageUUID;

		Path locationPath = Paths.get(fileLocation);

		if (!Files.exists(locationPath)) {
			Error error = new Error("Invalid file path!");
			throw new ImagePathException(error);
		}

		imageUUID = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(fileLocation, imageUUID);

		if (file != null && !file.isEmpty()) {
			Files.write(fileNameAndPath, file.getBytes());
			System.out.println("IMage Save at:" + fileNameAndPath.toString());
		} else {
			imageUUID = imgName;
		}

		product.setImageName(imageUUID);
		productService.addProduct(product);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteProduct/{productId}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("productId") Integer productId) throws Throwable {

		ProductDto product = productService.getProduct(productId);
		Path path = Paths.get(
				"C:\\Users\\Radovan\\eclipse-workspace\\Ecommerce_Thymeleaf_Ajax\\src\\main\\resources\\static\\images\\productImages\\"
						+ product.getImageName());

		if (Files.exists(path)) {
			Files.delete(path);
		} else {
			Error error = new Error("Invalid file path!");
			throw new ImagePathException(error);
		}

		cartItemService.eraseAllByProductId(productId);
		productService.deleteProduct(productId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/updateProduct/{productId}", method = RequestMethod.GET)
	public String renderUpdateForm(@PathVariable("productId") Integer productId, ModelMap map) {

		ProductDto product = new ProductDto();
		ProductDto currentProduct = productService.getProduct(productId);
		map.put("product", product);
		map.put("currentProduct", currentProduct);
		return "fragments/updateProduct :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allCustomers", method = RequestMethod.GET)
	public String customerList(ModelMap map) {

		List<CustomerDto> allCustomers = customerService.getAllCustomers();
		List<UserDto> allUsers = userService.listAllUsers();
		map.put("allCustomers", allCustomers);
		map.put("allUsers", allUsers);
		map.put("recordsPerPage", 8);
		return "fragments/customerList :: ajaxLoadedContent";

	}

	@RequestMapping(value = "/getCustomer/{customerId}", method = RequestMethod.GET)
	public String getCustomer(@PathVariable("customerId") Integer customerId, ModelMap map) {

		CustomerDto customer = customerService.getCustomer(customerId);
		UserDto tempUser = userService.getUserById(customer.getUserId());
		ShippingAddressDto shippingAddress = shippingAddressService.getShippingAddress(customer.getShippingAddressId());
		BillingAddressDto billingAddress = billingAddressService.getBillingAddress(customer.getBillingAddressId());
		map.put("tempCustomer", customer);
		map.put("tempUser", tempUser);
		map.put("billingAddress", billingAddress);
		map.put("shippingAddress", shippingAddress);
		return "fragments/customerDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteCustomer/{customerId}", method = RequestMethod.GET)
	public String removeCustomer(@PathVariable("customerId") Integer customerId) {
		CustomerDto customer = customerService.getCustomer(customerId);
		CartDto cart = cartService.getCartByCartId(customer.getCartId());
		BillingAddressDto billingAddress = billingAddressService.getBillingAddress(customer.getBillingAddressId());
		ShippingAddressDto shippingAddress = shippingAddressService.getShippingAddress(customer.getShippingAddressId());
		UserDto user = userService.getUserById(customer.getUserId());
		
		List<CustomerOrderDto> allOrders = orderService.listAllByCustomerId(customerId);
		for(CustomerOrderDto order:allOrders) {
			orderItemService.eraseAllByOrderId(order.getCustomerOrderId());
			orderService.deleteOrder(order.getCustomerOrderId());
		}
			
		
		cartItemService.eraseAllCartItems(cart.getCartId());
		reviewService.deleteAllByCustomerId(customerId);
		customerService.resetCustomer(customerId);
		billingAddressService.deleteBillingAddress(billingAddress.getBillingAddressId());
		shippingAddressService.deleteShippingAddress(shippingAddress.getShippingAddressId());
		cartService.deleteCart(cart.getCartId());		
		customerService.deleteCustomer(customerId);
		userService.deleteUser(user.getId());
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allReviews", method = RequestMethod.GET)
	public String listAllReviews(ModelMap map) {

		List<ReviewMessageDto> allReviews = reviewService.allReviews();
		List<CustomerDto> allCustomers = customerService.getAllCustomers();
		List<UserDto> allUsers = userService.listAllUsers();
		map.put("allCustomers", allCustomers);
		map.put("allUsers", allUsers);
		map.put("allReviews", allReviews);
		map.put("recordsPerPage", 5);
		return "fragments/reviewList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/reviewDetails/{reviewId}", method = RequestMethod.GET)
	public String getReview(@PathVariable("reviewId") Integer reviewId, ModelMap map) {
		ReviewMessageDto review = reviewService.getReview(reviewId);
		CustomerDto customer = customerService.getCustomer(review.getCustomerId());
		UserDto user = userService.getUserById(customer.getUserId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = review.getDate().toLocalDateTime();
		String createdAtStr = dateTime.format(formatter);
		map.put("review", review);
		map.put("user", user);
		map.put("createdAtStr", createdAtStr);
		return "fragments/reviewDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteReview/{reviewId}", method = RequestMethod.GET)
	public String deleteReview(@PathVariable("reviewId") Integer reviewId) {

		reviewService.deleteReview(reviewId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/suspendUser/{userId}", method = RequestMethod.POST)
	public String suspendUser(@PathVariable("userId") Integer userId) {
		System.out.println("Suspending user with id: " + userId);
		userService.suspendUser(userId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allOrders", method = RequestMethod.GET)
	public String listAllOrders(ModelMap map) {

		List<CustomerOrderDto> allOrders = orderService.listAll();
		List<CustomerDto> allCustomers = customerService.getAllCustomers();
		List<UserDto> allUsers = userService.listAllUsers();
		map.put("allOrders", allOrders);
		map.put("allCustomers", allCustomers);
		map.put("allUsers", allUsers);
		map.put("recordsPerPage", 10);
		return "fragments/orderList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.GET)
	public String deleteOrder(@PathVariable("orderId") Integer orderId) {

		orderItemService.eraseAllByOrderId(orderId);
		orderService.deleteOrder(orderId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/getOrder/{orderId}", method = RequestMethod.GET)
	public String orderDetails(@PathVariable("orderId") Integer orderId, ModelMap map) {

		CustomerOrderDto order = orderService.getOrder(orderId);
		OrderAddressDto address = orderAddressService.getAddressById(order.getAddressId());
		List<ProductDto> allProducts = productService.listAll();
		Double orderPrice = orderService.calculateOrderTotal(orderId);
		List<OrderItemDto> orderedItems = orderItemService.listAllByOrderId(orderId);
		map.put("order", order);
		map.put("address", address);
		map.put("allProducts", allProducts);
		map.put("orderPrice", orderPrice);
		map.put("orderedItems", orderedItems);
		return "fragments/orderDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/invalidPath", method = RequestMethod.GET)
	public String invalidImagePath() {
		return "fragments/invalidImagePath :: ajaxLoadedContent";
	}

}
