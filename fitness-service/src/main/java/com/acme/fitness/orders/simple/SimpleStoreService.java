package com.acme.fitness.orders.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.orders.StoreDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.orders.StoreService;

@Service
public class SimpleStoreService implements StoreService {

	@Autowired
	private StoreDao storeDao;

	@Override
	public Store addProduct(Product product, int quantity) {
		Store store = new Store(product, quantity);
		storeDao.save(store);
		return store;
	}

	@Override
	public Store getStoreById(long id) throws FitnessDaoException {
		return storeDao.getStoreById(id);
	}

	@Override
	public Store getStoreByProduct(Product product) throws FitnessDaoException {
		return storeDao.getStoreByProductId(product.getId());
	}

	@Override
	public boolean takeOutProduct(Product product, int quantity)
			throws FitnessDaoException {
		boolean result = false;
		Store store = storeDao.getStoreByProductId(product.getId());
		if ((store.getQuantity() - quantity) >= 0) {
			result = true;
			updateStoreQuantity(store, store.getQuantity() - quantity);
		}
		return result;
	}

	@Override
	public void putInProduct(Product product, int quantity)
			throws FitnessDaoException {
		Store store = storeDao.getStoreByProductId(product.getId());
		updateStoreQuantity(store, store.getQuantity() + quantity);
	}
	
	@Override
	public void deleteStore(Store store) {
		storeDao.delete(store);
	}

	@Override
	public void updateStore(Store store) {
		storeDao.update(store);
	}

	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	private void updateStoreQuantity(Store store, int quantity) {
		store.setQuantity(quantity);
		storeDao.update(store);
	}

}
