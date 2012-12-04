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
		underTest = new SimpleTrainingService();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAddTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		User expectedClient = new User();
		User expectedTrainer = new User();
		Date expectedDate = new Date();
		Basket expectedBasket = new Basket();
		Training expectedTraining = new Training(expectedClient, expectedTrainer, expectedDate, false, 0, null, expectedBasket);
		underTest.setTrainingDao(trainingDao);
		//WHEN
		underTest.saveNewTraining(expectedTrainer, expectedClient, expectedDate, expectedBasket);
		//THEN
		BDDMockito.verify(trainingDao).save(expectedTraining);
	}
	
	@Test
	public void testDeleteTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		Training expectedTraining = new Training(new User(), new User(), new Date(), false, 0, null, new Basket());
		underTest.setTrainingDao(trainingDao);
		//WHEN
		underTest.deleteTraining(expectedTraining);
		//THEN
		BDDMockito.verify(trainingDao).delete(expectedTraining);
	}
	
	@Test
	public void testUpdateTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		Training expectedTraining = new Training(new User(), new User(), new Date(), false, 12, "review", new Basket());
		underTest.setTrainingDao(trainingDao);
		//WHEN
		underTest.updateTraining(expectedTraining);
		//THEN
		BDDMockito.verify(trainingDao).update(expectedTraining);
	}
	
	@Test
	public void testRecordTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		Training training = new Training(new User(), new User(), new Date(), false, 0, null, new Basket());
		int expectedBurnedCalories = 12;
		String expectedReview = "review";
		boolean expectedAnalyzed = true;
		Training expectedTraining = new Training(new User(), new User(), new Date(), false, 0, null, new Basket());
		expectedTraining.setBurnedCalories(expectedBurnedCalories);
		expectedTraining.setReview(expectedReview);
		expectedTraining.setAnalyzed(expectedAnalyzed);
		underTest.setTrainingDao(trainingDao);
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
		underTest.setTrainingDao(trainingDao);
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
		underTest.setTrainingDao(trainingDao);
		//WHEN
		List<Training> result = underTest.getTrainingsByClient(client);
		//THEN
		BDDMockito.verify(trainingDao).getTrainingsByClient(client);
		Assert.assertEquals(expectedTrainings, result);
	}
}
