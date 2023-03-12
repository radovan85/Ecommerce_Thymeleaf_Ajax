package com.radovan.spring.service;

import java.io.IOException;
import java.util.List;

import com.radovan.spring.dto.ProductDto;


public interface ProductService {

	List<ProductDto> listAll();

	ProductDto getProduct(Integer id);

	void deleteProduct(Integer id);

	ProductDto addProduct(ProductDto product) throws IOException;
	
	ProductDto updateProduct(Integer id,ProductDto product) throws IOException;

	List<ProductDto> listAllByKeyword(String keyword);
	
	
}
