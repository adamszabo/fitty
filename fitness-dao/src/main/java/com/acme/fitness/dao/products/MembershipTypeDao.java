package com.acme.fitness.dao.products;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.MembershipType;

public interface MembershipTypeDao extends GenericDao<MembershipType>{
	
	public List<MembershipType> getAllMembershipTypes();
	
	public MembershipType getMembershipTypeById(long id) throws FitnessDaoException;
}
