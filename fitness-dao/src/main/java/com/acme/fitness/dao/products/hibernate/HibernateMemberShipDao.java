package com.acme.fitness.dao.products.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.MemberShipDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.MemberShip;
import com.acme.fitness.domain.users.User;

@Repository
public class HibernateMemberShipDao extends AbstractHibernateGenericDao<MemberShip> implements MemberShipDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberShip> getAllMemberShips() {
		return getSession().createCriteria(MemberShip.class).list();
	}

	@Override
	public MemberShip getMemberShipById(long id) {
		return (MemberShip) getSession().createCriteria(MemberShip.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberShip> getMemberShipsByOrder(Basket basket) {
		return getSession().createCriteria(MemberShip.class).add(Restrictions.eq("basket", basket)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberShip> getMemberShipsByUser(User user) {
		Criteria crit=getSession().createCriteria(MemberShip.class).createCriteria("basket", "basket").add(Restrictions.eq("user", user));
		return crit.list();
	}

}
