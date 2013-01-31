package com.acme.fitness.orders.simple;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.OrderItemService;
import com.acme.fitness.orders.StoreService;
import com.acme.fitness.products.MembershipService;
import com.acme.fitness.products.TrainingService;

public class SimpleBasketServiceTest {

	private SimpleBasketService underTest;

	@Mock
	private BasketDao basketDao;

	@Mock
	private Basket basketMock;

	@Mock
	private MembershipService membershipService;

	@Mock
	private TrainingService trainingService;

	@Mock
	private OrderItemService orderItemService;

	@Mock
	private StoreService storeService;

	@Mock
	private OrderItem orderItemMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleBasketService(basketDao, membershipService, trainingService, orderItemService, storeService);
	}

	@Test
	public void testNewBasketShouldReturnTheRightObject() {
		// GIVEN
		User expectedClient = new User();
		expectedClient.setId(1L);
		Basket expectedBasket = new Basket(false, expectedClient);
		// WHEN
		Basket result = underTest.newBasket(expectedClient);
		// THEN
		Assert.assertEquals(expectedBasket, result);
	}

	@Test
	public void testDeleteBasketShouldInvokeTheMethodRight() {
		// GIVEN
		Basket expectedBasket = new Basket();
		expectedBasket.setId(1L);
		// WHEN
		underTest.deleteBasket(expectedBasket);
		// THEN
		BDDMockito.verify(basketDao).delete(expectedBasket);
	}

	@Test
	public void testUpdateBasketShouldInvokeTheMethodRight() {
		// GIVEN
		Basket expectedBasket = new Basket();
		expectedBasket.setId(1L);
		// WHEN
		underTest.updateBasket(expectedBasket);
		// THEN
		BDDMockito.verify(basketDao).update(expectedBasket);
	}

	@Test
	public void testAddMembershipToBasketShouldAddTheMembershipRight() {
		// GIVEN
		User user=new User();
		Basket basket = new Basket(false, user);
		basket.setId(1L);
		Membership membership = new Membership();
		membership.setId(2L);
		Basket expectedBasket = new Basket(false, user);
		expectedBasket.setId(1L);
		Membership expectedMembership = new Membership();
		expectedMembership.setId(2L);
		expectedBasket.addMembership(expectedMembership);
		// WHEN
		underTest.addMembershipToBasket(basket, membership);
		// THEN
		Assert.assertEquals(expectedBasket, basket);
	}

	@Test
	public void testAddTrainingToBasketShouldAddTheTrainingRight() {
		// GIVEN
		User user=new User();
		Basket basket = new Basket(false, user);
		basket.setId(1L);
		Training training = new Training();
		training.setId(2L);
		Basket expectedBasket = new Basket(false, user);
		expectedBasket.setId(1L);
		Training expectedTraining = new Training();
		expectedTraining.setId(2L);
		expectedBasket.addTraining(expectedTraining);
		// WHEN
		underTest.addTrainingToBasket(basket, training);
		// THEN
		Assert.assertEquals(expectedBasket, basket);
	}

	@Test
	public void testAddOrderItemToBasketShouldAddTheOrderItemRight() {
		// GIVEN
		User user=new User();
		Basket basket = new Basket(false, user);
		basket.setId(1L);
		OrderItem orderItem = new OrderItem();
		orderItem.setId(2L);
		Basket expectedBasket = new Basket(false, user);
		expectedBasket.setId(1L);
		OrderItem expectedOrderItem = new OrderItem();
		expectedOrderItem.setId(2L);
		expectedBasket.addOrderItem(expectedOrderItem);
		// WHEN
		underTest.addOrderItemToBasket(basket, orderItem);
		// THEN
		Assert.assertEquals(expectedBasket, basket);
	}

	@Test
	public void testGetMembershipsShouldReturnTheRightObject() {
		// GIVEN
		List<Membership> memberships = new ArrayList<Membership>();
		memberships.add(new Membership());
		memberships.add(new Membership());
		Basket basket = new Basket();
		for (Membership m : memberships) {
			basket.addMembership(m);
		}
		// WHEN
		List<Membership> result = underTest.getMemberships(basket);
		// THEN
		Assert.assertEquals(memberships, result);
	}

	@Test
	public void testGetTrainingsShouldReturnTheRightObject() {
		// GIVEN
		List<Training> trainings = new ArrayList<Training>();
		trainings.add(new Training());
		trainings.add(new Training());
		Basket basket = new Basket();
		for (Training t : trainings) {
			basket.addTraining(t);
		}
		// WHEN
		List<Training> result = underTest.getTrainings(basket);
		// THEN
		Assert.assertEquals(trainings, result);
	}

	@Test
	public void testGetOrderItemsShouldReturnTheRightObject() {
		// GIVEN
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(new OrderItem());
		orderItems.add(new OrderItem());
		Basket basket = new Basket();
		for (OrderItem o : orderItems) {
			basket.addOrderItem(o);
		}
		// WHEN
		List<OrderItem> result = underTest.getOrderItems(basket);
		// THEN
		Assert.assertEquals(orderItems, result);
	}

	@Test
	public void testDeliverShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		basket.setId(1L);
		Basket expectedBasket = new Basket();
		expectedBasket.setId(1L);
		expectedBasket.setDelivered(true);
		// WHEN
		underTest.deliver(basket);
		// THEN
		BDDMockito.verify(basketDao).update(expectedBasket);
	}

	@Test
	public void testGetBasketsByUserShouldReturnProperly() {
		// GIVEN
		User user = new User();
		BDDMockito.given(basketDao.getBasketsByUser(user)).willReturn(
				new ArrayList<Basket>());
		// WHEN
		List<Basket> result = underTest.getBasketsByUser(user);
		// THEN
		BDDMockito.verify(basketDao).getBasketsByUser(user);
		Assert.assertEquals(new ArrayList<Basket>(), result);
	}

	@Test
	public void testGetBasketByIdShouldReturnProperly()
			throws FitnessDaoException {
		// GIVEN
		Basket expected = new Basket();
		BDDMockito.given(basketDao.getBasketById(1L)).willReturn(expected);
		// WHEN
		Basket result = underTest.getBasketById(1L);
		// THEN
		BDDMockito.verify(basketDao).getBasketById(1L);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testCheckOutBasketShouldInvokeTheTrainingAndMembershipMethodsRight()
			throws StoreQuantityException {
		// GIVEN
		Training training = new Training();
		Membership membership = new Membership();
		List<Training> trainings = new ArrayList<Training>();
		List<Membership> memberships = new ArrayList<Membership>();
		trainings.add(training);
		memberships.add(membership);
		BDDMockito.given(basketMock.getTrainings()).willReturn(trainings);
		BDDMockito.given(basketMock.getMemberships()).willReturn(memberships);
		// WHEN
		underTest.checkOutBasket(basketMock);
		// THEN
		BDDMockito.verify(basketDao).save(basketMock);
		BDDMockito.verify(basketMock).getTrainings();
		BDDMockito.verify(basketMock).getMemberships();
	}

	@Test
	public void testCheckOutBasketShouldInvokeTheUpdateOrderItemMethodRight()
			throws StoreQuantityException, FitnessDaoException {
		// GIVEN
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItemMock);
		BDDMockito.given(basketMock.getOrderItems()).willReturn(orderItems);
		BDDMockito.given(orderItemMock.getProduct()).willReturn(new Product());
		BDDMockito.given(orderItemMock.getQuantity()).willReturn(1);
		BDDMockito.given(storeService.takeOutProduct(new Product(), 1))
				.willReturn(true);
		BDDMockito.doNothing().when(basketDao).save(basketMock);
		BDDMockito.doNothing().when(orderItemService)
				.updateOrderItem(orderItemMock);
		// WHEN
		underTest.checkOutBasket(basketMock);
		// THEN
		BDDMockito.verify(basketMock).getOrderItems();
		BDDMockito.verify(orderItemMock).getProduct();
		BDDMockito.verify(orderItemMock).getQuantity();
		BDDMockito.verify(storeService).takeOutProduct(new Product(), 1);
		BDDMockito.verify(basketDao).save(basketMock);
		BDDMockito.verify(orderItemService).updateOrderItem(orderItemMock);
	}

	@Test
	public void testCheckOutBasketShouldThrowExpcetionWhenInvokeTheUpdateOrderItemMethod() throws FitnessDaoException {
		//GIVEN
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItemMock);
		Product expectedProduct = new Product();
		expectedProduct.setId(1L);
		BDDMockito.given(basketMock.getOrderItems()).willReturn(orderItems);
		BDDMockito.given(orderItemMock.getProduct()).willReturn(expectedProduct);
		BDDMockito.given(orderItemMock.getQuantity()).willReturn(1);
		BDDMockito.given(storeService.takeOutProduct(expectedProduct, 1)).willThrow(new FitnessDaoException());
		BDDMockito.doNothing().when(basketDao).save(basketMock);
		BDDMockito.doNothing().when(orderItemService).updateOrderItem(orderItemMock);
		List<Product> result = null;
		List<Product> products = new ArrayList<Product>();
		products.add(expectedProduct);
		//WHEN
		try {
			underTest.checkOutBasket(basketMock);
		} catch (StoreQuantityException e) {
			result = e.getProduct();
		}
		//THEN
		Assert.assertEquals(products, result);
		BDDMockito.verify(basketMock).getOrderItems();
		BDDMockito.verify(orderItemMock, BDDMockito.times(2)).getProduct();
		BDDMockito.verify(orderItemMock).getQuantity();
		BDDMockito.verify(storeService).takeOutProduct(expectedProduct, 1);
		BDDMockito.verify(basketDao).save(basketMock);
	}
	
	@Test
	public void testCheckOutBasketShouldInvokeTakeOutProductsElseBranchWhenTakeOUtProductMethodReturnsFalse() throws FitnessDaoException {
		//GIVEN
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItemMock);
		Product expectedProduct = new Product();
		expectedProduct.setId(1L);
		BDDMockito.given(basketMock.getOrderItems()).willReturn(orderItems);
		BDDMockito.given(orderItemMock.getProduct()).willReturn(expectedProduct);
		BDDMockito.given(orderItemMock.getQuantity()).willReturn(1);
		BDDMockito.given(storeService.takeOutProduct(expectedProduct, 1)).willReturn(false);
		BDDMockito.doNothing().when(orderItemService).updateOrderItem(orderItemMock);
		List<Product> result = null;
		List<Product> products = new ArrayList<Product>();
		products.add(expectedProduct);
		//WHEN
		try {
			underTest.checkOutBasket(basketMock);
		} catch (StoreQuantityException e) {
			result = e.getProduct();
		}
		//THEN
		Assert.assertEquals(products, result);
		BDDMockito.verify(basketMock).getOrderItems();
		BDDMockito.verify(orderItemMock, BDDMockito.times(2)).getProduct();
		BDDMockito.verify(orderItemMock).getQuantity();
		BDDMockito.verify(storeService).takeOutProduct(expectedProduct, 1);
		BDDMockito.verify(basketDao).save(basketMock);
	}
	
	@Test
	public void testGetBasketsByUserAndDeliveredStatus(){
		//GIVEN
		User user=new User();
		List<Basket> expected=new ArrayList<Basket>();
		BDDMockito.given(basketDao.getBasketsByUserAndDeliveredStatus(user, true)).willReturn(expected);
		//WHEN
		List<Basket> result=underTest.getBasketsByUserAndDeliveredStatus(user, true);
		//THEN
		BDDMockito.verify(basketDao).getBasketsByUserAndDeliveredStatus(user, true);
		Assert.assertEquals(expected, result);
		
	}
	
}
