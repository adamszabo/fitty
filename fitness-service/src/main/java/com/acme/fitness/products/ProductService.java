package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.Product;

public interface ProductService {
	
	Product addProduct(String name, String details, double price, String manufacturer, Date creation);

	void deleteProduct(Product product);

	void updateProduct(Product product);

	Product getProductById(long id) throws FitnessDaoException;

	List<Product> getProductByName(String name);

	List<Product> getProductsByManufacturer(String manufacturer);

	List<Product> getProductsByPriceInterval(double fromPrice, double toPrice);
}
