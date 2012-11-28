package com.acme.fitness.dao.products.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.MembershipDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;

@Repository
public class HibernateMemberShipDao extends AbstractHibernateGenericDao<Membership> implements MembershipDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Membership> getAllMemberships() {
		return getSession().createCriteria(Membership.class).list();
	}

	@Override
	public Membership getMembershipById(long id) {
		return (Membership) getSession().createCriteria(Membership.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Membership> getMembershipsByOrder(Basket basket) {
		return getSession().createCriteria(Membership.class).add(Restrictions.eq("basket", basket)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Membership> getMembershipsByUser(User user) {
		Criteria crit=getSession().createCriteria(Membership.class).createCriteria("basket", "basket").add(Restrictions.eq("user", user));
		return crit.list();
	}

}
