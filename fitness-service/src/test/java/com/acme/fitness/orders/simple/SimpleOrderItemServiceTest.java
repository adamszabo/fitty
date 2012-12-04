package com.acme.fitness.orders.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.orders.OrderItemDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;

public class SimpleOrderItemServiceTest {
	private SimpleOrderItemService underTest;
	
	@Mock
	private OrderItemDao orderItemDaoMock;
	
	@Mock
	private Basket basketMock;
	
	@Mock
	private OrderItem orderItemMock;
	
	@Mock
	private Product productMock;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest=new SimpleOrderItemService();
	}
	
	@Test
	public void testNewOrderItemShouldReturnProperly() {
		//GIVEN
		Product product = new Product();
		product.setId(1L);
		OrderItem expected = new OrderItem();
		expected.setProduct(product);
		expected.setQuantity(0);
		//WHEN
		OrderItem result = underTest.newOrderItem(product, 0);
		//THEN
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testSaveOrderItemShouldInvokeTheMethodRight() {
		//GIVEN
		Basket basket = new Basket();
		OrderItem orderItem = new OrderItem();
		underTest.setOrderItemDao(orderItemDaoMock);
		OrderItem expectedOrderItem = new OrderItem();
		expectedOrderItem.setBasket(basket);
		//WHEN
		underTest.saveOrderItem(basket, orderItem);
		//THEN
		BDDMockito.verify(orderItemDaoMock).save(expectedOrderItem);
	}
	
	@Test
	public void testSaveNewOrderItemShouldReturnProperly(){
		//GIVEN
		underTest.setOrderItemDao(orderItemDaoMock);
		OrderItem expected=new OrderItem(productMock, 0, basketMock);
		//WHEN
		OrderItem result=underTest.saveNewOrderItem(productMock, 0, basketMock);
		//THEN
		BDDMockito.verify(orderItemDaoMock).save(expected);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testDeleteOrderItemShouldReturnProperly(){
		//GIVEN
		underTest.setOrderItemDao(orderItemDaoMock);
		//WHEN
		underTest.deleteOrderItem(orderItemMock);
		//THEN
		BDDMockito.verify(orderItemDaoMock).delete(orderItemMock);
	}
	
	@Test
	public void testUpdateOrderItemShouldReturnProperly(){
		//GIVEN
		underTest.setOrderItemDao(orderItemDaoMock);
		//WHEN
		underTest.updateOrderItem(orderItemMock);
		//THEN
		BDDMockito.verify(orderItemDaoMock).update(orderItemMock);
	}
	
	@Test
	public void testGetOrderItemByIdWhenHasResultShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setOrderItemDao(orderItemDaoMock);
		BDDMockito.given(orderItemDaoMock.getOrderItemById(Mockito.anyLong())).willReturn(orderItemMock);
		//WHEN
		OrderItem result=underTest.getOrderItemById(Mockito.anyLong());
		//THEN
		BDDMockito.verify(orderItemDaoMock).getOrderItemById(Mockito.anyLong());
		Assert.assertEquals(orderItemMock, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetOrderItemByIdWhenHasNotResultShouldThrowFitnessDaoException() throws FitnessDaoException{
		//GIVEN
		underTest.setOrderItemDao(orderItemDaoMock);
		BDDMockito.given(orderItemDaoMock.getOrderItemById(Mockito.anyLong())).willThrow(new FitnessDaoException());
		//WHEN
		OrderItem result=underTest.getOrderItemById(Mockito.anyLong());
		//THEN
		BDDMockito.verify(orderItemDaoMock).getOrderItemById(Mockito.anyLong());
		Assert.assertEquals(orderItemMock, result);
	}
}
