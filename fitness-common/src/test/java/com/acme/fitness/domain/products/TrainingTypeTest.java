package com.acme.fitness.domain.products;

import junit.framework.Assert;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.users.User;

public class TrainingTypeTest {
	
	private TrainingType underTest;
	
	@Mock
	private User user;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new TrainingType();
	}
	
	@Test
	public void testGetterAndSetterOfTrainer() {
		underTest.setTrainer(user);
		Assert.assertEquals(user, underTest.getTrainer());
	}
	
	@Test
	public void testGetterAndSetterOfDetail() {
		underTest.setDetail("test");
		Assert.assertEquals("test", underTest.getDetail());
	}
	
	@Test
	public void testGetterAndSetterOfPrice() {
		underTest.setPrice(1.0);
		Assert.assertEquals(1.0, underTest.getPrice());
	}
	
	@Test
	public void testEqualsWithEqualsVerifier() {
		EqualsVerifier.forClass(TrainingType.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
	
	@Test
	public void testTrainingTypeConstructorWithArguments() {
		//GIVEN
		TrainingType expected = new TrainingType();
		expected.setDetail("test");
		expected.setTrainer(user);
		expected.setPrice(1.0);
		//WHEN
		underTest = new TrainingType(user, "test", 1.0);
		//THEN
		Assert.assertEquals(expected, underTest);
	}
	
	@Test
	public void testTrainingTypeDefaultConstructor() {
		//WHEN
		underTest = new TrainingType();
		//THEN
		Assert.assertEquals(null, underTest.getTrainer());
		Assert.assertEquals(null, underTest.getDetail());
		Assert.assertEquals(0L, underTest.getId());
		Assert.assertEquals(0.0, underTest.getPrice());
	}
}
