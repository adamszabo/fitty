package com.acme.fitness.domain;

import java.util.Date;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.acme.fitness.domain.users.User;

public class UserTest {
	private User underTest;

	@Before
	public void setUp() {
		underTest = new User();
	}

	@Test
	public void testGetterSetterBehaviourOfId() {
		underTest.setId(1L);
		Assert.assertEquals(1L, underTest.getId());
	}

	@Test
	public void testGetterSetterBehaviourOfUserName() {
		underTest.setUserName("USERNAME");
		Assert.assertEquals("USERNAME", underTest.getUserName());
	}

	@Test
	public void testGetterSetterBehaviourOfPassword() {
		underTest.setPassword("PASSWORD");
		Assert.assertEquals("PASSWORD", underTest.getPassword());
	}

	@Test
	public void testGetterSetterBehaviourOfEmail() {
		underTest.setEmail("EMAIL@EMAIL.COM");
		Assert.assertEquals("EMAIL@EMAIL.COM", underTest.getEmail());
	}

	@Test
	public void testGetterSetterBehaviourOfMobile() {
		underTest.setMobile("MOBILENUMBER");
		Assert.assertEquals("MOBILENUMBER", underTest.getMobile());
	}

	@Test
	public void testGetterSetterBehaviourOfRegistration() {
		Date expected=new Date();
		underTest.setRegistration(expected);
		Assert.assertEquals(expected, underTest.getRegistration());
	}

	@Test
	public void testGetterSetterBehaviourOfLastLogin() {
		Date expected=new Date();
		underTest.setLastLogin(expected);
		Assert.assertEquals(expected, underTest.getLastLogin());
	}

	@Test
	public void testGetterSetterBehaviourOfLastLoginIp() {
		underTest.setLastLoginIp("LASTIP");
		Assert.assertEquals("LASTIP", underTest.getLastLoginIp());		
	}

	@Test
	public void testGetterSetterBehaviourOfEnabled() {
		underTest.setEnabled(true);
		Assert.assertEquals(true, underTest.isEnabled());
	}

	@Test
	public void testGetterSetterBehaviourOfNumberOfEntries() {
		underTest.setNumberOfRetries(1);
		Assert.assertEquals(1, underTest.getNumberOfRetries());
	}
	
	@Test
	public void testEqualsWithEqualsVeryfier(){
		EqualsVerifier.forClass(User.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
}
