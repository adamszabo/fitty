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
import com.acme.fitness.domain.products.MembershipType;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.ProductImage;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.MembershipService;
import com.acme.fitness.products.MembershipTypeService;
import com.acme.fitness.products.ProductImageService;
import com.acme.fitness.products.ProductService;
import com.acme.fitness.products.TrainingService;
import com.acme.fitness.products.TrainingTypeService;

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
	private MembershipType membershipType;

	@Mock
	private MembershipService membershipService;

	@Mock
	private TrainingService trainingService;

	@Mock
	private ProductImage productImage;

	@Mock
	private MembershipTypeService membershipTypeService;

	@Mock
	private ProductImageService imageService;
	
	@Mock
	private TrainingTypeService trainingTypeService;
	
	@Mock
	private TrainingType trainingType;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleGeneralProductsService(productService, membershipService, trainingService, membershipTypeService, imageService, trainingTypeService);
	}

	@Test
	public void testAddProductShouldReturnProperly() {
		// GIVEN
		Date date = new Date();
		BDDMockito.given(productService.addProduct(TEST_STRING, TEST_STRING, 0, TEST_STRING, date, productImage)).willReturn(product);
		// WHEN
		Product result = underTest.addProduct(TEST_STRING, TEST_STRING, 0, TEST_STRING, date, productImage);
		// THEN
		BDDMockito.verify(productService).addProduct(TEST_STRING, TEST_STRING, 0, TEST_STRING, date, productImage);
		Assert.assertEquals(product, result);
	}

	@Test
	public void testDeleteProductShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.deleteProduct(product);
		// THEN
		BDDMockito.verify(productService).deleteProduct(product);
	}

	@Test
	public void testUpdateProductShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.updateProduct(product);
		// THEN
		BDDMockito.verify(productService).updateProduct(product);
	}

	@Test
	public void testGetAllProductShouldReturnProperly() {
		// GIVEN
		List<Product> expected = new ArrayList<Product>();
		BDDMockito.given(productService.getAllProducts()).willReturn(expected);
		// WHEN
		List<Product> result = underTest.getAllProduct();
		// THEN
		BDDMockito.verify(productService).getAllProducts();
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetProductsByNameShouldReturnProperly() {
		// GIVEN
		List<Product> expected = new ArrayList<Product>();
		BDDMockito.given(productService.getProductsByName(Mockito.anyString())).willReturn(expected);
		// WHEN
		List<Product> result = underTest.getProductsByName(Mockito.anyString());
		// THEN
		BDDMockito.verify(productService).getProductsByName(Mockito.anyString());
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetProductsByManufacturerShouldReturnProperly() {
		// GIVEN
		List<Product> expected = new ArrayList<Product>();
		BDDMockito.given(productService.getProductsByManufacturer(Mockito.anyString())).willReturn(expected);
		// WHEN
		List<Product> result = underTest.getProductsByManufacturer(Mockito.anyString());
		// THEN
		BDDMockito.verify(productService).getProductsByManufacturer(Mockito.anyString());
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetProductsByPriceIntervalShouldReturnProperly() {
		// GIVEN
		List<Product> expected = new ArrayList<Product>();
		BDDMockito.given(productService.getProductsByPriceInterval(Mockito.anyDouble(), Mockito.anyDouble())).willReturn(expected);
		// WHEN
		List<Product> result = underTest.getProductsByPriceInterval(Mockito.anyDouble(), Mockito.anyDouble());
		// THEN
		BDDMockito.verify(productService).getProductsByPriceInterval(Mockito.anyDouble(), Mockito.anyDouble());
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGoOnHolidayShouldInvokeTheRightMethod() {
		// GIVEN
		User trainer = new User();
		Date date = new Date();
		// WHEN
		underTest.goOnHoliday(trainer, date);
		// THEN
		BDDMockito.verify(trainingService).goOnHoliday(trainer, date);
	}

	@Test
	public void testNewTrainingShouldReturnProperly() throws FitnessDaoException {
		// GIVEN
		Date date = new Date();
		BDDMockito.given(trainingService.newTraining(user, user, date)).willReturn(training);
		// WHEN
		Training result = underTest.newTraining(user, user, date);
		// THEN
		BDDMockito.verify(trainingService).newTraining(user, user, date);
		Assert.assertEquals(training, result);
	}

	@Test
	public void testDeleteTrainingShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.deleteTraining(training);
		// THEN
		BDDMockito.verify(trainingService).deleteTraining(training);
	}

	@Test
	public void testUpdateTrainingShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.updateTraining(training);
		// THEN
		BDDMockito.verify(trainingService).updateTraining(training);
	}

	@Test
	public void testRecordTrainingResultsShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.recordTrainingResults(training, 0, TEST_STRING);
		// THEN
		BDDMockito.verify(trainingService).recordTrainingResults(training, 0, TEST_STRING);
	}

	@Test
	public void testGetTrainingsByTrainerShouldReturnProperly() {
		// GIVEN
		List<Training> expected = new ArrayList<Training>();
		BDDMockito.given(trainingService.getTrainingsByTrainer(user)).willReturn(expected);
		// WHEN
		List<Training> result = underTest.getTrainingsByTrainer(user);
		// THEN
		BDDMockito.verify(trainingService).getTrainingsByTrainer(user);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetTrainingsByClientShouldReturnProperly() {
		// GIVEN
		List<Training> expected = new ArrayList<Training>();
		BDDMockito.given(trainingService.getTrainingsByClient(user)).willReturn(expected);
		// WHEN
		List<Training> result = underTest.getTrainingsByClient(user);
		// THEN
		BDDMockito.verify(trainingService).getTrainingsByClient(user);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetTrainingsByBasketShouldReturnProperly() {
		// GIVEN
		List<Training> expected = new ArrayList<Training>();
		BDDMockito.given(trainingService.getTrainingsByBasket(basket)).willReturn(expected);
		// WHEN
		List<Training> result = underTest.getTrainingsByBasket(basket);
		// THEN
		BDDMockito.verify(trainingService).getTrainingsByBasket(basket);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testIsDateReservedShouldReturnProperly() {
		// GIVEN
		User trainer = new User();
		Date date = new Date();
		BDDMockito.given(trainingService.isDateReserved(trainer, date)).willReturn(true);
		// WHEN
		boolean actual = underTest.isDateReserved(trainer, date);
		// THEN
		Assert.assertEquals(true, actual);
		BDDMockito.verify(trainingService).isDateReserved(trainer, date);
	}

	@Test
	public void testGetTrainingsOnWeekByTrainerShouldReturnProperly() {
		// GIVEN
		User trainer = new User();
		Date date = new Date();
		List<Training> trainings = new ArrayList<Training>();
		BDDMockito.given(trainingService.getTrainingsOnWeekByTrainer(trainer, date)).willReturn(trainings);
		// WHEN
		List<Training> actual = underTest.getTrainingsOnWeekByTrainer(trainer, date);
		// THEN
		Assert.assertEquals(trainings, actual);
		BDDMockito.verify(trainingService).getTrainingsOnWeekByTrainer(trainer, date);
	}

	@Test
	public void testNewMembershipShouldReturnProperly() {
		// GIVEN
		Date date = new Date();
		BDDMockito.given(membershipService.newMemberShip(true, TEST_STRING, 0, date, date, 10.0)).willReturn(membership);
		// WHEN
		Membership result = underTest.newMemberShip(true, TEST_STRING, 0, date, date, 10.0);
		// THEN
		BDDMockito.verify(membershipService).newMemberShip(true, TEST_STRING, 0, date, date, 10.0);
		Assert.assertEquals(membership, result);
	}

	@Test
	public void testDeleteMembershipShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.deleteMembership(membership);
		// THEN
		BDDMockito.verify(membershipService).deleteMembership(membership);
	}

	@Test
	public void testUpdateMembershipShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.updateMembership(membership);
		// THEN
		BDDMockito.verify(membershipService).updateMembership(membership);
	}

	@Test
	public void testIsValidMembershipShouldReturnProperly() {
		// GIVEN
		Date date = new Date();
		// WHEN
		underTest.isValid(membership, date);
		// THEN
		BDDMockito.verify(membershipService).isValid(membership, date);
	}

	@Test
	public void testGetMembershipByIdWhenHasResultShouldReturnProperly() throws FitnessDaoException {
		// GIVEN
		BDDMockito.given(membershipService.getMembershipById(Mockito.anyLong())).willReturn(membership);
		// WHEN
		Membership result = underTest.getMembershipById(Mockito.anyLong());
		// THEN
		BDDMockito.verify(membershipService).getMembershipById(Mockito.anyLong());
		Assert.assertEquals(membership, result);
	}

	@Test(expected = FitnessDaoException.class)
	public void testGetMembershipByIdWhenDoesNotHaveResultShouldReturnProperly() throws FitnessDaoException {
		// GIVEN
		BDDMockito.given(membershipService.getMembershipById(Mockito.anyLong())).willThrow(new FitnessDaoException());
		// WHEN
		Membership result = underTest.getMembershipById(Mockito.anyLong());
		// THEN
		BDDMockito.verify(membershipService).getMembershipById(Mockito.anyLong());
		Assert.assertEquals(membership, result);
	}

	@Test
	public void testGetMembershipByBasketShouldReturnProperly() {
		// GIVEN
		List<Membership> expected = new ArrayList<Membership>();
		BDDMockito.given(membershipService.getMembershipByBasket(basket)).willReturn(expected);
		// WHEN
		List<Membership> result = underTest.getMembershipByBasket(basket);
		// THEN
		BDDMockito.verify(membershipService).getMembershipByBasket(basket);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetMembershipByUserShouldReturnProperly() {
		// GIVEN
		List<Membership> expected = new ArrayList<Membership>();
		BDDMockito.given(membershipService.getMembershipByUser(user)).willReturn(expected);
		// WHEN
		List<Membership> result = underTest.getMembershipByUser(user);
		// THEN
		BDDMockito.verify(membershipService).getMembershipByUser(user);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testMembershipIncreaseClientEntriesShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.increaseClientEntries(membership);
		// THEN
		BDDMockito.verify(membershipService).increaseClientEntries(membership);
	}

	@Test
	public void testNewMembershipTypeShouldReturnProperly() {
		// GIVEN
		BDDMockito.given(membershipTypeService.newMembershipType(TEST_STRING, true, 1, 1, 1.0)).willReturn(membershipType);
		// WHEN
		MembershipType result = underTest.newMembershipType(TEST_STRING, true, 1, 1, 1.0);
		// THEN
		BDDMockito.verify(membershipTypeService).newMembershipType(TEST_STRING, true, 1, 1, 1.0);
		Assert.assertEquals(membershipType, result);
	}

	@Test
	public void testSaveMembershipTypeShouldInvokeTheRightMethod() {
		// WHEN
		underTest.saveMembershipType(membershipType);
		// THEN
		BDDMockito.verify(membershipTypeService).saveMembershipType(membershipType);
	}

	@Test
	public void testDeleteMembershipTypeShouldInvokeTheRightMethod() {
		// WHEN
		underTest.deleteMembershipType(membershipType);
		// THEN
		BDDMockito.verify(membershipTypeService).deleteMembershipType(membershipType);
	}

	@Test
	public void testUpdateMembershipTypeShouldInvokeTheRightMethod() {
		// WHEN
		underTest.updateMembershipType(membershipType);
		// THEn
		BDDMockito.verify(membershipTypeService).updateMembershipType(membershipType);
	}

	@Test
	public void testGetMembershipTypeByIdShouldReturnProperlyWhenTheIdExists() throws FitnessDaoException {
		// GIVEN
		BDDMockito.given(membershipTypeService.getMembershipTypeById(1L)).willReturn(membershipType);
		// WHEN
		MembershipType result = underTest.getMembershipTypeById(1L);
		// THEN
		Assert.assertEquals(result, membershipType);
		BDDMockito.verify(membershipTypeService).getMembershipTypeById(1L);
	}

	@Test(expected = FitnessDaoException.class)
	public void testGetMembershipTypeByIdShouldThrowExpcetionWhenTheIdDoesntExist() throws FitnessDaoException {
		// GIVEN
		BDDMockito.given(membershipTypeService.getMembershipTypeById(1L)).willThrow(new FitnessDaoException());
		// WHEN
		underTest.getMembershipTypeById(1L);
		// THEN
		BDDMockito.verify(membershipTypeService).getMembershipTypeById(1L);
	}

	@Test
	public void testGetAllMembershipTypesShouldReturnProperly() {
		// GIVEN
		List<MembershipType> expected = new ArrayList<MembershipType>();
		expected.add(membershipType);
		BDDMockito.given(membershipTypeService.getAllMembershipTypes()).willReturn(expected);
		// WHEN
		List<MembershipType> result = underTest.getAllMembershipTypes();
		// THEN
		BDDMockito.verify(membershipTypeService).getAllMembershipTypes();
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testValidMembershipsByUserShouldReturnProperly() {
		// GIVEN
		Date date = new Date();
		List<Membership> expected = new ArrayList<Membership>();
		BDDMockito.given(membershipService.getValidMembershipsByUser(user, date)).willReturn(expected);
		// WHEN
		List<Membership> result = underTest.getValidMembershipsByUser(user, date);
		// THEN
		BDDMockito.verify(membershipService).getValidMembershipsByUser(user, date);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetAllProductImageShouldReturnProperly() {
		// GIVEN
		List<ProductImage> expected = new ArrayList<ProductImage>();
		BDDMockito.given(imageService.getAllProductImages()).willReturn(expected);
		// WHEN
		List<ProductImage> result = underTest.getAllProductImage();
		// THEN
		BDDMockito.verify(imageService).getAllProductImages();
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetProductImageByIdShouldReturnProperly() throws FitnessDaoException {
		// GIVEN
		ProductImage expected = new ProductImage();
		BDDMockito.given(imageService.getProductImageById(1L)).willReturn(expected);
		// WHEN
		ProductImage result = underTest.getProductImageById(1L);
		// THEN
		BDDMockito.verify(imageService).getProductImageById(1L);
		Assert.assertEquals(expected, result);
	}

	@Test(expected = FitnessDaoException.class)
	public void testGetProductImageByIdShouldThrowExceptionWhenTheImageDoesNotExists() throws FitnessDaoException {
		// GIVEN
		ProductImage expected = new ProductImage();
		BDDMockito.given(imageService.getProductImageById(1L)).willThrow(new FitnessDaoException());
		// WHEN
		ProductImage result = underTest.getProductImageById(1L);
		// THEN
		BDDMockito.verify(imageService).getProductImageById(1L);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGoOnHolidayToAllDayShouldReturnProperly() {
		// GIVEN
		Date date=new Date();
		// WHEN
		underTest.goOnHolidayToAllDay(user, date);
		// THEN
		BDDMockito.verify(trainingService, BDDMockito.times(1)).goOnHolidayToAllDay(user, date);
	}
	
	@Test
	public void testNewTrainingShouldInvokeTheRightMethod() {
		//WHEN
		underTest.newTrainingType(user, "test", 1.0);
		//THEN
		BDDMockito.verify(trainingTypeService).newTrainingType(user, "test", 1.0);
	}
	
	@Test
	public void testSaveTrainingTypeShouldInvokeTheRightMethod() {
		//WHEN
		underTest.saveTrainingType(trainingType);
		//THEn
		BDDMockito.verify(trainingTypeService).saveTrainingType(trainingType);
	}
	
	@Test
	public void testGetAllTrainingTypes() {
		//WHEN
		underTest.getAllTrainingTypes();
		//THEN
		BDDMockito.verify(trainingTypeService).getAllTrainingTypes();
	}
	
	@Test
	public void testGetTrainingTypeByIdShouldInvokeTheRightMethod() throws FitnessDaoException {
		//WHEN
		underTest.getTrainingTypeById(1L);
		//THEN
		BDDMockito.verify(trainingTypeService).getTrainingTypeById(1L);
	}
	
	@Test
	public void testGetTrainingTypeByTrainerShouldInvokeTheRightMethod() throws FitnessDaoException {
		//WHEN
		underTest.getTrainingTypeByTrainer(user);
		//THEN
		BDDMockito.verify(trainingTypeService).getTrainingTypeByTrainer(user);
	}
	
	@Test
	public void testUpdateTrainingTypeShouldInvokeTheRightMethod() {
		//WHEN
		underTest.updateTrainingType(trainingType);
		//THEN
		BDDMockito.verify(trainingTypeService).updateTrainingType(trainingType);
	}
}
