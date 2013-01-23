package com.acme.fitness.domain.orders;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.products.Product;

public class StoreTest {
	private Store underTest;
	
	@Mock
	private Product product;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new Store();
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
	public void testEqualsWithEqualsVeryfier(){
		EqualsVerifier.forClass(Store.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
}
