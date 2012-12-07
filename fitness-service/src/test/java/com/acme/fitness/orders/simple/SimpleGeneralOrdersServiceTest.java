package com.acme.fitness.orders.simple;

import java.util.HashSet;
import java.util.Set;

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
		underTest = new SimpleGeneralOrdersService();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testNewBasketShouldReturnProperly() {
		// GIVEN
		User client = new User();
		Basket expected = new Basket();
		BDDMockito.given(basketServiceMock.newBasket(client)).willReturn(
				expected);
		underTest.setBasketService(basketServiceMock);
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
		underTest.setBasketService(basketServiceMock);
		// WHEN
		underTest.deleteBasket(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).deleteBasket(basket);
	}

	@Test
	public void testUpdateBasketShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		underTest.setBasketService(basketServiceMock);
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
		underTest.setBasketService(basketServiceMock);
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
		underTest.setBasketService(basketServiceMock);
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
		underTest.setBasketService(basketServiceMock);
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
		Set<Membership> expected = new HashSet<Membership>();
		BDDMockito.given(basketServiceMock.getMemberships(basket)).willReturn(
				expected);
		underTest.setBasketService(basketServiceMock);
		// WHEN
		Set<Membership> result = underTest.getMemberships(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).getMemberships(basket);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetTrainingsShouldReturnProperly() {
		// GIVEN
		Basket basket = new Basket();
		Set<Training> expected = new HashSet<Training>();
		BDDMockito.given(basketServiceMock.getTrainings(basket)).willReturn(
				expected);
		underTest.setBasketService(basketServiceMock);
		// WHEN
		Set<Training> result = underTest.getTrainings(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).getTrainings(basket);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetOrderItemsShouldReturnProperly() {
		// GIVEN
		Basket basket = new Basket();
		Set<OrderItem> expected = new HashSet<OrderItem>();
		BDDMockito.given(basketServiceMock.getOrderItems(basket)).willReturn(
				expected);
		underTest.setBasketService(basketServiceMock);
		// WHEN
		Set<OrderItem> result = underTest.getOrderItems(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).getOrderItems(basket);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testCheckOutBasketShouldInvokeTheMethodRight()
			throws StoreQuantityException {
		// GIVEN
		Basket basket = new Basket();
		underTest.setBasketService(basketServiceMock);
		// WHEN
		underTest.checkOutBasket(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).checkOutBasket(basket);
	}

	@Test
	public void testDeliverShouldInvokeTheMethodRight() {
		// GIVEN
		Basket basket = new Basket();
		underTest.setBasketService(basketServiceMock);
		// WHEN
		underTest.deliver(basket);
		// THEN
		BDDMockito.verify(basketServiceMock).deliver(basket);
	}

	@Test
	public void testGetBasketsByUserShouldReturnProperly() {
		// GIVEN
		User user = new User();
		Set<Basket> expected = new HashSet<Basket>();
		BDDMockito.given(basketServiceMock.getBasketsByUser(user)).willReturn(
				expected);
		underTest.setBasketService(basketServiceMock);
		// WHEN
		Set<Basket> result = underTest.getBasketsByUser(user);
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
		underTest.setBasketService(basketServiceMock);
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
		underTest.setBasketService(basketServiceMock);
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
		underTest.setOrderItemService(orderItemServiceMock);
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
		underTest.setOrderItemService(orderItemServiceMock);
		// WHEN
		underTest.deleteOrderItem(orderItem);
		// THEN
		BDDMockito.verify(orderItemServiceMock).deleteOrderItem(orderItem);
	}

	@Test
	public void testUpdateOrderItemShouldInvokeTheMethodRight() {
		// GIVEN
		OrderItem orderItem = new OrderItem();
		underTest.setOrderItemService(orderItemServiceMock);
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
		underTest.setOrderItemService(orderItemServiceMock);
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
		underTest.setOrderItemService(orderItemServiceMock);
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
		underTest.setStoreService(storeServiceMock);
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
		underTest.setStoreService(storeServiceMock);
		// WHEN
		underTest.deleteStore(store);
		// THEN
		BDDMockito.verify(storeServiceMock).deleteStore(store);
	}

	@Test
	public void testUpdateStoreShouldInvokeTheMethodRight() {
		// GIVEN
		Store store = new Store();
		underTest.setStoreService(storeServiceMock);
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
		underTest.setStoreService(storeServiceMock);
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
		underTest.setStoreService(storeServiceMock);
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
		underTest.setStoreService(storeServiceMock);
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
		underTest.setStoreService(storeServiceMock);
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
		underTest.setStoreService(storeServiceMock);
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
		underTest.setStoreService(storeServiceMock);
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
		underTest.setStoreService(storeServiceMock);
		// WHEN
		underTest.putInProduct(product, quantity);
		// THEN
		BDDMockito.verify(storeServiceMock).putInProduct(product, quantity);
	}
	
	@Test
	public void testGetAllStoresShouldReturnProperly() {
		//GIVEN
		BDDMockito.given(storeServiceMock.getAllStores()).willReturn(new HashSet<Store>());
		underTest.setStoreService(storeServiceMock);
		//WHEN
		Set<Store> result = underTest.getAllStores();
		//THEN
		BDDMockito.verify(storeServiceMock).getAllStores();
		Assert.assertEquals(new HashSet<Store>(), result);
	}
	
	@Test
	public void testBasketServicesGettersAndSettersBehaviour() {
		//WHEN
		underTest.setBasketService(basketServiceMock);
		//THEN
		Assert.assertEquals(basketServiceMock, underTest.getBasketService());
	}
	
	@Test
	public void testStoreServicesGettersAndSettersBehaviour() {
		//WHEN
		underTest.setStoreService(storeServiceMock);
		//THEN
		Assert.assertEquals(storeServiceMock, underTest.getStoreService());
	}
	
	@Test
	public void testOrderItemsGettersAndSettersBehaviour() {
		//WHEN
		underTest.setOrderItemService(orderItemServiceMock);
		//THEN
		Assert.assertEquals(orderItemServiceMock, underTest.getOrderItemService());
	}
}