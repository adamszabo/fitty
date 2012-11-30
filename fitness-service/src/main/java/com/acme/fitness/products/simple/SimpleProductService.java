package com.acme.fitness.products.simple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.ProductDao;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.products.ProductService;

@Service
public class SimpleProductService implements ProductService {
	
	@Autowired
	private ProductDao productDao;

	@Override
	public Product addProduct(String name, String details, double price,
			String manufacturer, Date creation) {
		Product product = new Product(name, details, price, manufacturer, creation);
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
	public Product getProductById(long id) {
		Product result = productDao.getProductById(id);
		return result;
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

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
