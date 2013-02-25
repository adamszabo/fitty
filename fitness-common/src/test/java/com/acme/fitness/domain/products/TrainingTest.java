package com.acme.fitness.domain.products;

import java.util.Date;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.users.User;

public class TrainingTest {
	private Training underTest;
	
	@Mock
	private User user;
	
	@Mock
	private Basket basket;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new Training();
	}

	@Test
	public void testGetterSetterBehaviourOfId() {
		underTest.setId(1L);
		Assert.assertEquals(1L, underTest.getId());
	}

	@Test
	public void testGetterSetterBehaviourOfTrainer() {
		underTest.setTrainer(user);
		Assert.assertEquals(user, underTest.getTrainer());
	}

	@Test
	public void testGetterSetterBehaviourOfClient() {
		underTest.setClient(user);
		Assert.assertEquals(user, underTest.getClient());
	}

	@Test
	public void testGetterSetterBehaviourOfDate() {
		Date expected=new Date();
		underTest.setDate(expected);
		Assert.assertEquals(expected, underTest.getDate());
	}

	@Test
	public void testGetterSetterBehaviourOfIsAnalyzed() {
		underTest.setAnalyzed(true);
		Assert.assertEquals(true, underTest.isAnalyzed());
	}

	@Test
	public void testGetterSetterBehaviourOfBurnedCalories() {
		underTest.setBurnedCalories(100);
		Assert.assertEquals(100, underTest.getBurnedCalories());
	}

	@Test
	public void testGetterSetterBehaviourOfReview() {
		underTest.setReview("TESTREVIEW");
		Assert.assertEquals("TESTREVIEW", underTest.getReview());
	}

	@Test
	public void testGetterSetterBehaviourOfBasket() {
		underTest.setBasket(basket);
		Assert.assertEquals(basket, underTest.getBasket());
	}
	
	@Test
	public void testEqualsWithEqualsVeryfier(){
		EqualsVerifier.forClass(Training.class).suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NULL_FIELDS).verify();
	}
	
	@Test
	public void testTrainingConstructorWithArguments() {
		//GIVEN
		User user=new User();
		Date date = new Date();
		Basket basket = new Basket();
		Training expected = new Training();
		expected.setTrainer(user);
		expected.setClient(user);
		expected.setDate(date);
		expected.setAnalyzed(true);
		expected.setBurnedCalories(1);
		expected.setReview("review");
		expected.setBasket(basket);
		expected.setPrice(1.0);
		//WHEN
		underTest = new Training(user, user, date, true, 1, "review", basket, 1.0);
		//THEN
		Assert.assertEquals(expected, underTest);
	}
}
