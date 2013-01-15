package com.acme.fitness.domain.products;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ProductImageTest {
	private static final String TEST_STRING_JPG = "jpg";
	private ProductImage underTest;
	
	@Before
	public void setUp(){
		underTest=new ProductImage();
	}
	
	@Test
	public void testGetterSetterBehaviourOfId() {
		underTest.setId(1L);
		Assert.assertEquals(1L, underTest.getId());
	}
	
	@Test
	public void testGetterSetterBehaviourOfMime() {
		underTest.setMime(TEST_STRING_JPG);
		Assert.assertEquals(TEST_STRING_JPG, underTest.getMime());
	}
	
	@Test
	public void testGetterSetterBehaviourOfImage() {
		byte[] image=TEST_STRING_JPG.getBytes();
		underTest.setImage(image);
		Assert.assertEquals(image, underTest.getImage());
	}
	
	@Test
	public void testEqualsWithEqualsVeryfier() {
		EqualsVerifier.forClass(ProductImage.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
}
