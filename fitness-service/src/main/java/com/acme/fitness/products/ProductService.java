package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.ProductImage;

public interface ProductService {
	
	Product addProduct(String name, String details, double price, String manufacturer, Date creation, ProductImage productImage);

	void deleteProduct(Product product);

	void updateProduct(Product product);
	
	List<Product> getAllProducts();

	Product getProductById(long id) throws FitnessDaoException;

	List<Product> getProductsByName(String name);

	List<Product> getProductsByManufacturer(String manufacturer);

	List<Product> getProductsByPriceInterval(double fromPrice, double toPrice);

	void updateProductAndSaveImage(Product product, ProductImage image);
}
