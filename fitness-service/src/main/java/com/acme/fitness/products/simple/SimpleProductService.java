package com.acme.fitness.products.simple;

import java.util.Date;
import java.util.List;

import org.hibernate.TransientObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.ProductDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.ProductImage;
import com.acme.fitness.products.ProductImageService;
import com.acme.fitness.products.ProductService;

@Service
public class SimpleProductService implements ProductService {

	private ProductDao productDao;
	private ProductImageService productImageService;

	@Autowired
	public SimpleProductService(ProductDao productDao, ProductImageService productImageDao) {
		this.productDao = productDao;
		this.productImageService = productImageDao;
	}

	@Override
	public Product addProduct(String name, String details, double price, String manufacturer, Date creation, ProductImage productImage) {
		if (productImage != null) {
			productImageService.saveProductImage(productImage);
		}

		Product product = new Product(name, details, price, manufacturer, creation, productImage);
		productDao.save(product);
		return product;
	}

	@Override
	public void deleteProduct(Product product) {
		productDao.delete(product);

	}

	@Override
	public void updateProduct(Product product) {
		productDao.update(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productDao.getAllProduct();
	}

	@Override
	public Product getProductById(long id) throws FitnessDaoException {
		Product result = productDao.getProductById(id);
		return result;
	}

	@Override
	public List<Product> getProductsByName(String name) {
		List<Product> result = productDao.getProductsByName(name);
		return result;
	}

	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		return productDao.getProductsByManufacturer(manufacturer);
	}

	@Override
	public List<Product> getProductsByPriceInterval(double fromPrice, double toPrice) {
		List<Product> result = productDao.getProductsByPriceInterval(fromPrice, toPrice);
		return result;
	}

	@Override
	public void updateProductAndSaveImage(Product product, ProductImage image) {
		productImageService.saveProductImage(image);
		productDao.update(product);
	}
}
