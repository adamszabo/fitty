package com.acme.fitness.orders.simple;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.orders.StoreDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;

public class SimpleStoreServiceTest {

	private SimpleStoreService underTest;

	@Mock
	private StoreDao storeDao;

	@Before
	public void setUp() {
		underTest = new SimpleStoreService();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddProductShouldInvokeTheMethodRight() {
		// GIVEN
		Product expectedProduct = new Product();
		int expectedQuantity = 11;
		Store expectedStore = new Store(expectedProduct, expectedQuantity);
		underTest.setStoreDao(storeDao);
		// WHEN
		underTest.addProduct(expectedProduct, expectedQuantity);
		// THEN
		BDDMockito.verify(storeDao).save(expectedStore);
	}

	@Test
	public void testGetStoreByIdShouldInvokeTheMethodRight() throws FitnessDaoException {
		// GIVEN
		long expectedId = 1L;
		Store expectedStore = new Store();
		BDDMockito.given(storeDao.getStoreById(expectedId)).willReturn(
				expectedStore);
		underTest.setStoreDao(storeDao);
		// WHEN
		Store result = underTest.getStoreById(expectedId);
		// THEN
		BDDMockito.verify(storeDao).getStoreById(expectedId);
		Assert.assertEquals(expectedStore, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetStoreByIdShouldThrowsExceptionWhenTheProductIdsNotFound() throws FitnessDaoException {
		//GIVEN
		BDDMockito.given(storeDao.getStoreById(Mockito.anyLong())).willThrow(new FitnessDaoException());
		underTest.setStoreDao(storeDao);
		//WHEN
		underTest.getStoreById(1L);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(Mockito.anyLong());
	}
	
	@Test
	public void testGetStoreByProductShouldInvokeTheMethodRight() throws FitnessDaoException {
		//GIVEN
		Product expectedProduct = new Product();
		expectedProduct.setId(5L);
		Store expectedStore = new Store();
		expectedStore.setId(1L);
		BDDMockito.given(storeDao.getStoreByProductId(expectedProduct.getId())).willReturn(expectedStore);
		underTest.setStoreDao(storeDao);
		//WHEN
		Store result = underTest.getStoreByProduct(expectedProduct);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(expectedProduct.getId());
		Assert.assertEquals(expectedStore, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetStoreByProductShouldThrowsExceptionWhenTheProductIdsNotFound() throws FitnessDaoException {
		//GIVEN
		BDDMockito.given(storeDao.getStoreByProductId(Mockito.anyLong())).willThrow(new FitnessDaoException());
		underTest.setStoreDao(storeDao);
		//WHEN
		underTest.getStoreByProduct(new Product());
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(Mockito.anyLong());
	}
	
	@Test
	public void testTakeOutProductShouldReturnTrueWhenThereIsEnoughQuantityOfTheProduct() throws FitnessDaoException {
		//GIVEN
		Product product = new Product("name", null, 1.0, null, new Date());
		product.setId(1L);
		int quantity = 11;
		int takeOutQuantity = 7;
		boolean expectedAvaiablility = true;
		Store store = new Store(product, quantity);
		BDDMockito.given(storeDao.getStoreByProductId(product.getId())).willReturn(store);
		Store expectedStore = new Store(product, quantity - takeOutQuantity);
		underTest.setStoreDao(storeDao);
		//WHEN
		boolean result = underTest.takeOutProduct(product, takeOutQuantity);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(product.getId());
		BDDMockito.verify(storeDao).update(expectedStore);
		Assert.assertEquals(expectedAvaiablility, result);
	}
	
	@Test
	public void testTakeOutProductShouldReturnFalseWhenThereIsNotEnoughQuantityOfTheProduct() throws FitnessDaoException {
		//GIVEN
		Product product = new Product("name", null, 1.0, null, new Date());
		product.setId(1L);
		int quantity = 7;
		int takeOutQuantity = 11;
		boolean expectedAvaiablility = false;
		Store store = new Store(product, quantity);
		BDDMockito.given(storeDao.getStoreByProductId(product.getId())).willReturn(store);
		underTest.setStoreDao(storeDao);
		//WHEN
		boolean result = underTest.takeOutProduct(product, takeOutQuantity);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(product.getId());
		Assert.assertEquals(expectedAvaiablility, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testTakeOutProductShouldThrowsExceptionWhenTheProductIdsNotFound() throws FitnessDaoException {
		//GIVEN
		BDDMockito.given(storeDao.getStoreByProductId(Mockito.anyLong())).willThrow(new FitnessDaoException());
		underTest.setStoreDao(storeDao);
		//WHEN
		underTest.takeOutProduct(new Product(), 1);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(Mockito.anyLong());
	}
	
	@Test
	public void testPutInProductShouldInvokeTheMethodRight() throws FitnessDaoException {
		//GIVEN
		Product product = new Product("name", null, 1.0, null, new Date());
		product.setId(1L);
		int quantity = 7;
		int putInQuantity = 11;
		Store store = new Store(product, quantity);
		BDDMockito.given(storeDao.getStoreByProductId(product.getId())).willReturn(store);
		Store expectedStore = new Store(product, quantity + putInQuantity);
		underTest.setStoreDao(storeDao);
		//WHEN
		underTest.putInProduct(product, putInQuantity);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(product.getId());
		BDDMockito.verify(storeDao).update(expectedStore);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testPutInProductShouldThrowsExceptionWhenTheProductIdsNotFound() throws FitnessDaoException {
		//GIVEN
		BDDMockito.given(storeDao.getStoreByProductId(Mockito.anyLong())).willThrow(new FitnessDaoException());
		underTest.setStoreDao(storeDao);
		//WHEN
		underTest.putInProduct(new Product(), 1);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(Mockito.anyLong());
	}
	
	@Test
	public void testDeleteStoreShouldInvokeTheMethodRight() {
		//GIVEN
		Store store = new Store();
		underTest.setStoreDao(storeDao);
		//WHEN
		underTest.deleteStore(store);
		//THEN
		BDDMockito.verify(storeDao).delete(store);
	}
	
	@Test
	public void testUpdateStoreShouldInvokeTheMethodRight() {
		//GIVEN
		Store store = new Store();
		underTest.setStoreDao(storeDao);
		//WHEN
		underTest.updateStore(store);
		//THEN
		BDDMockito.verify(storeDao).update(store);
	}
}
