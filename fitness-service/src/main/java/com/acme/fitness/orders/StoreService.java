package com.acme.fitness.orders;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;

public interface StoreService {
	Store addProduct(Product product, int quantity);
	Store getStoreById(long id) throws FitnessDaoException;
	Store getStoreByProduct(Product product) throws FitnessDaoException;
	boolean takeOutProduct(Product product, int quantity) throws FitnessDaoException;
	void putInProduct(Product product, int quantity) throws FitnessDaoException;
	void deleteStore(Store store);
	void updateStore(Store store);
}
