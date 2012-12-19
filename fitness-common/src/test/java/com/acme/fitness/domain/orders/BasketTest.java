package com.acme.fitness.domain.orders;

import java.util.ArrayList;
import java.util.List;

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
		List<OrderItem> expected=new ArrayList<OrderItem>();
		underTest.setOrderItems(expected);
		Assert.assertEquals(expected, underTest.getOrderItems());
	}
	
	@Test
	public void testGetterSetterBehaviourOfTrainings() {
		List<Training> expected=new ArrayList<Training>();
		underTest.setTrainings(expected);
		Assert.assertEquals(expected, underTest.getTrainings());
	}
	
	@Test
	public void testGetterSetterBehaviourOfMemeberships() {
		List<Membership> expected=new ArrayList<Membership>();
		underTest.setMemberships(expected);
		Assert.assertEquals(expected, underTest.getMemberships());
	}
	
	@Test
	public void testEqualsWithEqualsVeryfier(){
		EqualsVerifier.forClass(User.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
	
	@Test
	public void testConstructorWithDeliveredAndUserArgumentsShouldCreateNewInstanceRight() {
		//GIVEN
		Basket expected = new Basket();
		expected.setDelivered(false);
		expected.setUser(new User());
		//WHEN
		underTest = new Basket(false, new User());
		//THEN
		Assert.assertEquals(expected, underTest);
	}
	
	@Test
	public void testAddMembershipShouldInvokeTheMethodRight() {
		//GIVEN
		Membership membership = new Membership();
		List<Membership> expected = new ArrayList<Membership>();
		expected.add(membership);
		//WHEN
		underTest.addMembership(membership);
		//THEN
		Assert.assertEquals(expected, underTest.getMemberships());
	}
	
	@Test
	public void testAddTrainingShouldInvokeTheMethodRight() {
		//GIVEN
		Training training = new Training();
		List<Training> expected = new ArrayList<Training>();
		expected.add(training);
		//WHEN
		underTest.addTraining(training);
		//THEN
		Assert.assertEquals(expected, underTest.getTrainings());
	}
	
	@Test
	public void testAddOrderItemShouldInvokeTheMethodRight() {
		//GIVEN
		OrderItem orderItem = new OrderItem();
		List<OrderItem> expected = new ArrayList<OrderItem>();
		expected.add(orderItem);
		//WHEN
		underTest.addOrderItem(orderItem);
		//THEN
		Assert.assertEquals(expected, underTest.getOrderItems());
	}
	
	@Test
	public void testToStringShouldReturnProperly() {
		//GIVEN
		underTest = new Basket(false, new User());
		//WHEN
		String result = underTest.toString();
		//THEN
		Assert.assertEquals("Basket [id=0, delivered=false, user=User [id=0, fullName=null, userName=null, email=null, registration=null, lastLogin=null, lastLoginIp=null, isEnabled=true, numberOfRetries=0]]", result);
	}
}
