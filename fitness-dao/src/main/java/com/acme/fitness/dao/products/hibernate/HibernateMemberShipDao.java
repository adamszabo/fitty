package com.acme.fitness.dao.products.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.MemberShipDao;
import com.acme.fitness.domain.MemberShip;
import com.acme.fitness.domain.Order;
import com.acme.fitness.domain.User;

@Repository
public class HibernateMemberShipDao extends AbstractHibernateGenericDao<MemberShip> implements MemberShipDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberShip> getAllMemberShip() {
		return getSession().createCriteria(MemberShip.class).list();
	}

	@Override
	public MemberShip getMemberShipById(long id) {
		return (MemberShip) getSession().createCriteria(MemberShip.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberShip> getMemberShipByOrder(Order order) {
		return getSession().createCriteria(MemberShip.class).add(Restrictions.eq("order", order)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberShip> getMemberShipByUser(User user) {
		Criteria crit=getSession().createCriteria(MemberShip.class).createCriteria("order", "order").add(Restrictions.eq("user", user));
		return crit.list();
	}

}
