package com.acme.fitness.products.simple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.ProductImageDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.ProductImage;
import com.acme.fitness.products.ProductImageService;

@Service
public class SimpleProductImageService implements ProductImageService {
	private ProductImageDao imageDao;
	
	@Autowired
	public SimpleProductImageService(ProductImageDao imageDao){
		this.imageDao=imageDao;
	}
	
	@Override
	public void saveProductImage(ProductImage image) {
		imageDao.save(image);
	}

	@Override
	public void updateProductImage(ProductImage image) {
		imageDao.update(image);
	}

	@Override
	public void deleteProductImage(ProductImage image) {
		imageDao.delete(image);
	}

	@Override
	public List<ProductImage> getAllProductImages() {
		return imageDao.getAllProductImages();
	}

	@Override
	public ProductImage getProductImageById(long id) throws FitnessDaoException {
		return imageDao.getProductImageById(id);
	}

}
