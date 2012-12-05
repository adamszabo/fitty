package com.acme.fitness.domain.exceptions;

import java.util.List;

import com.acme.fitness.domain.products.Product;

public class StoreQuantityException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private List<Product> product;
	
	public StoreQuantityException(List<Product> product) {
		super();
		this.product=product;
	}
	
	public StoreQuantityException(String msg, List<Product> product){
		super(msg);
		this.product=product;
	}

	public List<Product> getProduct() {
		return product;
	}
	
}
