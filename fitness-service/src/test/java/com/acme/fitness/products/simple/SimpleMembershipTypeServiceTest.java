package com.acme.fitness.products.simple;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acme.fitness.dao.products.MembershipTypeDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.MembershipType;

public class SimpleMembershipTypeServiceTest {

	private SimpleMembershipTypeService underTest;
	
	@Mock
	private MembershipTypeDao membershipTypeDao;
	
	@Mock
	private MembershipType membershipType;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new SimpleMembershipTypeService(membershipTypeDao);
	}
	
	@Test
	public void testNewMembershipTypeShouldReturnProperly() {
		//GIVEN
		MembershipType expected = new MembershipType();
		expected.setExpireDateInDays(1);
		expected.setMaxNumberOfEntries(1);
		expected.setIsIntervally(true);
		expected.setPrice(1.0);
		expected.setDetail("type");
		//WHEN
		MembershipType result = underTest.newMembershipType("type", true, 1, 1, 1.0);
		//THEN
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testSaveMembershipTypeShouldInvokeTheRightMethod() {
		//WHEN
		underTest.saveMembershipType(membershipType);
		//THEN
		BDDMockito.verify(membershipTypeDao).save(membershipType);
	}
	
	@Test
	public void testUpdateMembershipTypeShouldInvokeTheRightMethod() {
		//WHEN
		underTest.updateMembershipType(membershipType);
		//THEN
		BDDMockito.verify(membershipTypeDao).update(membershipType);
		
	}
	
	@Test
	public void testDeleteMembershipTypeShouldInvokeTheRightMethod() {
		//WHEN
		underTest.deleteMembershipType(membershipType);
		//THEN
		BDDMockito.verify(membershipTypeDao).delete(membershipType);
		
	}
	
	@Test
	public void testGetMembershipTypeByIdShouldReturnProperlyWhenTheIdExists() throws FitnessDaoException {
		//GIVEN
		BDDMockito.given(membershipTypeDao.getMembershipTypeById(1L)).willReturn(membershipType);
		//WHEN
		MembershipType result = underTest.getMembershipTypeById(1L);
		//THEN
		Assert.assertEquals(membershipType, result);
		BDDMockito.verify(membershipTypeDao).getMembershipTypeById(1L);
	}
	
	@Test(expected=FitnessDaoException.class)
	public void testGetMembershipTypeByIdShouldThrowExceptionWhenTheIdDoesntExist() throws FitnessDaoException {
		//GIVEN
		BDDMockito.given(membershipTypeDao.getMembershipTypeById(1L)).willThrow(new FitnessDaoException());
		//WHEN
		underTest.getMembershipTypeById(1L);
		//THEN
		BDDMockito.verify(membershipTypeDao).getMembershipTypeById(1L);
	}
	
	@Test
	public void testGetAllMembershipTypesShouldReturnProperly() {
		//GIVEN
		List<MembershipType> expected = new ArrayList<MembershipType>();
		expected.add(membershipType);
		BDDMockito.given(membershipTypeDao.getAllMembershipTypes()).willReturn(expected);
		//WHEN
		List<MembershipType> result = underTest.getAllMembershipTypes();
		//THEN
		Assert.assertEquals(expected, result);
		BDDMockito.verify(membershipTypeDao).getAllMembershipTypes();
	}
}
