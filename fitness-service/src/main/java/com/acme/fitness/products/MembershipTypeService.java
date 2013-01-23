package com.acme.fitness.products;

import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.MembershipType;

public interface MembershipTypeService {
	MembershipType newMembershipType(String type, boolean isIntervally, int maxNumberOfEntries, int expireDateInDays, double price);
	void saveMembershipType(MembershipType membershipType);
	void deleteMembershipType(MembershipType membershipType);
	void updateMembershipType(MembershipType membershipType);
	MembershipType getMembershipTypeById(long id) throws FitnessDaoException;
	List<MembershipType> getAllMembershipTypes();
}
