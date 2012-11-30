package com.acme.fitness.products.simple;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.products.ProductDao;

public class SimpleProductServiceTest {
	
	private SimpleProductService underTest;

	@Mock
	private ProductDao productDao;
	
	@Before
	public void setUp(){
		underTest = new SimpleProductService();
		MockitoAnnotations.initMocks(this);
	}
}
