package com.acme.fitness.dao.products;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.Product;

public interface ProductDao extends GenericDao<Product> {

	public List<Product> getAllProduct();
	
	public Product getProductById(long id) throws FitnessDaoException;
	
	public List<Product> getProductsByName(String name);
	
	public List<Product> getProductsByManufacturer(String manufacturer);
	
	public List<Product> getProductsByPriceInterval(double fromPrice, double toPrice);
}
