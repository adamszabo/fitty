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
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.Roles;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.TrainingTypeService;

public class SimpleRoleServiceTest {
	
	private SimpleRoleService underTest;
	
	@Mock
	private RoleDao roleDao;
	
	@Mock
	private TrainingTypeService trainingTypeService;
	
	@Mock
	private TrainingType trainingType;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleRoleService(roleDao, trainingTypeService);
	}
	
	@Test
	public void testAddRoleToUserShouldRunProperly(){
		//GIVEN
		String expectedRoleName = Roles.Client.toString();
		User expectedUser = new User();
		expectedUser.setUsername("Test");
		Role role = new Role(expectedUser, expectedRoleName);
		//WHEN
		underTest.addRoleToUser(expectedRoleName, expectedUser);
		//THEN
		BDDMockito.verify(roleDao).save(role);
	}
	
	@Test
	public void testRemoveRoleFromUserShouldInvokeTheDeleteMethodProperly(){
		//GIVEN
		String expectedRoleName = Roles.ProductAdmin.toString();
		User expectedUser = new User();
		expectedUser.setUsername("Test");
		List<Role> roles = new ArrayList<Role>();
		Role expectedRole = new Role(expectedUser, expectedRoleName);
		Role notExpectedRole = new Role(expectedUser, Roles.Client.toString());
		roles.add(expectedRole);
		roles.add(notExpectedRole);
		BDDMockito.given(roleDao.getRolesByUser(expectedUser)).willReturn(roles);
		//WHEN
		underTest.removeRoleFromUser(expectedRoleName, expectedUser);
		//THEN
		BDDMockito.verify(roleDao).delete(expectedRole);
	}
	
	@Test
	public void testRemoveRoleFromUserShouldInvokeTheDeleteTrainingTypeMethodWhenTheRoleNameIsTrainer() throws FitnessDaoException {
		//GIVEN
		User expectedUser = new User();
		BDDMockito.given(trainingTypeService.getTrainingTypeByTrainer(expectedUser)).willReturn(trainingType);
		List<Role> roles = new ArrayList<Role>();
		Role expectedRole = new Role(expectedUser, Roles.Trainer.toString());
		roles.add(expectedRole);
		BDDMockito.given(roleDao.getRolesByUser(expectedUser)).willReturn(roles);
		//WHEN
		underTest.removeRoleFromUser(Roles.Trainer.toString(), expectedUser);
		//THEN
		BDDMockito.verify(trainingTypeService).deleteTrainingType(trainingType);
		BDDMockito.verify(trainingTypeService).getTrainingTypeByTrainer(expectedUser);
	}
	
	@Test
	public void testGetRolesByUserShouldReturnTheRightList(){
		//GIVEN
		User expectedUser = new User();
		expectedUser.setUsername("Test");
		List<Role> expectedRoles = new ArrayList<Role>();
		Role r1 = new Role(expectedUser, Roles.Recepcionist.toString());
		Role r2 = new Role(expectedUser, Roles.Client.toString());
		expectedRoles.add(r1);
		expectedRoles.add(r2);
		BDDMockito.given(roleDao.getRolesByUser(expectedUser)).willReturn(expectedRoles);
		//WHEN
		List<Role> result = underTest.getRolesByUser(expectedUser);
		//THEN
		Assert.assertEquals(expectedRoles, result);
		BDDMockito.verify(roleDao).getRolesByUser(expectedUser);
	}
}
