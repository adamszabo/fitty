package com.acme.fitness.users.simple;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.users.UserDao;
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
	public void addUserShouldReturnProperly(){
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
	public void deleteUserShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		//WHEN
		underTest.deleteUser(userMock);
		//THEN
		BDDMockito.verify(userDao).delete(userMock);
	}
	
	@Test
	public void updateUserShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		//WHEN
		underTest.updateUser(userMock);
		//THEN
		BDDMockito.verify(userDao).update(userMock);
	}
	
	@Test
	public void getUserByIdShouldReturnProperly(){
		//GIVEN
		underTest.setUserDao(userDao);
		BDDMockito.given(userDao.getUserById(1L)).willReturn(userMock);
		//WHEN
		User result=underTest.getUserById(1L);
		//THEN
		BDDMockito.verify(userDao).getUserById(1L);
		Assert.assertEquals(userMock, result);
	}
	
	public void getUserByIdWhenNoResultFoundShouldThrowException(){
		
	}
}
