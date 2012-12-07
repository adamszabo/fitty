package com.acme.fitness.products.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.MembershipService;
import com.acme.fitness.products.ProductService;
import com.acme.fitness.products.TrainingService;

public class SimpleGeneralProductsServiceTest {
	private static final String TEST_STRING = "TESTSTRING";

	private SimpleGeneralProductsService underTest;
	
	@Mock
	private ProductService productService;
	
	@Mock
	private Product product;
	
	@Mock
	private User user;
	
	@Mock
	private Basket basket;
	
	@Mock
	private Membership membership;
	
	@Mock
	private Training training;
	
	@Mock
	private MembershipService membershipService;
	
	@Mock
	private TrainingService trainingService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest=new SimpleGeneralProductsService();
	}
	
	@Test
	public void testAddProductShouldReturnProperly(){
		// GIVEN
		underTest.setProductService(productService);
		Date date=new Date();
		BDDMockito.given(productService.addProduct(TEST_STRING, TEST_STRING, 0, TEST_STRING, date)).willReturn(product);
		// WHEN
		Product result=underTest.addProduct(TEST_STRING, TEST_STRING, 0, TEST_STRING, date);
		// THEN
		BDDMockito.verify(productService).addProduct(TEST_STRING, TEST_STRING, 0, TEST_STRING, date);
		Assert.assertEquals(product, result);
	}
	
	@Test
	public void testDeleteProductShouldReturnProperly(){
		// GIVEN
		underTest.setProductService(productService);
		// WHEN
		underTest.deleteProduct(product);
		// THEN
		BDDMockito.verify(productService).deleteProduct(product);
	}
	
	@Test
	public void testUpdateProductShouldReturnProperly(){
		// GIVEN
		underTest.setProductService(productService);
		// WHEN
		underTest.updateProduct(product);
		// THEN
		BDDMockito.verify(productService).updateProduct(product);
	}
	
	@Test
	public void testGetAllProductShouldReturnProperly(){
		// GIVEN
		underTest.setProductService(productService);
		List<Product> expected=new ArrayList<Product>();
		BDDMockito.given(productService.getAllProducts()).willReturn(expected);
		// WHEN
		List<Product> result=underTest.getAllProduct();
		// THEN
		BDDMockito.verify(productService).getAllProducts();
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetProductsByNameShouldReturnProperly(){
		// GIVEN
		underTest.setProductService(productService);
		List<Product> expected=new ArrayList<Product>();
		BDDMockito.given(productService.getProductsByName(Mockito.anyString())).willReturn(expected);
		// WHEN
		List<Product> result=underTest.getProductsByName(Mockito.anyString());
		// THEN
		BDDMockito.verify(productService).getProductsByName(Mockito.anyString());
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetProductsByManufacturerShouldReturnProperly(){
		// GIVEN
		underTest.setProductService(productService);
		List<Product> expected=new ArrayList<Product>();
		BDDMockito.given(productService.getProductsByManufacturer(Mockito.anyString())).willReturn(expected);
		// WHEN
		List<Product> result=underTest.getProductsByManufacturer(Mockito.anyString());
		// THEN
		BDDMockito.verify(productService).getProductsByManufacturer(Mockito.anyString());
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetProductsByPriceIntervalShouldReturnProperly(){
		// GIVEN
		underTest.setProductService(productService);
		List<Product> expected=new ArrayList<Product>();
		BDDMockito.given(productService.getProductsByPriceInterval(Mockito.anyDouble(), Mockito.anyDouble())).willReturn(expected);
		// WHEN
		List<Product> result=underTest.getProductsByPriceInterval(Mockito.anyDouble(), Mockito.anyDouble());
		// THEN
		BDDMockito.verify(productService).getProductsByPriceInterval(Mockito.anyDouble(), Mockito.anyDouble());
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testNewTrainingShouldReturnProperly(){
		// GIVEN
		underTest.setTrainingService(trainingService);
		Date date=new Date();
		BDDMockito.given(trainingService.newTraining(user, user, date)).willReturn(training);
		// WHEN
		Training result=underTest.newTraining(user, user, date);
		// THEN
		BDDMockito.verify(trainingService).newTraining(user, user, date);
		Assert.assertEquals(training, result);
	}
	
	@Test
	public void testDeleteTrainingShouldReturnProperly(){
		// GIVEN
		underTest.setTrainingService(trainingService);
		// WHEN
		underTest.deleteTraining(training);
		// THEN
		BDDMockito.verify(trainingService).deleteTraining(training);
	}
	
	@Test
	public void testUpdateTrainingShouldReturnProperly(){
		// GIVEN
		underTest.setTrainingService(trainingService);
		// WHEN
		underTest.updateTraining(training);
		// THEN
		BDDMockito.verify(trainingService).updateTraining(training);
	}
	
	@Test
	public void testRecordTrainingResultsShouldReturnProperly(){
		// GIVEN
		underTest.setTrainingService(trainingService);
		// WHEN
		underTest.recordTrainingResults(training, 0, TEST_STRING);
		// THEN
		BDDMockito.verify(trainingService).recordTrainingResults(training, 0, TEST_STRING);
	}
	
	@Test
	public void testGetTrainingsByTrainerShouldReturnProperly(){
		// GIVEN
		underTest.setTrainingService(trainingService);
		List<Training> expected=new ArrayList<Training>();
		BDDMockito.given(trainingService.getTrainingsByTrainer(user)).willReturn(expected);
		// WHEN
		List<Training> result=underTest.getTrainingsByTrainer(user);
		// THEN
		BDDMockito.verify(trainingService).getTrainingsByTrainer(user);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetTrainingsByClientShouldReturnProperly(){
		// GIVEN
		underTest.setTrainingService(trainingService);
		List<Training> expected=new ArrayList<Training>();
		BDDMockito.given(trainingService.getTrainingsByClient(user)).willReturn(expected);
		// WHEN
		List<Training> result=underTest.getTrainingsByClient(user);
		// THEN
		BDDMockito.verify(trainingService).getTrainingsByClient(user);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetTrainingsByBasketShouldReturnProperly(){
		// GIVEN
		underTest.setTrainingService(trainingService);
		List<Training> expected=new ArrayList<Training>();
		BDDMockito.given(trainingService.getTrainingsByBasket(basket)).willReturn(expected);
		// WHEN
		List<Training> result=underTest.getTrainingsByBasket(basket);
		// THEN
		BDDMockito.verify(trainingService).getTrainingsByBasket(basket);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testNewMembershipShouldReturnProperly(){
		// GIVEN
		underTest.setMembershipService(membershipService);
		Date date=new Date();
		BDDMockito.given(membershipService.newMemberShip(TEST_STRING, 0, date, 0.0)).willReturn(membership);
		// WHEN
		Membership result=underTest.newMemberShip(TEST_STRING, 0, date, 0.0);
		// THEN
		BDDMockito.verify(membershipService).newMemberShip(TEST_STRING, 0, date, 0.0);
		Assert.assertEquals(membership, result);
	}
	
	@Test
	public void testDeleteMembershipShouldReturnProperly(){
		// GIVEN
		underTest.setMembershipService(membershipService);
		// WHEN
		underTest.deleteMembership(membership);
		// THEN
		BDDMockito.verify(membershipService).deleteMembership(membership);
	}
	
	@Test
	public void testUpdateMembershipShouldReturnProperly(){
		// GIVEN
		underTest.setMembershipService(membershipService);
		// WHEN
		underTest.updateMembership(membership);
		// THEN
		BDDMockito.verify(membershipService).updateMembership(membership);
	}
	
	@Test
	public void testIsValidMembershipShouldReturnProperly(){
		// GIVEN
		underTest.setMembershipService(membershipService);
		// WHEN
		underTest.isValid(membership);
		// THEN
		BDDMockito.verify(membershipService).isValid(membership);
	}
	
	@Test
	public void testGetMembershipByIdWhenHasResultShouldReturnProperly() throws FitnessDaoException{
		// GIVEN
		underTest.setMembershipService(membershipService);
		BDDMockito.given(membershipService.getMembershipById(Mockito.anyLong())).willReturn(membership);
		// WHEN
		Membership result=underTest.getMembershipById(Mockito.anyLong());
		// THEN
		BDDMockito.verify(membershipService).getMembershipById(Mockito.anyLong());
		Assert.assertEquals(membership, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetMembershipByIdWhenDoesNotHaveResultShouldReturnProperly() throws FitnessDaoException{
		// GIVEN
		underTest.setMembershipService(membershipService);
		BDDMockito.given(membershipService.getMembershipById(Mockito.anyLong())).willThrow(new FitnessDaoException());
		// WHEN
		Membership result=underTest.getMembershipById(Mockito.anyLong());
		// THEN
		BDDMockito.verify(membershipService).getMembershipById(Mockito.anyLong());
		Assert.assertEquals(membership, result);
	}
	
	@Test
	public void testGetMembershipByBasketShouldReturnProperly() {
		// GIVEN
		underTest.setMembershipService(membershipService);
		List<Membership> expected=new ArrayList<Membership>();
		BDDMockito.given(membershipService.getMembershipByBasket(basket)).willReturn(expected);
		// WHEN
		List<Membership> result=underTest.getMembershipByBasket(basket);
		// THEN
		BDDMockito.verify(membershipService).getMembershipByBasket(basket);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetMembershipByUserShouldReturnProperly() {
		// GIVEN
		underTest.setMembershipService(membershipService);
		List<Membership> expected=new ArrayList<Membership>();
		BDDMockito.given(membershipService.getMembershipByUser(user)).willReturn(expected);
		// WHEN
		List<Membership> result=underTest.getMembershipByUser(user);
		// THEN
		BDDMockito.verify(membershipService).getMembershipByUser(user);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testMembershipIncreaseClientEntriesShouldReturnProperly() {
		// GIVEN
		underTest.setMembershipService(membershipService);
		// WHEN
		underTest.increaseClientEntries(membership);
		// THEN
		BDDMockito.verify(membershipService).increaseClientEntries(membership);
	}
	
	@Test
	public void testProductServicesGetterAndSetterBehaviour() {
		//WHEN
		underTest.setProductService(productService);
		//THEN
		Assert.assertEquals(productService, underTest.getProductService());
	}
	
	@Test
	public void testMembershipServicesGetterAndSetterBehaviour() {
		//WHEN
		underTest.setMembershipService(membershipService);
		//THEN
		Assert.assertEquals(membershipService, underTest.getMembershipService());
	}

	@Test
	public void testTrainingServicesGetterAndSetterBehaviour() {
		//WHEn
		underTest.setTrainingService(trainingService);
		//THEN
		Assert.assertEquals(trainingService, underTest.getTrainingService());
	}
}
