package com.acme.fitness.products.simple;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.products.TrainingTypeDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;

public class SimpleTrainingTypeServiceTest {
	
	private SimpleTrainingTypeService underTest;
	
	@Mock
	private TrainingTypeDao trainingTypeDao;

	@Mock
	private User trainer;
	
	@Mock
	private TrainingType trainingTypeMock;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleTrainingTypeService(trainingTypeDao);
	}
	
	@Test
	public void testNewTrainingTypeShouldReturnTheRightValue() {
		//GIVEN
		TrainingType expected = new TrainingType();
		expected.setDetail("test");
		expected.setPrice(1.0);
		expected.setTrainer(trainer);
		//WHEN
		TrainingType result = underTest.newTrainingType(trainer, "test", 1.0);
		//THEN
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testSaveTrainingTypeShouldInvokeTheRightMethod() {
		//WHEN
		underTest.saveTrainingType(trainingTypeMock);
		//THEN
		BDDMockito.verify(trainingTypeDao).save(trainingTypeMock);
	}
	
	@Test
	public void testUpdateTrainingTypeShouldInvokeTheRightMethod() {
		//WHEN
		underTest.updateTrainingType(trainingTypeMock);
		//THEn
		BDDMockito.verify(trainingTypeDao).update(trainingTypeMock);
	}
	
	@Test
	public void testGetAllTrainingTypesShouldInvokeTheRightMethod() {
		//WHEN
		underTest.getAllTrainingTypes();
		//THEN
		BDDMockito.verify(trainingTypeDao).getAllTrainingTypes();
	}
	
	@Test
	public void testGetTrainingByIdShouldInvokeTheRightMethod() throws FitnessDaoException {
		//WHEN
		underTest.getTrainingTypeById(1L);
		//THEN
		BDDMockito.verify(trainingTypeDao).getTrainingTypeById(1L);
	}

	@Test
	public void testGetTrainingTypeByTrainerShouldInvokeTheRightMethod() throws FitnessDaoException {
		//WHEN
		underTest.getTrainingTypeByTrainer(trainer);
		//THEn
		BDDMockito.verify(trainingTypeDao).getTrainingTypeByTrainer(trainer);
	}
}
