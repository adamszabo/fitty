package com.acme.fitness.dao.orders.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.orders.OrderDao;
import com.acme.fitness.domain.Order;
import com.acme.fitness.domain.User;

@Repository
public class HibernateOrderDao extends AbstractHibernateGenericDao<Order> implements OrderDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAllOrder() {
		//List<Order> result=getSession().createCriteria(Order.class).setFetchMode("orderItems", FetchMode.JOIN).list();
		List<Order> result=getSession().createCriteria(Order.class).list();
		return result;
	}

	@Override
	public Order getOrderById(long id) {
		return (Order) getSession().createCriteria(Order.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrderByUser(User user) {
		return getSession().createCriteria(Order.class).add(Restrictions.eq("user", user)).list();
	}

}
