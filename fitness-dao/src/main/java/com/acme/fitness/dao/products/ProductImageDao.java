package com.acme.fitness.dao.products;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.ProductImage;

public interface ProductImageDao extends GenericDao<ProductImage> {
	public List<ProductImage> getAllProductImages();
	public ProductImage getProductImageById(long id) throws FitnessDaoException;
}
