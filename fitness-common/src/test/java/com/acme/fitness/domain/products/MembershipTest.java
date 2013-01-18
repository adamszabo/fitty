package com.acme.fitness.domain.products;

import java.util.Date;

import junit.framework.Assert;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.orders.Basket;

public class MembershipTest {

	private Membership underTest;

	@Mock
	private Basket basket;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new Membership();
	}

	@Test
	public void testGetterSetterBehaviourOfId() {
		underTest.setId(1L);
		Assert.assertEquals(1L, underTest.getId());
	}

	@Test
	public void testGetterSetterBehaviourOfType() {
		underTest.setType("type");
		Assert.assertEquals("type", underTest.getType());
	}

	@Test
	public void testGetterSetterBehaviourOfNumberOfEntries() {
		underTest.setNumberOfEntries(1);
		Assert.assertEquals(1, underTest.getNumberOfEntries());
	}

	@Test
	public void testGetterSetterBehaviourOfExpireDate() {
		Date date = new Date(5555L);
		underTest.setExpireDate(date);
		Assert.assertEquals(date, underTest.getExpireDate());
	}

	@Test
	public void testGetterSetterBehaviourOfPrice() {
		underTest.setPrice(111.0);
		Assert.assertEquals(111.0, underTest.getPrice());
	}

	@Test
	public void testGetterSetterBehaviourOfBaskest() {
		underTest.setBasket(basket);
		Assert.assertEquals(basket, underTest.getBasket());
	}

	@Test
	public void testEqualsWithEqualsVeryfier() {
		EqualsVerifier.forClass(Membership.class)
				.suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NULL_FIELDS).verify();
	}
	
	@Test
	public void testMaxNumberOfEntiresGetterAndSetterBehaviour() {
		underTest.setMaxNumberOfEntries(2);
		Assert.assertEquals(2, underTest.getMaxNumberOfEntries());
	}
	
	@Test
	public void testMembershipConstructorWithArguments() {
		//GIVEN
		Date date = new Date();
		Membership expected = new Membership();
		expected.setType("type");
		expected.setNumberOfEntries(1);
		expected.setMaxNumberOfEntries(1);
		expected.setExpireDate(date);
		expected.setPrice(1.0);
		expected.setBasket(new Basket());
		//WHEN
		underTest = new Membership("type", 1, 1, date, 1.0, new Basket());
		//THEN
		Assert.assertEquals(expected, underTest);
	}

}
