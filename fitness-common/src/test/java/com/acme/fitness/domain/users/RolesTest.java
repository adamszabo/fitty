package com.acme.fitness.domain.users;


import org.junit.Assert;
import org.junit.Test;

public class RolesTest {

	@Test
	public void testRolesEnum() {
		Assert.assertNotNull(Roles.Client);
		Assert.assertNotNull(Roles.SystemAdmin);
		Assert.assertNotNull(Roles.Recepcionist);
		Assert.assertNotNull(Roles.ProductAdmin);
		Assert.assertNotNull(Roles.Trainer);
	}
}
