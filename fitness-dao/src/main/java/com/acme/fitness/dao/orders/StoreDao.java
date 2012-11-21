package com.acme.fitness.dao.orders;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.Store;

public interface StoreDao extends GenericDao<Store> {
	
	public List<Store> getAllStore();
	
	public Store getStoreById(long id);

	public Store getStoreByProdctId(long id);

	public List<Store> getStoreByProductName(String name);

	public List<Store> getStoreByProductManufacturer(String manufacturer);
}
