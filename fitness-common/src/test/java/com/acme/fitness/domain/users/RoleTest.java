package com.acme.fitness.domain.users;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RoleTest {
	private Role underTest;
	
	@Mock
	private User user;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new Role();
	}

	@Test
	public void testGetterSetterBehaviourOfId() {
		underTest.setId(1L);
		Assert.assertEquals(1L, underTest.getId());
	}
	
	@Test
	public void testGetterSetterBehaviourOfUser() {
		underTest.setUser(user);
		Assert.assertEquals(user, underTest.getUser());
	}
	
	@Test
	public void testGetterSetterBehaviourOfName() {
		underTest.setName("TESTROLE");
		Assert.assertEquals("TESTROLE", underTest.getName());
	}
	
	@Test
	public void testEqualsWithEqualsVeryfier(){
		EqualsVerifier.forClass(Role.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
}
