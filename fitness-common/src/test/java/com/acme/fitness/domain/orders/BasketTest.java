package com.acme.fitness.domain.orders;

import java.util.ArrayList;
import java.util.List;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public class BasketTest {
	private Basket underTest;
	
	@Mock
	private User user;
	
	@Mock
	private Membership membership;
	
	@Mock
	private Training training;
	
	@Mock
	private OrderItem orderItem;
	
	@Mock
	private Product product;

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
		User user=new User();
		Basket expected = new Basket();
		expected.setDelivered(false);
		expected.setUser(user);
		//WHEN
		underTest = new Basket(false, user);
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
	public void testGetPriceShouldReturnZeroWhenThereIsNothingInTheBasket() {
		//WHEN
		double actual = underTest.getPrice();
		//THEN
		Assert.assertEquals(0.0, actual, 0.0);
	}
	
	@Test
	public void testGetPriceShouldReturnTheRightMethodWhenContainsEverything() {
		//GIVEN
		underTest.addMembership(membership);
		underTest.addOrderItem(orderItem);
		underTest.addTraining(training);
		BDDMockito.given(membership.getPrice()).willReturn(1.0);
		BDDMockito.given(orderItem.getQuantity()).willReturn(2);
		BDDMockito.given(orderItem.getProduct()).willReturn(product);
		BDDMockito.given(product.getPrice()).willReturn(4.0);
		BDDMockito.given(training.getPrice()).willReturn(8.0);
		//WHEN
		double actual = underTest.getPrice();
		//THEN
		Assert.assertEquals(17.0, actual, 0.0);
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
