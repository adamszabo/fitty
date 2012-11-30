package com.acme.fitness.products.simple;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.products.ProductDao;
import com.acme.fitness.domain.products.Product;

public class SimpleProductServiceTest {
	
	private SimpleProductService underTest;

	@Mock
	private ProductDao productDao;
	
	@Before
	public void setUp(){
		underTest = new SimpleProductService();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAddProductShouldInvokeTheMethodRight(){
		//GIVEN
		String expectedName = "name";
		String expectedDetails = "details";
		double expectedPrice = 11.0;
		String expectedManufacturer = "manufacturer";
		Date expectedDate = new Date();
		Product expectedProduct = new Product(expectedName, expectedDetails, expectedPrice, expectedManufacturer, expectedDate);
		underTest.setProductDao(productDao);
		//WHEN
		underTest.addProduct(expectedName, expectedDetails, expectedPrice, expectedManufacturer, expectedDate);
		//THEN
		BDDMockito.verify(productDao).save(expectedProduct);
	}
	
	@Test
	public void testDeleteProductShouldInvokeTheMethodRight(){
		//GIVEN
		Product expectedProduct = new Product("name", "details", 11L, "manufacturer", new Date());
		underTest.setProductDao(productDao);
		//WHEN
		underTest.deleteProduct(expectedProduct);
		//THEN
		BDDMockito.verify(productDao).delete(expectedProduct);
	}
	
	@Test
	public void testUpdateProductShouldInvokeTheMethodRight() {
		//GIVEN
		Product expectedProduct = new Product("name", "details", 11L, "manufacturer", new Date());
		underTest.setProductDao(productDao);
		//WHEN
		underTest.updateProduct(expectedProduct);
		//THEn
		BDDMockito.verify(productDao).update(expectedProduct);
	}
	
	
}
