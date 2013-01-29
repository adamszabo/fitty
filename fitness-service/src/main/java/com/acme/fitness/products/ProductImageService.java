package com.acme.fitness.products;

import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.ProductImage;

public interface ProductImageService {
	void saveProductImage(ProductImage image);
	void updateProductImage(ProductImage image);
	void deleteProductImage(ProductImage image);
	List<ProductImage> getAllProductImages();
	ProductImage getProductImageById(long id) throws FitnessDaoException;
}
