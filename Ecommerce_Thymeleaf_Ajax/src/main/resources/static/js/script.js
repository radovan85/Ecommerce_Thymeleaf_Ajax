function redirectLogin() {
	$("#ajaxLoadedContent").load("/login");
}

function redirectRegister() {
	$("#ajaxLoadedContent").load("/userRegistration");
}

function redirectHome() {
	$("#ajaxLoadedContent").load("/home");
}

function redirectAdmin() {
	$("#ajaxLoadedContent").load("/admin/");
}

function redirectProductList() {
	$("#ajaxLoadedContent").load("/products/allProducts");
}

function redirectNewProduct() {
	$("#ajaxLoadedContent").load("/admin/addNewProduct");
}

function redirectAbout() {
	$("#ajaxLoadedContent").load("/aboutUs");
}

function redirectAllCustomers() {
	$("#ajaxLoadedContent").load("/admin/allCustomers");
}

function redirectCart() {
	$("#ajaxLoadedContent").load("/cart/viewCart");
}

function redirectReview() {
	$("#ajaxLoadedContent").load("/reviews/addReview");
}

function redirectConfirmShipping() {
	$("#ajaxLoadedContent").load("/order/shippingConfirmation");
}

function redirectPhoneConfirmation() {
	$("#ajaxLoadedContent").load("/order/phoneConfirmation");
}

function cancelCheckout() {
	$("#ajaxLoadedContent").load("/order/cancel");
}

function redirectReview() {
	$("#ajaxLoadedContent").load("/review/sendReview");
}

function redirectReviewDetails(reviewId) {
	$("#ajaxLoadedContent").load("/admin/reviewDetails/" + reviewId);
}

function redirectAllReviews() {
	$("#ajaxLoadedContent").load("/admin/allReviews");
}

function redirectOrderDetails(orderId) {
	$("#ajaxLoadedContent").load("/admin/getOrder/" + orderId);
}

function redirectAllOrders() {
	$("#ajaxLoadedContent").load("/admin/allOrders");
}

function redirectInvalidPath() {
	$("#ajaxLoadedContent").load("/admin/invalidPath");
}

function formInterceptor(formName) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();
		if (validateRegForm()) {
			$.ajax({
				url : "http://localhost:8080/userRegistration",
				type : 'post',
				data : $form.serialize(),
				success : function(response) {
					$("#ajaxLoadedContent").load("/registerComplete");
				},
				error : function(error) {
					$("#ajaxLoadedContent").load("/registerFail");
				}
			});
		}
		;
	});
};

function loginInterceptor(formName) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();

		$.ajax({
			url : "http://localhost:8080/login",
			type : 'post',
			data : $form.serialize(),
			success : function(response) {
				confirmLoginPass();
			},
			error : function(error) {
				alert("Failed");
			}
		});

	});
};

function confirmLoginPass() {
	$.ajax({
		url : "http://localhost:8080/loginPassConfirm",
		type : "POST",
		success : function(response) {
			checkForSuspension();
		},
		error : function(error) {
			$("#ajaxLoadedContent").load("/loginErrorPage");
		}
	});
}

function checkForSuspension() {
	$.ajax({
		url : "http://localhost:8080/suspensionChecker",
		type : "POST",
		success : function(response) {
			window.location.href = "/";
		},
		error : function(error) {
			$("#ajaxLoadedContent").load("/suspensionPage");
		}
	});
}

function logout() {
	$.ajax({
		url : "http://localhost:8080/loggedout",
		type : "POST",
		success : function(response) {
			window.location.href = "/";
		},
		error : function(error) {
			alert("Failed");
		}
	});
}

function executeProductForm() {

	var formData = new FormData();
	var files = $('input[type=file]');
	for (var i = 0; i < files.length; i++) {
		if (files[i].value == "" || files[i].value == null) {
			alert("Please provide image");
			return false;
		} else {
			formData.append(files[i].name, files[i].files[0]);
		}
	}
	var formSerializeArray = $("#productForm").serializeArray();
	for (var i = 0; i < formSerializeArray.length; i++) {
		formData
				.append(formSerializeArray[i].name, formSerializeArray[i].value)
	}
	if (validateProduct()) {
		$.ajax({
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			url : "http://localhost:8080/admin/createProduct",
			success : function(response) {
				$("#ajaxLoadedContent").load("/products/allProducts");
			},
			error : function(error) {
				redirectInvalidPath();
			}
		});
	}
	;
};

function updateProduct(productId) {
	$("#ajaxLoadedContent").load("/admin/updateProduct" + "/" + productId);
};

function deleteProduct(productId) {
	if (confirm('Are you sure you want to remove this product?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteProduct/" + productId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				$("#ajaxLoadedContent").load("/products/allProducts");
			},
			error : function(error) {
				redirectInvalidPath();
			}
		});
	}
	;
};

function viewCustomerDetails(customerId) {
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/admin/getCustomer/" + customerId,
		beforeSend : function(xhr) {
			xhr.overrideMimeType("text/plain; charset=x-user-defined");
		},
		success : function(data) {
			$("#ajaxLoadedContent").load("/admin/getCustomer/" + customerId);
		},
		error : function(error) {
			alert("Failed");
		}
	});
};

function viewProductDetails(productId) {
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/products/getProduct/" + productId,
		beforeSend : function(xhr) {
			xhr.overrideMimeType("text/plain; charset=x-user-defined");
		},
		success : function(data) {
			$("#ajaxLoadedContent").load("/products/getProduct/" + productId);
		},
		error : function(error) {
			alert("Failed");
		}
	});
};

function billingAddressInterceptor(formName) {
	var $form = $("#" + formName);
	$form
			.on(
					'submit',
					function(e) {
						e.preventDefault();
						if (validateBillingAddress()) {
							$
									.ajax({
										url : "http://localhost:8080/order/storeBillingAddress",
										type : 'post',
										data : $form.serialize(),
										success : function(response) {
											$("#ajaxLoadedContent")
													.load(
															"/order/shippingConfirmation");
										},
										error : function(error) {
											alert("Failed")
										}
									});
						}
						;
					});
};

function shippingAddressInterceptor(formName) {
	var $form = $("#" + formName);
	$form.on('submit', function(e) {
		e.preventDefault();
		if (validateShippingAddress()) {
			$.ajax({
				url : "http://localhost:8080/order/storeShippingAddress",
				type : 'post',
				data : $form.serialize(),
				success : function(response) {
					$("#ajaxLoadedContent").load("/order/phoneConfirmation");
				},
				error : function(error) {
					alert("Failed");
				}
			});
		}
		;
	});
};

function customerInterceptor(formName) {
	var $form = $("#" + formName);
	$form.on('submit', function(e) {
		e.preventDefault();
		if (validatePhoneNumber()) {
			$.ajax({
				url : "http://localhost:8080/order/storeCustomer",
				type : 'post',
				data : $form.serialize(),
				success : function(response) {
					$("#ajaxLoadedContent").load("/order/orderConfirmation");
				},
				error : function(error) {
					alert("Failed")
				}
			});
		}
		;
	});
};

function reviewInterceptor(formName) {
	var $form = $("#" + formName);
	$form.on('submit', function(e) {
		e.preventDefault();
		if (validateReview()) {
			$.ajax({
				url : "http://localhost:8080/review/sendReview",
				type : 'post',
				data : $form.serialize(),
				success : function(response) {
					$("#ajaxLoadedContent").load("/review/reviewSent");
				},
				error : function(error) {
					alert("Failed");
				}
			});
		}
		;
	});
};

function executeOrder() {
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/order/createOrder",
		beforeSend : function(xhr) {
			xhr.overrideMimeType("text/plain; charset=x-user-defined");
		},
		success : function(data) {
			$("#ajaxLoadedContent").load("/order/orderExecuted");
		},
		error : function(error) {
			$("#ajaxLoadedContent").load("/order/stockProblem");
		}
	});
};

function deleteItem(cartId, itemId) {
	if (confirm('Remove item from cart?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/cart/removeCartItem/" + cartId + "/"
					+ itemId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				$("#ajaxLoadedContent").load("/cart/viewCart");
			},
			error : function(error) {
				console.log(error);

			}
		});
	}
	;
};

function clearCart(cartId) {
	if (confirm('Are you sure you want to clear cart?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/cart/removeAllItems/" + cartId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				$("#ajaxLoadedContent").load("/cart/viewCart");
			},
			error : function(error) {
				console.log(error);

			}
		});
	}
	;
};

function addToCart(productId) {
	console.log(productId)
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/cart/add/" + productId,
		beforeSend : function(xhr) {
			xhr.overrideMimeType("text/plain; charset=x-user-defined");
		},
		success : function(data) {
			alert("Added To Cart")
			$("#ajaxLoadedContent").load("/products/getProduct/" + productId);
		},
		error : function(error) {
			alert("Failed");

		}
	});
};

function redirectCheckout() {
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/order/billingConfirmation",
		beforeSend : function(xhr) {
			xhr.overrideMimeType("text/plain; charset=x-user-defined");
		},
		success : function(data) {
			$("#ajaxLoadedContent").load("/order/billingConfirmation");
		},
		error : function(error) {
			$("#ajaxLoadedContent").load("/order/cartError");

		}
	});
};

function ValidatePassword() {
	var password = document.getElementById("password").value;
	var confirmpass = document.getElementById("confirmpass").value;
	if (password != confirmpass) {
		alert("Password does Not Match.");
		return false;
	}
	return true;
};

function validateNumber(e) {
	const pattern = /^\d{0,4}(\.\d{0,4})?$/g;

	return pattern.test(e.key)
};

function validatePhoneNumber() {
	var customerPhone = $("#customerPhone").val();

	var returnValue = true;

	if (customerPhone.length < 9 || customerPhone.length > 15) {
		$("#customerPhoneError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#customerPhoneError").css({
			"visibility" : "hidden"
		});
	}

	return returnValue;
};


function deleteReview(reviewId) {
	if (confirm('Are you sure you want to clear this review?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteReview/" + reviewId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllReviews();
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
	;
};

function deleteCustomer(customerId) {
	if (confirm('Are you sure you want to remove this customer?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteCustomer/" + customerId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllCustomers();
			},
			error : function(error) {
				alert("Failed");
			}
		});
	}
	;
};

function suspendUser(userId) {
	if (confirm('Are you sure you want to suspend this user?')) {
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/admin/suspendUser/" + userId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllCustomers();
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
	;
};

function deleteOrder(orderId) {
	if (confirm('Are you sure you want to clear this order?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteOrder/" + orderId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllOrders();
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
	;
};

function validateKeyword() {
	var returnValue = true;
	var keyword = $("#keyword").val();

	if (keyword === "") {
		$("#keywordError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#keywordError").css({
			"visibility" : "hidden"
		});
	}

	return returnValue;
}
