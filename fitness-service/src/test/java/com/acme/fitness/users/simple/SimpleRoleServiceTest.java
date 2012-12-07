package com.acme.fitness.users.simple;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.users.RoleDao;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.Roles;
import com.acme.fitness.domain.users.User;

public class SimpleRoleServiceTest {
	
	private SimpleRoleService underTest;
	
	@Mock
	private RoleDao roleDao;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleRoleService();
	}
	
	@Test
	public void testAddRoleToUserShouldRunProperly(){
		String expectedRoleName = Roles.Client.toString();
		User expectedUser = new User();
		expectedUser.setUsername("Test");
		Role role = new Role(expectedUser, expectedRoleName);
		underTest.setRoleDao(roleDao);
		underTest.addRoleToUser(expectedRoleName, expectedUser);
		BDDMockito.verify(roleDao).save(role);
	}
	
	@Test
	public void testRemoveRoleFromUserShouldInvokeTheDeleteMethodProperly(){
		String expectedRoleName = Roles.ProductAdmin.toString();
		User expectedUser = new User();
		expectedUser.setUsername("Test");
		List<Role> roles = new ArrayList<Role>();
		Role expectedRole = new Role(expectedUser, expectedRoleName);
		Role notExpectedRole = new Role(expectedUser, Roles.Client.toString());
		roles.add(expectedRole);
		roles.add(notExpectedRole);
		BDDMockito.given(roleDao.getRolesByUser(expectedUser)).willReturn(roles);
		underTest.setRoleDao(roleDao);
		underTest.removeRoleFromUser(expectedRoleName, expectedUser);
		BDDMockito.verify(roleDao).delete(expectedRole);
	}
	
	@Test
	public void testGetRolesByUserShouldReturnTheRightList(){
		User expectedUser = new User();
		expectedUser.setUsername("Test");
		List<Role> expectedRoles = new ArrayList<Role>();
		Role r1 = new Role(expectedUser, Roles.Recepcionist.toString());
		Role r2 = new Role(expectedUser, Roles.Client.toString());
		expectedRoles.add(r1);
		expectedRoles.add(r2);
		BDDMockito.given(roleDao.getRolesByUser(expectedUser)).willReturn(expectedRoles);
		underTest.setRoleDao(roleDao);
		List<Role> result = underTest.getRolesByUser(expectedUser); 
		Assert.assertEquals(expectedRoles, result);
		BDDMockito.verify(roleDao).getRolesByUser(expectedUser);
	}
	
	@Test
	public void testRoleDaosGetterAndSetterBehaviour() {
		//WHEN
		underTest.setRoleDao(roleDao);
		//THEn
		Assert.assertEquals(roleDao, underTest.getRoleDao());
	}
	
	
}
