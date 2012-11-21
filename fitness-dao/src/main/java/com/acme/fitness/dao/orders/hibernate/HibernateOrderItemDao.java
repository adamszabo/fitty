package com.acme.fitness.dao.orders.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.orders.OrderItemDao;
import com.acme.fitness.domain.OrderItem;

@Repository
public class HibernateOrderItemDao extends AbstractHibernateGenericDao<OrderItem> implements OrderItemDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getAllOrderItem() {
		return getSession().createCriteria(OrderItem.class).list();
	}

	@Override
	public OrderItem getOrderItemById(long id) {
		return (OrderItem) getSession().createCriteria(OrderItem.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

}
