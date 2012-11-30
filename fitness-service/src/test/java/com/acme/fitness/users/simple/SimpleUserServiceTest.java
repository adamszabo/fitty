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
		underTest=new SimpleUserService();
	}
	
	@Test
	public void testAddUserShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		User user=new User("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", new Date(),null, null);
		//WHEN
		User result=underTest.addUser("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", new Date());
		//THEN
		BDDMockito.verify(userDao).save(user);
		Assert.assertEquals(user, result);
	}
	
	@Test
	public void testDeleteUserShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		//WHEN
		underTest.deleteUser(userMock);
		//THEN
		BDDMockito.verify(userDao).delete(userMock);
	}
	
	@Test
	public void testUpdateUserShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		//WHEN
		underTest.updateUser(userMock);
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testGetUserByIdShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setUserDao(userDao);
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
		underTest.setUserDao(userDao);
		BDDMockito.given(userDao.getUserById(1L)).willThrow(new FitnessDaoException());
		//WHEN
		User result=underTest.getUserById(1L);
		//THEN
		BDDMockito.verify(userDao).getUserById(1L);
		Assert.assertEquals(userMock, result);
	}
	
	@Test
	public void testGetUsersByFullNameSouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
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
		underTest.setUserDao(userDao);
		//WHEN
		underTest.setNumberOfEntries(userMock, Mockito.anyInt());
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testAddLastLoginDateShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		//WHEN
		underTest.addLastLoginDate(userMock, new Date());
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testAddLastLoginIpShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		//WHEN
		underTest.addLastLoginIp(userMock, Mockito.anyString());
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void testSetEnabledShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		//WHEN
		underTest.setEnabled(userMock, Mockito.anyBoolean());
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
}
