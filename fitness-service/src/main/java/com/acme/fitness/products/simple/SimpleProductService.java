package com.acme.fitness.products.simple;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.products.Product;
import com.acme.fitness.products.ProductService;

public class SimpleProductService implements ProductService {

	@Override
	public void addProduct(String name, String details, double price,
			String manufacturer, Date creation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public Product getProductById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByPriceInterval(double fromPrice,
			double toPrice) {
		// TODO Auto-generated method stub
		return null;
	}

}
