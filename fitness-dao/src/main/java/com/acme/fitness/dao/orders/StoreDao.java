package com.acme.fitness.dao.orders;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.orders.Store;

public interface StoreDao extends GenericDao<Store> {
	
	public List<Store> getAllStores();
	
	public Store getStoreById(long id);

	public Store getStoreByProdctId(long id);

	public List<Store> getStoresByProductName(String name);

	public List<Store> getStoresByProductManufacturer(String manufacturer);
}
