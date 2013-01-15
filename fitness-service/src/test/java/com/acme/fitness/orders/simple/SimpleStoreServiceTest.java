package com.acme.fitness.orders.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.orders.StoreDao;
import com.acme.fitness.dao.products.ProductDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;

public class SimpleStoreServiceTest {

	private SimpleStoreService underTest;

	@Mock
	private StoreDao storeDao;
	
	@Mock
	private ProductDao productDao;
	
	@Mock
	private Product product;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleStoreService(storeDao, productDao);
	}

	@Test
	public void testAddProductShouldInvokeTheMethodRight() {
		// GIVEN
		Product expectedProduct = new Product();
		int expectedQuantity = 11;
		// WHEN
		underTest.addProduct(expectedProduct, expectedQuantity);
		// THEN
	}

	@Test
	public void testGetStoreByIdShouldInvokeTheMethodRight() throws FitnessDaoException {
		// GIVEN
		long expectedId = 1L;
		Store expectedStore = new Store();
		BDDMockito.given(storeDao.getStoreById(expectedId)).willReturn(
				expectedStore);
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
		//WHEN
		underTest.getStoreByProduct(new Product());
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(Mockito.anyLong());
	}
	
	@Test
	public void testTakeOutProductShouldReturnTrueWhenThereIsEnoughQuantityOfTheProduct() throws FitnessDaoException {
		//GIVEN
		Product product = new Product("name", null, 1.0, null, new Date(), null);
		product.setId(1L);
		int quantity = 11;
		int takeOutQuantity = 7;
		boolean expectedAvaiablility = true;
		Store store = new Store(product, quantity);
		BDDMockito.given(storeDao.getStoreByProductId(product.getId())).willReturn(store);
		Store expectedStore = new Store(product, quantity - takeOutQuantity);
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
		product.setId(1L);
		int quantity = 7;
		int takeOutQuantity = 11;
		boolean expectedAvaiablility = false;
		Store store = new Store(product, quantity);
		BDDMockito.given(storeDao.getStoreByProductId(product.getId())).willReturn(store);
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
		//WHEN
		underTest.takeOutProduct(new Product(), 1);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(Mockito.anyLong());
	}
	
	
	@Test(expected=FitnessDaoException.class)
	public void testPutInProductShouldThrowsExceptionWhenTheProductIsNotFoundInDatabase() throws FitnessDaoException {
		//GIVEN
		product.setId(1L);
		BDDMockito.given(productDao.getAllProduct()).willReturn(new ArrayList<Product>());
		//WHEN
		underTest.putInProduct(product, 1);
		//THEN
		BDDMockito.verify(productDao).getAllProduct().contains(product);
	}
	
	@Test
	public void testPutInProductShouldThrowsExceptionWhenTheProductIsNotFoundInStoreTable() throws FitnessDaoException {
		//GIVEN
		List<Product> products = new ArrayList<Product>();
		product.setId(1L);
		products.add(product);
		BDDMockito.given(productDao.getAllProduct()).willReturn(products);
		BDDMockito.when(storeDao.getStoreByProductId(product.getId())).thenThrow(new FitnessDaoException());
		//WHEN
		underTest.putInProduct(product, 1);
		//THEN
		BDDMockito.verify(productDao).getAllProduct();
		BDDMockito.verify(storeDao).getStoreByProductId(Mockito.anyLong());
		BDDMockito.verify(storeDao).save(new Store(product, 1)); 
	}
	
	@Test
	public void testPutInProductShouldInvokeTheMethodRight() throws FitnessDaoException {
		//GIVEN
		List<Product> products = new ArrayList<Product>();
		products.add(product);
		int quantity = 7;
		int putInQuantity = 11;
		Store store = new Store(product, quantity);
		BDDMockito.given(storeDao.getStoreByProductId(product.getId())).willReturn(store);
		BDDMockito.given(productDao.getAllProduct()).willReturn(products);
		Store expectedStore = new Store(product, quantity + putInQuantity);
		//WHEN
		underTest.putInProduct(product, putInQuantity);
		//THEN
		BDDMockito.verify(storeDao).getStoreByProductId(product.getId());
		BDDMockito.verify(storeDao).update(expectedStore);
		BDDMockito.verify(productDao).getAllProduct();
	}
	
	@Test
	public void testPutInProductShouldThrowsExceptionWhenTheProductIdsNotFound() throws FitnessDaoException {
		//GIVEN
		BDDMockito.given(storeDao.getAllStores()).willReturn(new ArrayList<Store>());
		//WHEN
		List<Store> result = underTest.getAllStores();
		//THEN
		BDDMockito.verify(storeDao).getAllStores();
		Assert.assertEquals(new ArrayList<Store>(), result);
	}
	
	@Test
	public void testDeleteStoreShouldInvokeTheMethodRight() {
		//GIVEN
		Store store = new Store();
		//WHEN
		underTest.deleteStore(store);
		//THEN
		BDDMockito.verify(storeDao).delete(store);
	}
	
	@Test
	public void testUpdateStoreShouldInvokeTheMethodRight() {
		//GIVEN
		Store store = new Store();
		underTest.updateStore(store);
		//THEN
		BDDMockito.verify(storeDao).update(store);
	}
}
