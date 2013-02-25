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
import com.acme.fitness.dao.products.TrainingTypeDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;

public class SimpleTrainingServiceTest {

	private SimpleTrainingService underTest;

	@Mock
	private TrainingDao trainingDao;

	@Mock
	private TrainingTypeDao trainingTypeDao;
	
	@Mock
	private TrainingType trainingType;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleTrainingService(trainingDao, trainingTypeDao);
	}

	@Test
	public void testNewTrainingShouldInvokeTheMethodRight() throws FitnessDaoException {
		// GIVEN
		User expectedClient = new User();
		expectedClient.setId(1L);
		User expectedTrainer = new User();
		expectedTrainer.setId(2L);
		Date expectedDate = new Date();
		Training expectedTraining = new Training(expectedTrainer, expectedClient, expectedDate, false, 0, null, null, 1.0);
		BDDMockito.given(trainingTypeDao.getTrainingTypeByTrainer(expectedTrainer)).willReturn(trainingType);
		BDDMockito.given(trainingType.getPrice()).willReturn(1.0);
		// WHEN
		Training result = underTest.newTraining(expectedTrainer, expectedClient, expectedDate);
		// THEN
		Assert.assertEquals(expectedTraining, result);
		BDDMockito.verify(trainingTypeDao).getTrainingTypeByTrainer(expectedTrainer);
		BDDMockito.verify(trainingType).getPrice();
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testNewTrainingShouldThrowExceptionWhenTheTrainerDoesntHaveTrainerType() throws FitnessDaoException {
		// GIVEN
		User expectedClient = new User();
		expectedClient.setId(1L);
		User expectedTrainer = new User();
		expectedTrainer.setId(2L);
		Date expectedDate = new Date();
		Training expectedTraining = new Training(expectedTrainer, expectedClient, expectedDate, false, 0, null, null, 1.0);
		BDDMockito.given(trainingTypeDao.getTrainingTypeByTrainer(expectedTrainer)).willThrow(new FitnessDaoException());
		// WHEN
		Training result = underTest.newTraining(expectedTrainer, expectedClient, expectedDate);
		// THEN
		Assert.assertEquals(expectedTraining, result);
		BDDMockito.verify(trainingTypeDao).getTrainingTypeByTrainer(expectedTrainer);
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
	public void testAddTrainingShouldInvokeTheMethodRight() throws FitnessDaoException {
		// GIVEN
		User expectedClient = new User();
		User expectedTrainer = new User();
		Date expectedDate = new Date();
		Basket expectedBasket = new Basket();
		Training expectedTraining = new Training(expectedTrainer, expectedClient, expectedDate, false, 0, null, expectedBasket, 1.0);
		BDDMockito.given(trainingTypeDao.getTrainingTypeByTrainer(expectedTrainer)).willReturn(trainingType);
		BDDMockito.given(trainingType.getPrice()).willReturn(1.0);
		// WHEN
		underTest.saveNewTraining(expectedTrainer, expectedClient, expectedDate, expectedBasket);
		// THEN
		BDDMockito.verify(trainingDao).save(expectedTraining);
		BDDMockito.verify(trainingTypeDao).getTrainingTypeByTrainer(expectedTrainer);
		BDDMockito.verify(trainingType).getPrice();
	}

	@Test
	public void testDeleteTrainingShouldInvokeTheMethodRight() {
		// GIVEN
		Training expectedTraining = new Training(new User(), new User(), new Date(), false, 0, null, new Basket(), 1.0);
		// WHEN
		underTest.deleteTraining(expectedTraining);
		// THEN
		BDDMockito.verify(trainingDao).delete(expectedTraining);
	}

	@Test
	public void testUpdateTrainingShouldInvokeTheMethodRight() {
		// GIVEN
		Training expectedTraining = new Training(new User(), new User(), new Date(), false, 12, "review", new Basket(), 1.0);
		// WHEN
		underTest.updateTraining(expectedTraining);
		// THEN
		BDDMockito.verify(trainingDao).update(expectedTraining);
	}

	@Test
	public void testRecordTrainingShouldInvokeTheMethodRight() {
		// GIVEN
		User user = new User();
		Training training = new Training(user, user, new Date(), false, 0, null, new Basket(), 1.0);
		int expectedBurnedCalories = 12;
		String expectedReview = "review";
		boolean expectedAnalyzed = true;
		Training expectedTraining = new Training(user, user, new Date(), false, 0, null, new Basket(), 1.0);
		expectedTraining.setBurnedCalories(expectedBurnedCalories);
		expectedTraining.setReview(expectedReview);
		expectedTraining.setAnalyzed(expectedAnalyzed);
		// WHEN
		underTest.recordTrainingResults(training, expectedBurnedCalories, expectedReview);
		// THEN

		BDDMockito.verify(trainingDao).update(expectedTraining);
	}

	@Test
	public void testGetTrainingsByTrainerShouldReturnRight() {
		// GIVEN
		List<Training> expectedTrainings = new ArrayList<Training>();
		User trainer = new User();
		BDDMockito.given(trainingDao.getTrainingsByTrainer(trainer)).willReturn(expectedTrainings);
		// WHEN
		List<Training> result = underTest.getTrainingsByTrainer(trainer);
		// THEN
		BDDMockito.verify(trainingDao).getTrainingsByTrainer(trainer);
		Assert.assertEquals(expectedTrainings, result);
	}

	@Test
	public void testGetTrainingsByClientShouldReturnRight() {
		// GIVEN
		List<Training> expectedTrainings = new ArrayList<Training>();
		User client = new User();
		BDDMockito.given(trainingDao.getTrainingsByClient(client)).willReturn(expectedTrainings);
		// WHEN
		List<Training> result = underTest.getTrainingsByClient(client);
		// THEN
		BDDMockito.verify(trainingDao).getTrainingsByClient(client);
		Assert.assertEquals(expectedTrainings, result);
	}

	@Test
	public void testGetTrainingsByBasketShouldReturnProperly() {
		// GIVEN
		Basket basket = new Basket();
		List<Training> expectedTrainings = new ArrayList<Training>();
		BDDMockito.given(trainingDao.getTrainingsByOrder(basket)).willReturn(expectedTrainings);
		// WHEN
		List<Training> result = underTest.getTrainingsByBasket(basket);
		// THEN
		Assert.assertEquals(expectedTrainings, result);
		BDDMockito.verify(trainingDao).getTrainingsByOrder(basket);
	}

	@Test
	public void testIsDateReservedShouldReturnProperly() {
		// GIVEN
		User trainer = new User();
		Date date = new Date();
		BDDMockito.given(trainingDao.isDateReserved(trainer, date)).willReturn(true);
		// WHEN
		boolean actual = underTest.isDateReserved(trainer, date);
		// THEN
		Assert.assertEquals(true, actual);
		BDDMockito.verify(trainingDao).isDateReserved(trainer, date);
	}

	@Test
	public void testGetTrainingsOnWeekByTrainerShouldReturnProperly() {
		// GIVEN
		User trainer = new User();
		Date date = new Date();
		List<Training> trainings = new ArrayList<Training>();
		BDDMockito.given(trainingDao.getTrainingsOnWeekByTrainer(trainer, date)).willReturn(trainings);
		// WHEN
		List<Training> actual = underTest.getTrainingsOnWeekByTrainer(trainer, date);
		// THEN
		Assert.assertEquals(trainings, actual);
		BDDMockito.verify(trainingDao).getTrainingsOnWeekByTrainer(trainer, date);
	}

	@Test
	public void testGoOnHoildayShouldInvokeTheRightMethod() {
		// GIVEN
		User trainer = new User();
		Date date = new Date();
		Training expectedTraining = new Training(trainer, trainer, date, false, 0, null, null, 0.0);
		// WHEN
		underTest.goOnHoliday(trainer, date);
		// THEN
		BDDMockito.verify(trainingDao).save(expectedTraining);
	}

	@Test
	public void testGoOnHolidayToAllDayShouldInvoceSaveNewTrainingsWhenTrainingsDateIsFree() {
		// GIVEN
		User trainer = new User();
		Date date = new Date();
		BDDMockito.given(trainingDao.isDateReserved((User) BDDMockito.anyObject(), (Date) BDDMockito.anyObject())).willReturn(false);
		// WHEN
		underTest.goOnHolidayToAllDay(trainer, date);
		// THEN
		BDDMockito.verify(trainingDao,BDDMockito.times(24)).isDateReserved((User) BDDMockito.anyObject(), (Date) BDDMockito.anyObject());
		BDDMockito.verify(trainingDao,BDDMockito.times(24)).save((Training) BDDMockito.anyObject());
	}
	
	@Test
	public void testGoOnHolidayToAllDayShouldNotInvoceSaveNewTrainingsWhenTrainingsDateIsReserved() {
		// GIVEN
		User trainer = new User();
		Date date = new Date();
		BDDMockito.given(trainingDao.isDateReserved((User) BDDMockito.anyObject(), (Date) BDDMockito.anyObject())).willReturn(true);
		// WHEN
		underTest.goOnHolidayToAllDay(trainer, date);
		// THEN
		BDDMockito.verify(trainingDao,BDDMockito.times(24)).isDateReserved((User) BDDMockito.anyObject(), (Date) BDDMockito.anyObject());
		BDDMockito.verify(trainingDao,BDDMockito.times(0)).save((Training) BDDMockito.anyObject());
	}
}
