package com.acme.fitness.dao.users.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.domain.User;

@Repository
public class HibernateUserDao extends AbstractHibernateGenericDao<User> implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUser() {
		return getSession().createCriteria(User.class).list();
	}

	@Override
	public User getUserById(long id) {
		return (User) getSession().createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
	}
	
}
