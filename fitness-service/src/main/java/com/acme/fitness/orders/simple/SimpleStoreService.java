package com.acme.fitness.orders.simple;

import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.orders.StoreService;

public class SimpleStoreService implements StoreService {

	@Override
	public void addProduct(Product product, int quantity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Store getStoreById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getPrductById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getQuantityByProduct(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getQuantityById(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void takeOutProduct(Product product, int quantity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putInProduct(Product product, int quantity) {
		// TODO Auto-generated method stub

	}

}
