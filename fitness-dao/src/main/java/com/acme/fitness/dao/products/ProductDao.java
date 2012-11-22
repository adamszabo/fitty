package com.acme.fitness.dao.products;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.products.Product;

public interface ProductDao extends GenericDao<Product> {

	public List<Product> getAllProduct();
	
	public Product getProductById(long id);
	
	public List<Product> getProductByName(String name);
	
	public List<Product> getProductByManufacturer(String manufacturer);
}
