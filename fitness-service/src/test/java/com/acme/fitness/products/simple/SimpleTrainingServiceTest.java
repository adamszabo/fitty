package com.acme.fitness.products.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.products.TrainingDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public class SimpleTrainingServiceTest {
	
	private SimpleTrainingService underTest;
	
	@Mock
	private TrainingDao trainingDao;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleTrainingService(trainingDao);
	}
	
	@Test
	public void testNewTrainingShouldInvokeTheMethodRight() {
		// GIVEN
		User expectedClient = new User();
		expectedClient.setId(1L);
		User expectedTrainer = new User();
		expectedTrainer.setId(2L);
		Date expectedDate = new Date();
		Training expectedTraining = new Training(expectedTrainer, expectedClient, expectedDate, false, 0, null, null);
		// WHEN
		Training result = underTest.newTraining(expectedTrainer, expectedClient, expectedDate);
		// THEN
		Assert.assertEquals(expectedTraining, result);
	}

	@Test
	public void testSaveTrainingShouldInvokeTheRightMethod() {
		// GIVEN
		Basket basket = new Basket();
		Training training = new Training();
		Training expectedTraining = training;
		expectedTraining.setBasket(basket);
		// WHEN
		underTest.saveTraining(basket, training);
		// THEN
		BDDMockito.verify(trainingDao).save(expectedTraining);
	}
	
	@Test
	public void testAddTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		User expectedClient = new User();
		User expectedTrainer = new User();
		Date expectedDate = new Date();
		Basket expectedBasket = new Basket();
		Training expectedTraining = new Training(expectedTrainer, expectedClient, expectedDate, false, 0, null, expectedBasket);
		//WHEN
		underTest.saveNewTraining(expectedTrainer, expectedClient, expectedDate, expectedBasket);
		//THEN
		BDDMockito.verify(trainingDao).save(expectedTraining);
	}
	
	@Test
	public void testDeleteTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		Training expectedTraining = new Training(new User(), new User(), new Date(), false, 0, null, new Basket());
		//WHEN
		underTest.deleteTraining(expectedTraining);
		//THEN
		BDDMockito.verify(trainingDao).delete(expectedTraining);
	}
	
	@Test
	public void testUpdateTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		Training expectedTraining = new Training(new User(), new User(), new Date(), false, 12, "review", new Basket());
		//WHEN
		underTest.updateTraining(expectedTraining);
		//THEN
		BDDMockito.verify(trainingDao).update(expectedTraining);
	}
	
	@Test
	public void testRecordTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		User user=new User();
		Training training = new Training(user, user, new Date(), false, 0, null, new Basket());
		int expectedBurnedCalories = 12;
		String expectedReview = "review";
		boolean expectedAnalyzed = true;
		Training expectedTraining = new Training(user, user, new Date(), false, 0, null, new Basket());
		expectedTraining.setBurnedCalories(expectedBurnedCalories);
		expectedTraining.setReview(expectedReview);
		expectedTraining.setAnalyzed(expectedAnalyzed);
		//WHEN
		underTest.recordTrainingResults(training, expectedBurnedCalories, expectedReview);
		//THEN
		
		BDDMockito.verify(trainingDao).update(expectedTraining);
	}
	
	@Test
	public void testGetTrainingsByTrainerShouldReturnRight() {
		//GIVEN
		List<Training> expectedTrainings = new ArrayList<Training>();
		User trainer = new User();
		BDDMockito.given(trainingDao.getTrainingsByTrainer(trainer)).willReturn(expectedTrainings);
		//WHEN
		List<Training> result = underTest.getTrainingsByTrainer(trainer);
		//THEN
		BDDMockito.verify(trainingDao).getTrainingsByTrainer(trainer);
		Assert.assertEquals(expectedTrainings, result);
	}
	
	@Test
	public void testGetTrainingsByClientShouldReturnRight() {
		//GIVEN
		List<Training> expectedTrainings = new ArrayList<Training>();
		User client = new User();
		BDDMockito.given(trainingDao.getTrainingsByClient(client)).willReturn(expectedTrainings);
		//WHEN
		List<Training> result = underTest.getTrainingsByClient(client);
		//THEN
		BDDMockito.verify(trainingDao).getTrainingsByClient(client);
		Assert.assertEquals(expectedTrainings, result);
	}
	
	@Test
	public void testGetTrainingsByBasketShouldReturnProperly() {
		//GIVEN
		Basket basket = new Basket();
		List<Training> expectedTrainings = new ArrayList<Training>();
		BDDMockito.given(trainingDao.getTrainingsByOrder(basket)).willReturn(expectedTrainings);
		//WHEN
		List<Training> result = underTest.getTrainingsByBasket(basket);
		//THEN
		Assert.assertEquals(expectedTrainings, result);
		BDDMockito.verify(trainingDao).getTrainingsByOrder(basket);
	}
}
