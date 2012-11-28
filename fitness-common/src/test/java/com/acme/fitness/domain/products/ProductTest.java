package com.acme.fitness.domain.products;

import java.util.Date;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {
	private Product underTest;

	@Before
	public void setUp() {
		underTest = new Product();
	}

	@Test
	public void testGetterSetterBehaviourOfId() {
		underTest.setId(1L);
		Assert.assertEquals(1L, underTest.getId());
	}

	@Test
	public void testGetterSetterBehaviourOfName() {
		underTest.setName("TESTNAME");
		Assert.assertEquals("TESTNAME", underTest.getName());
	}

	@Test
	public void testGetterSetterBehaviourOfDetails() {
		underTest.setDetails("TESTDETAILS");
		Assert.assertEquals("TESTDETAILS", underTest.getDetails());
	}

	@Test
	public void testGetterSetterBehaviourOfPrice() {
		underTest.setPrice(1.0);
		Assert.assertEquals(1.0, underTest.getPrice(), 0.0001);
	}

	@Test
	public void testGetterSetterBehaviourOfCreation() {
		Date expected = new Date();
		underTest.setCreation(expected);
		Assert.assertEquals(expected, underTest.getCreation());
	}

	@Test
	public void testEqualsWithEqualsVeryfier() {
		EqualsVerifier.forClass(Product.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
}
