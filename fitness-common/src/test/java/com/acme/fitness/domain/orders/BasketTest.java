package com.acme.fitness.domain.orders;

import java.util.HashSet;
import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public class BasketTest {
	private Basket underTest;
	
	@Mock
	private User user;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new Basket();
	}

	@Test
	public void testGetterSetterBehaviourOfId() {
		underTest.setId(1L);
		Assert.assertEquals(1L, underTest.getId());
	}
	
	@Test
	public void testGetterSetterBehaviourOfDelivered() {
		underTest.setDelivered(true);
		Assert.assertEquals(true, underTest.isDelivered());
	}
	
	@Test
	public void testGetterSetterBehaviourOfUser() {
		underTest.setUser(user);
		Assert.assertEquals(user, underTest.getUser());
	}
	
	@Test
	public void testGetterSetterBehaviourOfOrderItems() {
		Set<OrderItem> expected=new HashSet<OrderItem>();
		underTest.setOrderItems(expected);
		Assert.assertEquals(expected, underTest.getOrderItems());
	}
	
	@Test
	public void testGetterSetterBehaviourOfTrainings() {
		Set<Training> expected=new HashSet<Training>();
		underTest.setTrainings(expected);
		Assert.assertEquals(expected, underTest.getTrainings());
	}
	
	@Test
	public void testGetterSetterBehaviourOfMemeberships() {
		Set<Membership> expected=new HashSet<Membership>();
		underTest.setMemberships(expected);
		Assert.assertEquals(expected, underTest.getMemberships());
	}
	
	@Test
	public void testEqualsWithEqualsVeryfier(){
		EqualsVerifier.forClass(User.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
}
