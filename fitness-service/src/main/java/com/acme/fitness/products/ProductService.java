package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.products.Product;

public interface ProductService {
	void addProduct(String name, String details, double price, String manufacturer, Date creation);
	void deleteProduct(Product product);
	void updateProduct(Product product);
	Product getProductById(long id);
	Product getProductByName(String name);
	List<Product> getProductsByManufacturer(String manufacturer);
	List<Product> getProductsByPriceInterval(double fromPrice, double toPrice);
}
