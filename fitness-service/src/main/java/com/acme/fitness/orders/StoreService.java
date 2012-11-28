package com.acme.fitness.orders;

import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;

public interface StoreService {
	void addProduct(Product product, int quantity);
	Store getStoreById(long id);
	Product getPrductById(long id);
	int getQuantityByProduct(Product product);
	int getQuantityById(long id);
	void takeOutProduct(Product product, int quantity);
	void putInProduct(Product product, int quantity);
}
