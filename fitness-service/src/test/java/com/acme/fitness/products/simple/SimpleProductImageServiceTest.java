package com.acme.fitness.products.simple;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.products.ProductImageDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.ProductImage;

public class SimpleProductImageServiceTest {
	private SimpleProductImageService underTest;
	
	@Mock
	private ProductImageDao imageDao;
	
	@Mock
	private ProductImage image;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest=new SimpleProductImageService(imageDao);
	}
	
	@Test
	public void testSaveProductImageShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.saveProductImage(image);
		//THEN
		BDDMockito.verify(imageDao).save(image);
	}
	
	@Test
	public void testUpdateProductImageShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.updateProductImage(image);
		//THEN
		BDDMockito.verify(imageDao).update(image);
	}
	
	@Test
	public void testDeleteProductImageShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.deleteProductImage(image);
		//THEN
		BDDMockito.verify(imageDao).delete(image);
	}
	
	@Test
	public void testGetAllProductImagesShouldReturnProperly(){
		//GIVEN
		List<ProductImage> expected=new ArrayList<ProductImage>();
		//WHEN
		List<ProductImage> result=underTest.getAllProductImages();
		//THEN
		BDDMockito.verify(imageDao).getAllProductImages();
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetProductImageByIdShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		BDDMockito.given(imageDao.getProductImageById(1L)).willReturn(image);
		//WHEN
		ProductImage result=underTest.getProductImageById(1L);
		//THEN
		BDDMockito.verify(imageDao).getProductImageById(1L);
		Assert.assertEquals(image, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetProductImageByIdShouldThrowExcpetionWhenTheObjectDoesNotExists() throws FitnessDaoException{
		//GIVEN
		BDDMockito.given(imageDao.getProductImageById(1L)).willThrow(new FitnessDaoException());
		//WHEN
		underTest.getProductImageById(1L);
		//THEN
		BDDMockito.verify(imageDao).getProductImageById(1L);
	}
}
