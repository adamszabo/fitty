package com.acme.fitness.domain.orders;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.products.Product;

public class OrderItemTest {

	private OrderItem underTest;
	
	@Mock
	private Product product;
	
	@Mock
	private Basket basket;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new OrderItem();
	}

	@Test
	public void testGetterSetterBehaviourOfId() {
		underTest.setId(1L);
		Assert.assertEquals(1L, underTest.getId());
	}

	@Test
	public void testGetterSetterBehaviourOfProduct() {
		underTest.setProduct(product);
		Assert.assertEquals(product, underTest.getProduct());
	}

	@Test
	public void testGetterSetterBehaviourOfQuantity() {
		underTest.setQuantity(0);
		Assert.assertEquals(0, underTest.getQuantity());
	}

	@Test
	public void testGetterSetterBehaviourOfBasket() {
		underTest.setBasket(basket);
		Assert.assertEquals(basket, underTest.getBasket());
	}

	@Test
	public void testEqualsWithEqualsVeryfier() {
		EqualsVerifier.forClass(OrderItem.class)
				.suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NULL_FIELDS).verify();
	}
	
	@Test
	public void testConstructorWithProductQuantityAndBasketArguments() {
		//GIVEN
		OrderItem expected = new OrderItem();
		expected.setBasket(new Basket());
		expected.setQuantity(1);
		expected.setProduct(new Product());
		//WHEN
		underTest = new OrderItem(new Product(), 1, new Basket());
		//THEN
		Assert.assertEquals(expected, underTest);
	}
}
