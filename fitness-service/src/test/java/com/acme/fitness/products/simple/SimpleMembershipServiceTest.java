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
	private static final int MAX_NUMBER_OF_ENTRIES = 10;

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
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleMembershipService(membershipDaoMock);
	}

	@Test
	public void testNewMembershipShouldReturnTheRightObject() {
		// GIVEN
		boolean expectedIntervally = true;
		String expectedType = "type";
		int expectedMaxEntries = 10;
		Date expectedDate = new Date();
		double expectedPrice = 1230.0;
		Membership expected = new Membership(expectedIntervally, expectedType, 0,
				expectedMaxEntries, expectedDate, expectedDate, expectedPrice, null);
		// WHEN
		Membership result = underTest.newMemberShip(expectedIntervally, expectedType,
				expectedMaxEntries, expectedDate, expectedDate, expectedPrice);
		// THEN
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testSaveMembershipShouldInvokeTheRightMethod() {
		// GIVEN
		Basket basket = new Basket();
		basket.setId(1L);
		Membership membership = new Membership();
		membership.setId(2L);
		Membership expectedMembership = new Membership();
		expectedMembership.setId(2L);
		expectedMembership.setBasket(basket);
		// WHEN
		underTest.saveMemberShip(basket, membership);
		// THEN
		BDDMockito.verify(membershipDaoMock).save(expectedMembership);
	}
	
	@Test
	public void testSaveNewMembershipShouldReturnProperly() {
		// GIVEN
		Date date = new Date();
		Membership expected = new Membership(true, "TESTSTRING", 0, 0, date, date,
				0.0, basketMock);
		// WHEN
		Membership result = underTest.saveNewMemberShip(true, basketMock,
				"TESTSTRING", 0, date, date, 0.0);
		// THEN
		BDDMockito.verify(membershipDaoMock).save(expected);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testDeleteMembershipShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.deleteMembership(membershipMock);
		// THEN
		BDDMockito.verify(membershipDaoMock).delete(membershipMock);
	}

	@Test
	public void testUpdateMembershipShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.updateMembership(membershipMock);
		// THEN
		BDDMockito.verify(membershipDaoMock).update(membershipMock);
	}

	@Test
	public void testGetMembershipByIdWhenHasResultShouldReturnProperly()
			throws FitnessDaoException {
		// GIVEN
		BDDMockito
				.given(membershipDaoMock.getMembershipById(Mockito.anyLong()))
				.willReturn(membershipMock);
		// WHEN
		Membership result = underTest.getMembershipById(Mockito.anyLong());
		// THEN
		BDDMockito.verify(membershipDaoMock).getMembershipById(
				Mockito.anyLong());
		Assert.assertEquals(membershipMock, result);
	}

	@Test(expected = FitnessDaoException.class)
	public void testGetMembershipByIdWhenHasResultShouldThrowFitnessDaoException()
			throws FitnessDaoException {
		// GIVEN
		BDDMockito
				.given(membershipDaoMock.getMembershipById(Mockito.anyLong()))
				.willThrow(new FitnessDaoException());
		// WHEN
		Membership result = underTest.getMembershipById(Mockito.anyLong());
		// THEN
		BDDMockito.verify(membershipDaoMock).getMembershipById(
				Mockito.anyLong());
		Assert.assertEquals(membershipMock, result);
	}

	@Test
	public void testGetMembershipByBasketShouldReturnProperly() {
		// GIVEN
		List<Membership> expected = new ArrayList<Membership>();
		BDDMockito.given(membershipDaoMock.getMembershipsByOrder(basketMock))
				.willReturn(expected);
		// WHEN
		List<Membership> result = underTest.getMembershipByBasket(basketMock);
		// THEN
		BDDMockito.verify(membershipDaoMock).getMembershipsByOrder(basketMock);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetMembershipByUserShouldReturnProperly() {
		// GIVEN
		List<Membership> expected = new ArrayList<Membership>();
		BDDMockito.given(membershipDaoMock.getMembershipsByUser(userMock))
				.willReturn(expected);
		// WHEN
		List<Membership> result = underTest.getMembershipByUser(userMock);
		// THEN
		BDDMockito.verify(membershipDaoMock).getMembershipsByUser(userMock);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testincreaseClientEntriesShouldReturnProperly() {
		// GIVEN
		// WHEN
		underTest.increaseClientEntries(membershipMock);
		// THEN
		BDDMockito.verify(membershipMock).setNumberOfEntries(Mockito.anyInt());
		BDDMockito.verify(membershipMock).getNumberOfEntries();
		BDDMockito.verify(membershipDaoMock).update(membershipMock);
	}
	
	@Test
	public void testIsValidShouldReturnTrueWhenMembershipIsIntevallyAndTheDateIsBetweenStartAndEndDates() {
		//GIVEN
		Date date = new Date();
		BDDMockito.given(membershipMock.getIsIntervally()).willReturn(true);
		BDDMockito.given(membershipMock.getStartDate()).willReturn(new Date(date.getTime() - 1000*60*60));
		BDDMockito.given(membershipMock.getExpireDate()).willReturn(new Date(date.getTime() + 1000*60*60));
		//WHEN
		boolean result = underTest.isValid(membershipMock, date);
		//THEN
		BDDMockito.verify(membershipMock).getIsIntervally();
		BDDMockito.verify(membershipMock).getStartDate();
		BDDMockito.verify(membershipMock).getExpireDate();
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void testIsValidShouldReturnFalseWhenMembershipIsIntevallyAndTheDateIsBeforeTheValidIntervally() {
		//GIVEN
		Date date = new Date();
		BDDMockito.given(membershipMock.getIsIntervally()).willReturn(true);
		BDDMockito.given(membershipMock.getStartDate()).willReturn(new Date(date.getTime() + 10000*60*60));
		BDDMockito.given(membershipMock.getExpireDate()).willReturn(new Date(date.getTime() + 1000*60*60));
		//WHEN
		boolean result = underTest.isValid(membershipMock, date);
		//THEN
		BDDMockito.verify(membershipMock).getIsIntervally();
		BDDMockito.verify(membershipMock).getStartDate();
		BDDMockito.verify(membershipMock).getExpireDate();
		Assert.assertEquals(false, result);
	}
	
	@Test
	public void testIsValidShouldReturnFalseWhenMembershipIsIntevallyAndTheDateIsAfterTheValidIntervally() {
		//GIVEN
		Date date = new Date();
		BDDMockito.given(membershipMock.getIsIntervally()).willReturn(true);
		BDDMockito.given(membershipMock.getStartDate()).willReturn(new Date(date.getTime() - 10000*60*60));
		BDDMockito.given(membershipMock.getExpireDate()).willReturn(new Date(date.getTime() - 1000*60*60));
		//WHEN
		boolean result = underTest.isValid(membershipMock, date);
		//THEN
		BDDMockito.verify(membershipMock).getIsIntervally();
		BDDMockito.verify(membershipMock).getExpireDate();
		Assert.assertEquals(false, result);
	}
	
	@Test
	public void testIsValidShouldReturnTrueWhenMembershipIsNOTIntevallyAndTheNumberOfEntriesLessThanMaxNumberOfEntries() {
		//GIVEN
		Date date = new Date();
		BDDMockito.given(membershipMock.getIsIntervally()).willReturn(false);
		BDDMockito.given(membershipMock.getMaxNumberOfEntries()).willReturn(MAX_NUMBER_OF_ENTRIES);
		BDDMockito.given(membershipMock.getNumberOfEntries()).willReturn(MAX_NUMBER_OF_ENTRIES-1);
		//WHEN
		boolean result = underTest.isValid(membershipMock, date);
		//THEN
		BDDMockito.verify(membershipMock).getIsIntervally();
		BDDMockito.verify(membershipMock).getMaxNumberOfEntries();
		BDDMockito.verify(membershipMock).getNumberOfEntries();
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void testIsValidShouldReturnFalseWhenMembershipIsNOTIntevallyAndTheNumberOfEntriesGreaterThanMaxNumberOfEntries() {
		//GIVEN
		Date date = new Date();
		BDDMockito.given(membershipMock.getIsIntervally()).willReturn(false);
		BDDMockito.given(membershipMock.getMaxNumberOfEntries()).willReturn(MAX_NUMBER_OF_ENTRIES);
		BDDMockito.given(membershipMock.getNumberOfEntries()).willReturn(MAX_NUMBER_OF_ENTRIES+1);
		//WHEN
		boolean result = underTest.isValid(membershipMock, date);
		//THEN
		BDDMockito.verify(membershipMock).getIsIntervally();
		BDDMockito.verify(membershipMock).getMaxNumberOfEntries();
		BDDMockito.verify(membershipMock).getNumberOfEntries();
		Assert.assertEquals(false, result);
	}
}
