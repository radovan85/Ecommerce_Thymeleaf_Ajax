function validateBillingAddress() {

	var address = $("#address").val();
	var city = $("#city").val();
	var state = $("#state").val();
	var zipcode = $("#zipcode").val();
	var country = $("#country").val();

	var returnValue = true;

	if (address === "") {
		$("#addressError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#addressError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (city === "") {
		$("#cityError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#cityError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (state === "") {
		$("#stateError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#stateError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (zipcode === "") {
		$("#zipcodeError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#zipcodeError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (country === "") {
		$("#countryError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#countryError").css({
			"visibility" : "hidden"
		});
	}
	;

	return returnValue;
};

function validateProduct() {

	var productName = $("#productName").val();
	var productBrand = $("#productBrand").val();
	var productModel = $("#productModel").val();
	var productPrice = $("#productPrice").val();
	var unitStock = $("#unitStock").val();
	// var productStatus = document.forms.productForm.productStatus.value;
	var productCategory = $("#productCategory").val();
	var productDescription = $("#productDescription").val();
	var discount = $("#discount").val();
	var c01 = document.getElementById("c01");
	var c02 = document.getElementById("c02");

	var productPriceNum = Number(productPrice);
	var unitStockNum = Number(unitStock);
	var discountNum = Number(discount);

	var returnValue = true;

	if (productName === "") {
		$("#productNameError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#productNameError").css({
			"visibility" : "hidden"
		});
	}

	if (productBrand === "") {
		$("#productBrandError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#productBrandError").css({
			"visibility" : "hidden"
		});
	}

	if (productModel === "") {
		$("#productModelError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#productModelError").css({
			"visibility" : "hidden"
		});
	}

	if (productPrice === "") {
		$("#productPriceError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else if (productPriceNum <= 0) {
		$("#productPriceError").css({
			"visibility" : "visible"
		});
	} else {
		$("#productPriceError").css({
			"visibility" : "hidden"
		});
	}

	if (unitStock === "") {
		$("#unitStockError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else if (unitStockNum < 0) {
		$("#unitStockError").css({
			"visibility" : "visible"
		});
	} else {
		$("#unitStockError").css({
			"visibility" : "hidden"
		});
	}

	if (c01.checked === false && c02.checked === false) {
		$("#productStatusError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#productStatusError").css({
			"visibility" : "hidden"
		});
	}

	if (productCategory === "") {
		$("#productCategoryError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#productCategoryError").css({
			"visibility" : "hidden"
		});
	}

	if (discount === "") {
		$("#discountError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else if (discountNum < 0) {
		$("#discountError").css({
			"visibility" : "visible"
		});
	} else {
		$("#discountError").css({
			"visibility" : "hidden"
		});
	}

	if (productDescription === "") {
		$("#productDescriptionError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#productDescriptionError").css({
			"visibility" : "hidden"
		});
	}

	return returnValue;
};

function validateRegForm() {

	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var email = $("#email").val();
	var password = $("#password").val();
	var billAddress = $("#billAddress").val();
	var billCity = $("#billCity").val();
	var billState = $("#billState").val();
	var billZipcode = $("#billZipcode").val();
	var billCountry = $("#billCountry").val();
	var shippAddress = $("#shippAddress").val();
	var shippCity = $("#shippCity").val();
	var shippState = $("#shippState").val();
	var shippZipcode = $("#shippZipcode").val();
	var shippCountry = $("#shippCountry").val();
	var customerPhone = $("#customerPhone").val();

	var regEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/g; // Javascript
																		// reGex
																		// for
																		// Email
																		// Validation.

	var returnValue = true;

	if (firstName === "") {
		$("#firstNameError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#firstNameError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (lastName === "") {
		$("#lastNameError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#lastNameError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (email === "" || !regEmail.test(email)) {
		$("#emailError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#emailError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (password === "") {
		$("#passwordError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#passwordError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (billAddress === "") {
		$("#billAddressError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#billAddressError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (billCity === "") {
		$("#billCityError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#billCityError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (billState === "") {
		$("#billStateError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#billStateError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (billZipcode === "") {
		$("#billZipcodeError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#billZipcodeError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (billCountry === "") {
		$("#billCountryError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#billCountryError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (shippAddress === "") {
		$("#shippAddressError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#shippAddressError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (shippCity === "") {
		$("#shippCityError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#shippCityError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (shippState === "") {
		$("#shippStateError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#shippStateError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (shippZipcode === "") {
		$("#shippZipcodeError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#shippZipcodeError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (shippCountry === "") {
		$("#shippCountryError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#shippCountryError").css({
			"visibility" : "hidden"
		});
	}
	;

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
	;

	return returnValue;
};

function validateShippingAddress() {

	var address = $("#address").val();
	var city = $("#city").val();
	var state = $("#state").val();
	var zipcode = $("#zipcode").val();
	var country = $("#country").val();

	var returnValue = true;

	if (address === "") {
		$("#addressError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#addressError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (city === "") {
		$("#cityError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#cityError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (state === "") {
		$("#stateError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#stateError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (zipcode === "") {
		$("#zipcodeError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#zipcodeError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (country === "") {
		$("#countryError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#countryError").css({
			"visibility" : "hidden"
		});
	}
	;

	return returnValue;
};


function validateReview() {
	var text = $("#text").val();
	text = text.trim();
	var returnValue = true;

	if (text === "" || text.length > 255) {
		$("#textError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#textError").css({
			"visibility" : "hidden"
		});
	}

	return returnValue;
};