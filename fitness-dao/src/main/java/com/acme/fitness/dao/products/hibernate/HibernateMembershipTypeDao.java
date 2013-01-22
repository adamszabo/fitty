package com.acme.fitness.dao.products.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.MembershipTypeDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.MembershipType;

public class HibernateMembershipTypeDao extends AbstractHibernateGenericDao<MembershipType> implements MembershipTypeDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<MembershipType> getAllMembershipTypes() {
		return getSession().createCriteria(MembershipType.class).list();
	}

	@Override
	public MembershipType getMembershipTypeById(long id)
			throws FitnessDaoException {
		MembershipType result = (MembershipType) getSession().createCriteria(MembershipType.class).add(Restrictions.eq("id", id)).uniqueResult();
		if(result == null) {
			throw new FitnessDaoException("MembershipType doesn't found with id: " + id);
		}
		return result;
	}
	

}
