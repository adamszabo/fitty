package com.acme.fitness.products.simple;

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

import com.acme.fitness.dao.products.MembershipDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;

public class SimpleMembershipServiceTest {
	private SimpleMembershipService underTest;
	
	@Mock
	private MembershipDao membershipDaoMock;
	
	@Mock
	private Basket basketMock;
	
	@Mock
	private Membership membershipMock;
	
	@Mock
	private User userMock;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		underTest=new SimpleMembershipService();
	}
	
	@Test
	public void testAddMembershipShouldReturnProperly(){
		//GIVEN
		underTest.setMembershipDao(membershipDaoMock);
		Membership expected=new Membership("TESTSTRING", 0, 0, new Date(), 0.0, basketMock);
		//WHEN
		Membership result=underTest.addMemberShip(basketMock, "TESTSTRING", 0, new Date(), 0.0);
		//THEN
		BDDMockito.verify(membershipDaoMock).save(expected);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testDeleteMembershipShouldReturnProperly(){
		//GIVEN
		underTest.setMembershipDao(membershipDaoMock);
		//WHEN
		underTest.deleteMembership(membershipMock);
		//THEN
		BDDMockito.verify(membershipDaoMock).delete(membershipMock);
	}
	
	@Test
	public void testUpdateMembershipShouldReturnProperly(){
		//GIVEN
		underTest.setMembershipDao(membershipDaoMock);
		//WHEN
		underTest.updateMembership(membershipMock);
		//THEN
		BDDMockito.verify(membershipDaoMock).update(membershipMock);
	}
	
	@Test
	public void testGetMembershipByIdWhenHasResultShouldReturnProperly() throws FitnessDaoException{
		//GIVEN
		underTest.setMembershipDao(membershipDaoMock);
		BDDMockito.given(membershipDaoMock.getMembershipById(Mockito.anyLong())).willReturn(membershipMock);
		//WHEN
		Membership result=underTest.getMembershipById(Mockito.anyLong());
		//THEN
		BDDMockito.verify(membershipDaoMock).getMembershipById(Mockito.anyLong());
		Assert.assertEquals(membershipMock, result);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetMembershipByIdWhenHasResultShouldThrowFitnessDaoException() throws FitnessDaoException{
		//GIVEN
		underTest.setMembershipDao(membershipDaoMock);
		BDDMockito.given(membershipDaoMock.getMembershipById(Mockito.anyLong())).willThrow(new FitnessDaoException());
		//WHEN
		Membership result=underTest.getMembershipById(Mockito.anyLong());
		//THEN
		BDDMockito.verify(membershipDaoMock).getMembershipById(Mockito.anyLong());
		Assert.assertEquals(membershipMock, result);
	}
	
	@Test
	public void testGetMembershipByBasketShouldReturnProperly(){
		//GIVEN
		underTest.setMembershipDao(membershipDaoMock);
		List<Membership> expected=new ArrayList<Membership>();
		BDDMockito.given(membershipDaoMock.getMembershipsByOrder(basketMock)).willReturn(expected);
		//WHEN
		List<Membership> result=underTest.getMembershipByBasket(basketMock);
		//THEN
		BDDMockito.verify(membershipDaoMock).getMembershipsByOrder(basketMock);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testGetMembershipByUserShouldReturnProperly(){
		//GIVEN
		underTest.setMembershipDao(membershipDaoMock);
		List<Membership> expected=new ArrayList<Membership>();
		BDDMockito.given(membershipDaoMock.getMembershipsByUser(userMock)).willReturn(expected);
		//WHEN
		List<Membership> result=underTest.getMembershipByUser(userMock);
		//THEN
		BDDMockito.verify(membershipDaoMock).getMembershipsByUser(userMock);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testincreaseClientEntriesShouldReturnProperly(){
		//GIVEN
		underTest.setMembershipDao(membershipDaoMock);
		//WHEN
		underTest.increaseClientEntries(membershipMock);
		//THEN
		BDDMockito.verify(membershipMock).setNumberOfEntries(Mockito.anyInt());
		BDDMockito.verify(membershipMock).getNumberOfEntries();
		BDDMockito.verify(membershipDaoMock).update(membershipMock);
	}
}