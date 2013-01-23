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

import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;

public class SimpleUserServiceTest {
	private SimpleUserService underTest;
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private User userMock;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest=new SimpleUserService(userDao);
	}
	
	@Test
	public void testAddUserShouldReturnProperly(){
		//GIVEN
		Date date=new Date();
		User user=new User("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", date,null, null);
		//WHEN
		User result=underTest.addUser("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", date);
		//THEN
		BDDMockito.verify(userDao).save(user);
		Assert.assertEquals(user, result);
	}
	
	@Test
	public void testDeleteUserShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.deleteUser(userMock);
		//THEN
		BDDMockito.verify(userDao).delete(userMock);
	}
	
	@Test
	public void testUpdateUserShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.updateUser(userMock);
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testGetUserByIdShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		BDDMockito.given(userDao.getUserById(1L)).willReturn(userMock);
		//WHEN
		User result=underTest.getUserById(1L);
		//THEN
		BDDMockito.verify(userDao).getUserById(1L);
		Assert.assertEquals(userMock, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetUserByIdWhenNoResultFoundShouldThrowException() throws FitnessDaoException{
		//GIVEN
		BDDMockito.given(userDao.getUserById(1L)).willThrow(new FitnessDaoException());
		//WHEN
		User result=underTest.getUserById(1L);
		//THEN
		BDDMockito.verify(userDao).getUserById(1L);
		Assert.assertEquals(userMock, result);
	}
	
	@Test
	public void testGetUserByUserNameShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		BDDMockito.given(userDao.getUserByUsername(Mockito.anyString())).willReturn(userMock);
		//WHEN
		User result=underTest.getUserByUserName(Mockito.anyString());
		//THEN
		BDDMockito.verify(userDao).getUserByUsername(Mockito.anyString());
		Assert.assertEquals(userMock, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetUserByUserNameWhenNoResultFoundShouldThrowException() throws FitnessDaoException{
		//GIVEN
		BDDMockito.given(userDao.getUserByUsername(Mockito.anyString())).willThrow(new FitnessDaoException());
		//WHEN
		User result=underTest.getUserByUserName(Mockito.anyString());
		//THEN
		BDDMockito.verify(userDao).getUserByUsername(Mockito.anyString());
		Assert.assertEquals(userMock, result);
	}
	
	@Test
	public void testGetUsersByFullNameSouldReturnProperly(){
		//GIVEN
		List<User> expected=new ArrayList<User>();
		BDDMockito.given(userDao.getUsersByFullName("TESTNAME")).willReturn(expected);
		//WHEN
		List<User> result=underTest.getUsersByFullName("TESTNAME");
		//THEN
		BDDMockito.verify(userDao).getUsersByFullName("TESTNAME");
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testSetNumberOfEntriesShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.setNumberOfEntries(userMock, Mockito.anyInt());
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testAddLastLoginDateShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.addLastLoginDate(userMock, new Date());
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testAddLastLoginIpShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.addLastLoginIp(userMock, Mockito.anyString());
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testSetEnabledShouldReturnProperly(){
		//GIVEN
		//WHEN
		underTest.setEnabled(userMock, Mockito.anyBoolean());
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testGetUserByEmailShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		BDDMockito.given(userDao.getUserByEmail(Mockito.anyString())).willReturn(userMock);
		//WHEN
		User result=underTest.getUserByEmail(Mockito.anyString());
		//THEN
		BDDMockito.verify(userDao).getUserByEmail(Mockito.anyString());
		Assert.assertEquals(userMock, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetUserByEmailWhenNoResultFoundShouldThrowException() throws FitnessDaoException{
		//GIVEN
		BDDMockito.given(userDao.getUserByEmail(Mockito.anyString())).willThrow(new FitnessDaoException());
		//WHEN
		User result=underTest.getUserByEmail(Mockito.anyString());
		//THEN
		BDDMockito.verify(userDao).getUserByEmail(Mockito.anyString());
		Assert.assertEquals(userMock, result);
	}
	
	@Test
	public void testGetAllUsersShouldReturnProperly(){
		//GIVEN
		List<User> expected=new ArrayList<User>();
		BDDMockito.given(userDao.getAllUsers()).willReturn(expected);
		//WHEN
		List<User> result=underTest.getAllUsers();
		//THEN
		BDDMockito.verify(userDao).getAllUsers();
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetUsersByStringParamterShouldReturnProperly(){
		//GIVEN
		List<User> expected=new ArrayList<User>();
		BDDMockito.given(userDao.getUsersByStringParameter(Mockito.anyString(), Mockito.anyString())).willReturn(expected);
		//WHEN
		List<User> result=underTest.getUsersByStringParamter(Mockito.anyString(), Mockito.anyString());
		//THEN
		BDDMockito.verify(userDao).getUsersByStringParameter(Mockito.anyString(), Mockito.anyString());
		Assert.assertEquals(expected, result);
	}
}
