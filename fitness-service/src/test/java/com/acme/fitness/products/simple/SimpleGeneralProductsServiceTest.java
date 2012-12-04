package com.acme.fitness.products.simple;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.products.MembershipService;
import com.acme.fitness.products.ProductService;
import com.acme.fitness.products.TrainingService;

public class SimpleGeneralProductsServiceTest {
	private SimpleGeneralProductsService underTest;
	
	@Mock
	private ProductService productService;
	
	@Mock
	private MembershipService membershipService;
	
	@Mock
	private TrainingService trainingService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest=new SimpleGeneralProductsService();
	}
}
