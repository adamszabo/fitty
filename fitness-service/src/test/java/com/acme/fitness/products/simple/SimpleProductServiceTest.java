package com.acme.fitness.products.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.products.ProductDao;
import com.acme.fitness.dao.products.ProductImageDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.ProductImage;

public class SimpleProductServiceTest {
	
	private SimpleProductService underTest;

	@Mock
	private ProductDao productDao;
	
	@Mock
	private ProductImageDao productImageDao;
	
	@Mock
	private ProductImage productImage;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleProductService(productDao, productImageDao);
	}
	
	@Test
	public void testAddProductShouldInvokeTheMethodRightWhenTheProductImageIsNull(){
		//GIVEN
		String expectedName = "name";
		String expectedDetails = "details";
		double expectedPrice = 11.0;
		String expectedManufacturer = "manufacturer";
		Date expectedDate = new Date();
		Product expectedProduct = new Product(expectedName, expectedDetails, expectedPrice, expectedManufacturer, expectedDate, productImage);
		//WHEN
		underTest.addProduct(expectedName, expectedDetails, expectedPrice, expectedManufacturer, expectedDate, productImage);
		//THEN
		BDDMockito.verify(productDao).save(expectedProduct);
	}
	
	@Test
	public void testAddProductShouldInvokeTheMethodRightWhenTheProductImageIsNotNull(){
		//GIVEN
		String expectedName = "name";
		String expectedDetails = "details";
		double expectedPrice = 11.0;
		String expectedManufacturer = "manufacturer";
		Date expectedDate = new Date();
		Product expectedProduct = new Product(expectedName, expectedDetails, expectedPrice, expectedManufacturer, expectedDate, productImage);
		//WHEN
		underTest.addProduct(expectedName, expectedDetails, expectedPrice, expectedManufacturer, expectedDate, productImage);
		//THEN
		BDDMockito.verify(productImageDao).save(productImage);
		BDDMockito.verify(productDao).save(expectedProduct);
	}
	
	@Test
	public void testDeleteProductShouldInvokeTheMethodRight(){
		//GIVEN
		Product expectedProduct = new Product("name", "details", 11L, "manufacturer", new Date(), productImage);
		//WHEN
		underTest.deleteProduct(expectedProduct);
		//THEN
		BDDMockito.verify(productDao).delete(expectedProduct);
	}
	
	@Test
	public void testUpdateProductShouldInvokeTheMethodRight() {
		//GIVEN
		Product expectedProduct = new Product("name", "details", 11L, "manufacturer", new Date(), productImage);
		//WHEN
		underTest.updateProduct(expectedProduct);
		//THEn
		BDDMockito.verify(productDao).update(expectedProduct);
	}
	
	@Test
	public void testGetProductByIdShouldReturnRightWhenTheIdIsAppropriate() throws FitnessDaoException {
		//GIVEN
		Long expectedId = 11L;
		Product expectedProduct = new Product();
		expectedProduct.setName("test");
		BDDMockito.given(productDao.getProductById(expectedId)).willReturn(expectedProduct);
		//WHEN
		Product result = underTest.getProductById(expectedId);
		//THEN
		Assert.assertEquals(expectedProduct, result);
		BDDMockito.verify(productDao).getProductById(expectedId);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetProductByIdShouldThrowExceptionWhenIdIsNotExist() throws FitnessDaoException {
		//GIVEN
		Long wrongExpectedId = 11L;
		BDDMockito.given(productDao.getProductById(wrongExpectedId)).willThrow(new FitnessDaoException());
		//WHEN
		underTest.getProductById(wrongExpectedId);
		//THEN
		BDDMockito.verify(productDao).getProductById(wrongExpectedId);
	}
	
	@Test
	public void testGetProductsByNameShouldReturnRightWhenTheNameIsAppropriate() {
		//GIVEN
		String expectedName = "Gabi";
		List<Product> expectedProducts = new ArrayList<Product>();
		BDDMockito.given(productDao.getProductsByName(expectedName)).willReturn(expectedProducts);
		//WHEN
		List<Product> result = underTest.getProductsByName(expectedName);
		//THEN
		Assert.assertEquals(expectedProducts, result);
		BDDMockito.verify(productDao).getProductsByName(expectedName);
	}
	
	@Test
	public void testGetProductsByManufacturerShouldReturnRightWhenTheManufacturerIsAppropriate() {
		//GIVEN
		String expectedName = "Nike";
		List<Product> expectedProducts = new ArrayList<Product>();
		BDDMockito.given(productDao.getProductsByName(expectedName)).willReturn(expectedProducts);
		//WHEN
		List<Product> result = underTest.getProductsByName(expectedName);
		//THEN
		Assert.assertEquals(expectedProducts, result);
		BDDMockito.verify(productDao).getProductsByName(expectedName);
	}
	
	@Test
	public void testGetProductsByPriceIntervalShouldReturnRightWhenTheIntervalIsAppropriate() {
		//GIVEN
		double expectedFromPrice = 11.0;
		double expectedToPrice = 12.0;
		List<Product> expectedProducts = new ArrayList<Product>();
		BDDMockito.given(productDao.getProductsByPriceInterval(expectedFromPrice, expectedToPrice)).willReturn(expectedProducts);
		//WHEN
		List<Product> result = underTest.getProductsByPriceInterval(expectedFromPrice, expectedToPrice);
		//THEN
		Assert.assertEquals(expectedProducts, result);
		BDDMockito.verify(productDao).getProductsByPriceInterval(expectedFromPrice, expectedToPrice);
	}
	
	@Test
	public void testGetAllProductsShouldReturnProperly() {
		//GIVEN
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		product.setId(1L);
		products.add(product);
		BDDMockito.given(productDao.getAllProduct()).willReturn(products);
		//WHEN
		List<Product> result = underTest.getAllProducts();
		//THEN
		BDDMockito.verify(productDao).getAllProduct();
		Assert.assertEquals(products, result);
	}
	
	@Test
	public void testGetProductsByManufacturerShouldReturnProperly() {
		//GIVEN
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		product.setId(1L);
		products.add(product);
		BDDMockito.given(productDao.getProductsByManufacturer("manu")).willReturn(products);
		//WHEN
		List<Product> result = underTest.getProductsByManufacturer("manu");
		//THEN
		BDDMockito.verify(productDao).getProductsByManufacturer("manu");
		Assert.assertEquals(products, result);
	}
}
