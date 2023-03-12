package com.radovan.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.service.ProductService;

@Controller
@RequestMapping(value = "/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@RequestMapping(value="/allProducts",method = RequestMethod.GET)
	public String getAllProducts(ModelMap map) {
		List<ProductDto> allProducts = productService.listAll();
		map.put("allProducts", allProducts);
		map.put("recordsPerPage", 5);
		return "fragments/productList :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/getProduct/{productId}",method = RequestMethod.GET)
	public String getProductDetails(@PathVariable ("productId") Integer productId,ModelMap map) {
		
		ProductDto currentProduct = productService.getProduct(productId);
		map.put("currentProduct", currentProduct);
		return "fragments/productDetails :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/searchProducts",method=RequestMethod.GET)
	public String searchProducts(@RequestParam ("keyword") String keyword,ModelMap map) {
		
		List<ProductDto> searchResult = productService.listAllByKeyword(keyword);
		map.put("searchResult", searchResult);
		map.put("recordsPerPage", 5);
		return "fragments/searchList :: ajaxLoadedContent";
	}
	
	
	
}
