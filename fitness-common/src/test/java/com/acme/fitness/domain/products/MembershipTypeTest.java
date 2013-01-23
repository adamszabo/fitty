package com.acme.fitness.domain.products;

import junit.framework.Assert;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

public class MembershipTypeTest {

	private MembershipType underTest;

	@Before
	public void setUp() {
		underTest = new MembershipType();
	}

	@Test
	public void testGetterAndSetterBehaviorOfId() {
		// WHEN
		underTest.setId(1L);
		// THEN
		Assert.assertEquals(1L, underTest.getId());
	}

	@Test
	public void testGetterAndSetterBehaviorOfType() {
		// WHEN
		underTest.setDetail("type");
		// THEN
		Assert.assertEquals("type", underTest.getDetail());
	}

	@Test
	public void testGetterAndSetterBehaviorOfMaxNumberOfEntries() {
		// WHEN
		underTest.setMaxNumberOfEntries(1);
		// THEN
		Assert.assertEquals(1, underTest.getMaxNumberOfEntries());
	}

	@Test
	public void testGetterAndSetterBehaviorOfExpireDateInDays() {
		// WHEN
		underTest.setExpireDateInDays(1);
		// THEN
		Assert.assertEquals(1, underTest.getExpireDateInDays());
	}

	@Test
	public void testGetterAndSetterBehaviorOfPrice() {
		// WHEN
		underTest.setPrice(1.0);
		// THEN
		Assert.assertEquals(1.0, underTest.getPrice());
	}
	
	@Test
	public void testGetterAndSetterBehavoirOfIntervally() {
		//WHEN
		underTest.setIntervally(true);
		//THEN
		Assert.assertEquals(true, underTest.isIntervally());
	}

	@Test
	public void testEqualsWithEqualsVeryfier() {
		EqualsVerifier.forClass(MembershipType.class)
				.suppress(Warning.STRICT_INHERITANCE)
				.suppress(Warning.NULL_FIELDS).verify();
	}

	@Test
	public void testMembershipTypeConstructorWithArguments() {
		// GIVEN
		MembershipType expected = new MembershipType();
		expected.setExpireDateInDays(1);
		expected.setMaxNumberOfEntries(1);
		expected.setIntervally(true);
		expected.setPrice(1.0);
		expected.setDetail("type");
		// WHEN
		underTest = new MembershipType("type", true, 1, 1, 1.0);
		// THEN
		Assert.assertEquals(expected, underTest);
	}
}
