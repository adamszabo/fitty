package com.acme.fitness.orders.simple;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public class SimpleBasketServiceTest {
	
	private SimpleBasketService underTest;

	@Mock
	private BasketDao basketDao;
	
	@Mock
	private Basket basketMock;
	
	@Before
	public void setUp() {
		underTest = new SimpleBasketService();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testNewBasketShouldReturnTheRightObject() {
		//GIVEN
		User expectedClient = new User();
		expectedClient.setId(1L);
		Basket expectedBasket = new Basket(false, expectedClient);
		//WHEN
		Basket result = underTest.newBasket(expectedClient);
		//THEN
		Assert.assertEquals(expectedBasket, result);
	}
	
	@Test
	public void testDeleteBasketShouldInvokeTheMethodRight() {
		//GIVEN
		Basket expectedBasket = new Basket();
		expectedBasket.setId(1L);
		underTest.setBasketDao(basketDao);
		//WHEN
		underTest.deleteBasket(expectedBasket);
		//THEN
		BDDMockito.verify(basketDao).delete(expectedBasket);
	}
	
	@Test
	public void testUpdateBasketShouldInvokeTheMethodRight() {
		//GIVEN
		Basket expectedBasket = new Basket();
		expectedBasket.setId(1L);
		underTest.setBasketDao(basketDao);
		//WHEN
		underTest.updateBasket(expectedBasket);
		//THEN
		BDDMockito.verify(basketDao).update(expectedBasket);
	}
	
	@Test
	public void testAddMembershipToBasketShouldAddTheMembershipRight() {
		//GIVEN
		Basket basket = new Basket(false, new User());
		basket.setId(1L);
		Membership membership = new Membership();
		membership.setId(2L);
		Basket expectedBasket = new Basket(false, new User());
		expectedBasket.setId(1L);
		Membership expectedMembership = new Membership();
		expectedMembership.setId(2L);
		expectedBasket.addMembership(expectedMembership);
		//WHEN
		underTest.addMembershipToBasket(basket, membership);
		//THEN
		Assert.assertEquals(expectedBasket, basket);
	}
	
	@Test
	public void testAddTrainingToBasketShouldAddTheTrainingRight() {
		//GIVEN
		Basket basket = new Basket(false, new User());
		basket.setId(1L);
		Training training = new Training();
		training.setId(2L);
		Basket expectedBasket = new Basket(false, new User());
		expectedBasket.setId(1L);
		Training expectedTraining = new Training();
		expectedTraining.setId(2L);
		expectedBasket.addTraining(expectedTraining);
		//WHEN
		underTest.addTrainingToBasket(basket, training);
		//THEN
		Assert.assertEquals(expectedBasket, basket);
	}
	
	@Test
	public void testAddOrderItemToBasketShouldAddTheOrderItemRight() {
		//GIVEN
		Basket basket = new Basket(false, new User());
		basket.setId(1L);
		OrderItem orderItem = new OrderItem();
		orderItem.setId(2L);
		Basket expectedBasket = new Basket(false, new User());
		expectedBasket.setId(1L);
		OrderItem expectedOrderItem = new OrderItem();
		expectedOrderItem.setId(2L);
		expectedBasket.addOrderItem(expectedOrderItem);
		//WHEN
		underTest.addOrderItemToBasket(basket, orderItem);
		//THEN
		Assert.assertEquals(expectedBasket, basket);
	}
	
	@Test
	public void testGetMembershipsShouldReturnTheRightObject() {
		//GIVEN
		Set<Membership> memberships = new HashSet<Membership>();
		memberships.add(new Membership());
		memberships.add(new Membership());
		Basket basket = new Basket();
		for(Membership m : memberships) {
			basket.addMembership(m);
		}
		//WHEN
		Set<Membership> result = underTest.getMemberships(basket);
		//THEN
		Assert.assertEquals(memberships, result);
	}
	
	@Test
	public void testGetTrainingsShouldReturnTheRightObject() {
		//GIVEN
		Set<Training> trainings = new HashSet<Training>();
		trainings.add(new Training());
		trainings.add(new Training());
		Basket basket = new Basket();
		for(Training t : trainings) {
			basket.addTraining(t);
		}
		//WHEN
		Set<Training> result = underTest.getTrainings(basket);
		//THEN
		Assert.assertEquals(trainings, result);
	}
	
	@Test
	public void testGetOrderItemsShouldReturnTheRightObject() {
		//GIVEN
		Set<OrderItem> orderItems = new HashSet<OrderItem>();
		orderItems.add(new OrderItem());
		orderItems.add(new OrderItem());
		Basket basket = new Basket();
		for(OrderItem o : orderItems) {
			basket.addOrderItem(o);
		}
		//WHEN
		Set<OrderItem> result = underTest.getOrderItems(basket);
		//THEN
		Assert.assertEquals(orderItems, result);
	}
	
	@Test
	public void testCheckOutBasketShouldInvokeTheMethodRight() throws StoreQuantityException {
		//GIVEN
		underTest.setBasketDao(basketDao);
		//WHEN
		underTest.checkOutBasket(basketMock);
		//THEN
		BDDMockito.verify(basketDao).save(basketMock);
	}
	
	@Test
	public void testDeliverShouldInvokeTheMethodRight() {
		//GIVEN
		Basket basket = new Basket();
		basket.setId(1L);
		Basket expectedBasket = new Basket();
		expectedBasket.setId(1L);
		expectedBasket.setDelivered(true);
		underTest.setBasketDao(basketDao);
		//WHEN
		underTest.deliver(basket);
		//THEN
		BDDMockito.verify(basketDao).update(expectedBasket);
	}
		
}
