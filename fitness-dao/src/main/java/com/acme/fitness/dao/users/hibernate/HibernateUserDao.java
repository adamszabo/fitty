package com.acme.fitness.dao.users.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;

@Repository
public class HibernateUserDao extends AbstractHibernateGenericDao<User> implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		return getSession().createCriteria(User.class).list();
	}

	@Override
	public User getUserById(long id) throws FitnessDaoException {
		User result=(User) getSession().createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
		if(result!=null)
			return result;
		else
			throw new FitnessDaoException("User doesn't found with id:"+id);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByFullName(String fullName) {
		return getSession().createCriteria(User.class).add(Restrictions.eq("fullName", fullName)).list();
	}
	
}
