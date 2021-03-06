package com.acme.fitness.orders.simple;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.BasketService;
import com.acme.fitness.orders.OrderItemService;
import com.acme.fitness.orders.StoreService;

public class SimpleGeneralOrdersServiceTest {

	private SimpleGeneralOrdersService underTest;

	@Mock
	private BasketService basketServiceMock;

	@Mock
	private OrderItemService orderItemServiceMock;

	@Mock
	private StoreService storeServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleGeneralOrdersService(basketServiceMock, orderItemServiceMock, storeServiceMock);
	}

	@Test
	public void testNewBasketShouldReturnProperly() {
		// GIVEN
		User client = new User();
		Basket expected = new Basket();
		BDDMockito.given(basketServiceMock.newBasket(client)).willReturn(
				expected);
		// WHEN
		Basket result = underTest.newBasket(client);
		// THEN
		BDDMockito.verify(basketServiceMock).newBasket(client);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testDeleteBasketShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		// WHEN
		underTest.deleteBasket(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).deleteBasket(basket);
	}

	@Test
	public void testUpdateBasketShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		// WHEN
		underTest.updateBasket(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).updateBasket(basket);
	}

	@Test
	public void testAddMembershipToBasketShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		Membership membership = new Membership();
		// WHEN
		underTest.addMembershipToBasket(basket, membership);
		// THEN
		BDDMockito.verify(basketServiceMock).addMembershipToBasket(basket,
				membership);

	}

	@Test
	public void testAddTrainingToBasketShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		Training training = new Training();
		// WHEN
		underTest.addTrainingToBasket(basket, training);
		// THEN
		BDDMockito.verify(basketServiceMock).addTrainingToBasket(basket,
				training);
	}

	@Test
	public void testAddOrderItemToBasketShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		OrderItem orderItem = new OrderItem();
		// WHEN
		underTest.addOrderItemToBasket(basket, orderItem);
		// THEN
		BDDMockito.verify(basketServiceMock).addOrderItemToBasket(basket,
				orderItem);
	}

	@Test
	public void testGetMembershipsShouldReturnProperly() {
		// GIVEN
		Basket basket = new Basket();
		List<Membership> expected = new ArrayList<Membership>();
		BDDMockito.given(basketServiceMock.getMemberships(basket)).willReturn(
				expected);
		// WHEN
		List<Membership> result = underTest.getMemberships(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).getMemberships(basket);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetTrainingsShouldReturnProperly() {
		// GIVEN
		Basket basket = new Basket();
		List<Training> expected = new ArrayList<Training>();
		BDDMockito.given(basketServiceMock.getTrainings(basket)).willReturn(
				expected);
		// WHEN
		List<Training> result = underTest.getTrainings(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).getTrainings(basket);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetOrderItemsShouldReturnProperly() {
		// GIVEN
		Basket basket = new Basket();
		List<OrderItem> expected = new ArrayList<OrderItem>();
		BDDMockito.given(basketServiceMock.getOrderItems(basket)).willReturn(
				expected);
		// WHEN
		List<OrderItem> result = underTest.getOrderItems(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).getOrderItems(basket);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testCheckOutBasketShouldInvokeTheMethodRight()
			throws StoreQuantityException {
		// GIVEN
		Basket basket = new Basket();
		// WHEN
		underTest.checkOutBasket(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).checkOutBasket(basket);
	}

	@Test
	public void testDeliverShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		// WHEN
		underTest.deliver(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).deliver(basket);
	}

	@Test
	public void testGetBasketsByUserShouldReturnProperly() {
		// GIVEN
		User user = new User();
		List<Basket> expected = new ArrayList<Basket>();
		BDDMockito.given(basketServiceMock.getBasketsByUser(user)).willReturn(
				expected);
		// WHEN
		List<Basket> result = underTest.getBasketsByUser(user);
		// THEN
		BDDMockito.verify(basketServiceMock).getBasketsByUser(user);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetBasketByIdShouldReturnProperly()
			throws FitnessDaoException {
		// GIVEN
		Basket expected = new Basket();
		BDDMockito.given(basketServiceMock.getBasketById(Mockito.anyLong()))
				.willReturn(expected);
		// WHEN
		Basket result = underTest.getBasketById(1L);
		// THEN
		BDDMockito.verify(basketServiceMock).getBasketById(Mockito.anyLong());
		Assert.assertEquals(expected, result);
	}

	@Test(expected = FitnessDaoException.class)
	public void testGetBasketByIdShouldThrowException()
			throws FitnessDaoException {
		// GIVEN
		BDDMockito.given(basketServiceMock.getBasketById(Mockito.anyLong()))
				.willThrow(new FitnessDaoException());
		// WHEN
		underTest.getBasketById(1L);
		// THEN
		BDDMockito.verify(basketServiceMock).getBasketById(Mockito.anyLong());
	}

	@Test
	public void testNewOrderItemShouldReturnProperly() {
		// GIVEN
		Product product = new Product();
		int quantity = 1;
		OrderItem expected = new OrderItem();
		BDDMockito.given(orderItemServiceMock.newOrderItem(product, quantity))
				.willReturn(expected);
		// WHEN
		OrderItem result = underTest.newOrderItem(product, quantity);
		// THEN
		BDDMockito.verify(orderItemServiceMock).newOrderItem(product, quantity);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testDeleteOrderItemShouldInvokeTheMethodRight() {
		// GIVEN
		OrderItem orderItem = new OrderItem();
		// WHEN
		underTest.deleteOrderItem(orderItem);
		// THEN
		BDDMockito.verify(orderItemServiceMock).deleteOrderItem(orderItem);
	}

	@Test
	public void testUpdateOrderItemShouldInvokeTheMethodRight() {
		// GIVEN
		OrderItem orderItem = new OrderItem();
		// WHEN
		underTest.updateOrderItem(orderItem);
		// THEN
		BDDMockito.verify(orderItemServiceMock).updateOrderItem(orderItem);
	}

	@Test
	public void testGetOrderItemByIdShouldReturnProperly()
			throws FitnessDaoException {
		// GIVEN
		OrderItem expected = new OrderItem();
		BDDMockito.given(
				orderItemServiceMock.getOrderItemById(Mockito.anyLong()))
				.willReturn(expected);
		// WHEN
		OrderItem result = underTest.getOrderItemById(1L);
		// THEN
		BDDMockito.verify(orderItemServiceMock).getOrderItemById(
				Mockito.anyLong());
		Assert.assertEquals(expected, result);
	}

	@Test(expected = FitnessDaoException.class)
	public void testGetOrderItemByIdShouldThrowException()
			throws FitnessDaoException {
		// GIVEN
		BDDMockito.given(
				orderItemServiceMock.getOrderItemById(Mockito.anyLong()))
				.willThrow(new FitnessDaoException());
		// WHEN
		underTest.getOrderItemById(1L);
		// THEN
		BDDMockito.verify(orderItemServiceMock).getOrderItemById(
				Mockito.anyLong());
	}

	@Test
	public void testAddProductToStoreShouldReturnProperly() {
		// GIVEN
		Product product = new Product();
		int quantity = 1;
		Store expected = new Store(product, quantity);
		BDDMockito.given(storeServiceMock.addProduct(product, quantity))
				.willReturn(expected);
		// WHEN
		Store result = underTest.addProductToStore(product, quantity);
		// THEN
		BDDMockito.verify(storeServiceMock).addProduct(product, quantity);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testDeleteStoreShouldInvokeTheMethodRight() {
		// GIVEN
		Store store = new Store();
		// WHEN
		underTest.deleteStore(store);
		// THEN
		BDDMockito.verify(storeServiceMock).deleteStore(store);
	}

	@Test
	public void testUpdateStoreShouldInvokeTheMethodRight() {
		// GIVEN
		Store store = new Store();
		// WHEN
		underTest.updateStore(store);
		// THEN
		BDDMockito.verify(storeServiceMock).updateStore(store);
	}

	@Test
	public void testGetStoreByProductShouldReturnProperly()
			throws FitnessDaoException {
		// GIVEN
		Product product = new Product();
		Store expected = new Store();
		BDDMockito.given(storeServiceMock.getStoreByProduct(product))
				.willReturn(expected);
		// WHEN
		Store result = underTest.getStoreByProduct(product);
		// THEN
		BDDMockito.verify(storeServiceMock).getStoreByProduct(product);
		Assert.assertEquals(expected, result);
	}

	@Test(expected = FitnessDaoException.class)
	public void testGetStoreByProductShouldThrowException()
			throws FitnessDaoException {
		// GIVEN
		Product product = new Product();
		BDDMockito.given(storeServiceMock.getStoreByProduct(product))
				.willThrow(new FitnessDaoException());
		// WHEN
		underTest.getStoreByProduct(product);
		// THEN
		BDDMockito.verify(storeServiceMock).getStoreByProduct(product);
	}

	@Test
	public void testTakeOutProductShouldReturnProperly()
			throws FitnessDaoException {
		// GIVEN
		Product product = new Product();
		int quantity = 1;
		boolean expected = true;
		BDDMockito.given(storeServiceMock.takeOutProduct(product, quantity))
				.willReturn(expected);
		// WHEN
		boolean result = underTest.takeOutProduct(product, quantity);
		// THEN
		BDDMockito.verify(storeServiceMock).takeOutProduct(product, quantity);
		Assert.assertEquals(expected, result);
	}

	@Test(expected = FitnessDaoException.class)
	public void testTakeOutProductShouldThrowException()
			throws FitnessDaoException {
		// GIVEN
		Product product = new Product();
		int quantity = 1;
		BDDMockito.given(storeServiceMock.takeOutProduct(product, quantity))
				.willThrow(new FitnessDaoException());
		// WHEN
		underTest.takeOutProduct(product, quantity);
		// THEN
		BDDMockito.verify(storeServiceMock).takeOutProduct(product, quantity);
	}

	@Test
	public void testPutInProductShouldReturnProperly()
			throws FitnessDaoException {
		// GIVEN
		Product product = new Product();
		int quantity = 1;
		boolean expected = true;
		BDDMockito.given(storeServiceMock.takeOutProduct(product, quantity))
				.willReturn(expected);
		// WHEN
		boolean result = underTest.takeOutProduct(product, quantity);
		// THEN
		BDDMockito.verify(storeServiceMock).takeOutProduct(product, quantity);
		Assert.assertEquals(expected, result);
	}

	@Test(expected = FitnessDaoException.class)
	public void testPutInProductShouldThrowException()
			throws FitnessDaoException {
		// GIVEN
		Product product = new Product();
		int quantity = 1;
		BDDMockito.doThrow(new FitnessDaoException()).when(storeServiceMock)
				.putInProduct(product, quantity);
		// WHEN
		underTest.putInProduct(product, quantity);
		// THEN
		BDDMockito.verify(storeServiceMock).putInProduct(product, quantity);
	}

	@Test
	public void testPutInProductShouldInvokeTheMethodProperly() throws FitnessDaoException {
		// GIVEN
		Product product = new Product();
		int quantity = 1;
		BDDMockito.doNothing().when(storeServiceMock).putInProduct(product, quantity);
		// WHEN
		underTest.putInProduct(product, quantity);
		// THEN
		BDDMockito.verify(storeServiceMock).putInProduct(product, quantity);
	}
	
	@Test
	public void testGetAllStoresShouldReturnProperly() {
		//GIVEN
		BDDMockito.given(storeServiceMock.getAllStores()).willReturn(new ArrayList<Store>());
		//WHEN
		List<Store> result = underTest.getAllStores();
		//THEN
		BDDMockito.verify(storeServiceMock).getAllStores();
		Assert.assertEquals(new ArrayList<Store>(), result);
	}
	
	@Test
	public void testGetBasketsByUserAndDeliveredStatusShouldReturnProperly() {
		//GIVEN
		List<Basket> expected=new ArrayList<Basket>();
		User user=new User();
		BDDMockito.given(basketServiceMock.getBasketsByUserAndDeliveredStatus(user, true)).willReturn(expected);
		//WHEN
		List<Basket> result = underTest.getBasketsByUserAndDeliveredStatus(user, true);
		//THEN
		BDDMockito.verify(basketServiceMock).getBasketsByUserAndDeliveredStatus(user, true);
		Assert.assertEquals(expected, result);
	}
}