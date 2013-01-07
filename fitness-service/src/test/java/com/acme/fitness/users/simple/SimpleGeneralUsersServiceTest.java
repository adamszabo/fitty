package com.acme.fitness.users.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.RoleService;
import com.acme.fitness.users.UserService;

public class SimpleGeneralUsersServiceTest {
	private static final String TESTSTRING = "TESTSTRING";

	private SimpleGeneralUsersService underTest;
	
	@Mock
	private UserService userServiceMock;
	
	@Mock
	private RoleService roleServiceMock;
	
	@Mock
	private User userMock;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest=new SimpleGeneralUsersService();
	}
	
	@Test
	public void testAddUserShouldReturnProperly(){
		//GIVEN
		underTest.setUserService(userServiceMock);
		Date date=new Date();
		User user=new User("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", date,null, null);
		BDDMockito.given(userServiceMock.addUser("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", new Date())).willReturn(user);
		//WHEN
		User result=underTest.addUser("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", date);
		//THEN
		BDDMockito.verify(userServiceMock).addUser("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", date);
		Assert.assertEquals(user, result);
		
	}
	
	@Test
	public void testGetAllUsersShouldReturnProperly(){
		//GIVEN
		underTest.setUserService(userServiceMock);
		underTest.setRoleService(roleServiceMock);
		List<User> users=new ArrayList<User>();
		BDDMockito.given(userServiceMock.getAllUsers()).willReturn(users);
		//WHEN
		List<User> result=underTest.getAllUsers();
		//THEN
		BDDMockito.verify(userServiceMock).getAllUsers();
		Assert.assertEquals(users, result);
	}
	@Test
	public void testAddUserRoleShouldReturnProperly(){
		//GIVEN
		underTest.setRoleService(roleServiceMock);
		//WHEN
		underTest.addUserRole(TESTSTRING, userMock);
		//THEN
		BDDMockito.verify(roleServiceMock).addRoleToUser(TESTSTRING, userMock);
	}
	
	@Test
	public void testGetRemoveUserRoleShouldReturnProperly(){
		//GIVEN
		underTest.setRoleService(roleServiceMock);
		//WHEN
		underTest.removeUserRole(TESTSTRING, userMock);
		//THEN
		BDDMockito.verify(roleServiceMock).removeRoleFromUser(TESTSTRING, userMock);
	}
	
	@Test
	public void testAddLastLoginInfoShouldReturnProperly(){
		//GIVEN
		underTest.setUserService(userServiceMock);
		Date date=new Date();
		//WHEN
		underTest.addLastLoginInfo(userMock, TESTSTRING, date);
		//THEN
		BDDMockito.verify(userServiceMock).addLastLoginIp(userMock, TESTSTRING);
		BDDMockito.verify(userServiceMock).addLastLoginDate(userMock, date);
		
	}
	
	@Test
	public void testGetUserByUsernameWhenHasResultShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserByUserName(TESTSTRING)).willReturn(userMock);
		//WHEN
		User result=underTest.getUserByUsername(TESTSTRING);
		//THEN
		BDDMockito.verify(userServiceMock).getUserByUserName(TESTSTRING);
		Assert.assertEquals(userMock, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetUserByUsernameWhenDoesNotHaveResultShouldThrowFitnessDaoException() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserByUserName(TESTSTRING)).willThrow(new FitnessDaoException());
		//WHEN
		User result=underTest.getUserByUsername(TESTSTRING);
		//THEN
		BDDMockito.verify(userServiceMock).getUserByUserName(TESTSTRING);
		Assert.assertEquals(userMock, result);
	}
	
	@Test
	public void testGetUserByEmailWhenHasResultShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserByEmail(TESTSTRING)).willReturn(userMock);
		//WHEN
		User result=underTest.getUserByEmail(TESTSTRING);
		//THEN
		BDDMockito.verify(userServiceMock).getUserByEmail(TESTSTRING);
		Assert.assertEquals(userMock, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetUserByEmailWhenDoesNotHaveResultShouldThrowFitnessDaoException() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserByEmail(TESTSTRING)).willThrow(new FitnessDaoException());
		//WHEN
		User result=underTest.getUserByEmail(TESTSTRING);
		//THEN
		BDDMockito.verify(userServiceMock).getUserByEmail(TESTSTRING);
		Assert.assertEquals(userMock, result);
	}
	
	@Test
	public void testIsLoginValidByUserWhenUserRecordExistsInDatabaseAndNotLoggedInShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserByUserName(TESTSTRING)).willReturn(userMock);
		//WHEN
		boolean result=underTest.isLoginValidByUser(TESTSTRING, TESTSTRING, false);
		//THEN
		BDDMockito.verify(userServiceMock).getUserByUserName(TESTSTRING);
		Assert.assertEquals(true, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testIsLoginValidByUserWhenUserRecordDoesNotExistInDatabaseShouldThrowException() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserByUserName(TESTSTRING)).willThrow(new FitnessDaoException());
		//WHEN
		boolean result=underTest.isLoginValidByUser(TESTSTRING, TESTSTRING, false);
		//THEN
		BDDMockito.verify(userServiceMock).getUserByUserName(TESTSTRING);
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void testIsLoginValidByUserWhenUserRecordExistsInDatabaseAndLoggedInWithSameIpShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserByUserName(TESTSTRING)).willReturn(userMock);
		BDDMockito.given(userMock.getLastLoginIp()).willReturn(TESTSTRING);
		//WHEN
		boolean result=underTest.isLoginValidByUser(TESTSTRING, TESTSTRING, true);
		//THEN
		BDDMockito.verify(userServiceMock).getUserByUserName(TESTSTRING);
		BDDMockito.verify(userMock).getLastLoginIp();
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void testIsLoginValidByUserWhenUserRecordExistsInDatabaseAndLoggedInWithDifferentIpShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserByUserName(TESTSTRING)).willReturn(userMock);
		BDDMockito.given(userMock.getLastLoginIp()).willReturn("asdf");
		//WHEN
		boolean result=underTest.isLoginValidByUser(TESTSTRING, TESTSTRING, true);
		//THEN
		BDDMockito.verify(userServiceMock).getUserByUserName(TESTSTRING);
		BDDMockito.verify(userMock).getLastLoginIp();
		Assert.assertEquals(false, result);
	}
	
	@Test
	public void testDeleteUserShouldReturnProperly(){
		//GIVEN
		underTest.setUserService(userServiceMock);
		//WHEN
		underTest.deleteUser(userMock);
		//THEN
		BDDMockito.verify(userServiceMock).deleteUser(userMock);
	}
	
	@Test
	public void testUpdateUserShouldReturnProperly(){
		//GIVEN
		underTest.setUserService(userServiceMock);
		//WHEN
		underTest.updateUser(userMock);
		//THEN
		BDDMockito.verify(userServiceMock).updateUser(userMock);
	}
	
	@Test
	public void testGetRolesByUserShouldReturnProperly(){
		//GIVEN
		underTest.setRoleService(roleServiceMock);
		List<Role> expected=new ArrayList<Role>();
		BDDMockito.given(roleServiceMock.getRolesByUser(userMock)).willReturn(expected);
		//WHEN
		List<Role> result=underTest.getRolesbyUser(userMock);
		//THEN
		BDDMockito.verify(roleServiceMock).getRolesByUser(userMock);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetRolesByIdShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setUserService(userServiceMock);
		BDDMockito.given(userServiceMock.getUserById(Mockito.anyLong())).willReturn(userMock);
		//WHEN
		User result=underTest.getUserById(Mockito.anyLong());
		//THEN
		BDDMockito.verify(userServiceMock).getUserById(Mockito.anyLong());
		Assert.assertEquals(userMock, result);
	}
	
	@Test
	public void testUserServicesGetterAndSetterBehaviour() {
		//WHEN
		underTest.setUserService(userServiceMock);
		//THEN
		Assert.assertEquals(userServiceMock, underTest.getUserService());
	}
	
	@Test
	public void testRoleServicesGetterAndSetterBehaviour() {
		//WHEN
		underTest.setRoleService(roleServiceMock);
		//THEN
		Assert.assertEquals(roleServiceMock, underTest.getRoleService());
	}
}
