package com.acme.fitness.dao.users.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.Role;
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

	@Override
	public User getUserByUsername(String username) throws FitnessDaoException {
		User result=(User) getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
		if(result!=null)
			return result;
		else
			throw new FitnessDaoException("User doesn't found with username:"+username);
	}

	@Override
	public User getUserByEmail(String email) throws FitnessDaoException {
		User result=(User) getSession().createCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult();
		if(result!=null)
			return result;
		else
			throw new FitnessDaoException("User doesn't found with email:"+email);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllTrainers() {
		DetachedCriteria detCrit = DetachedCriteria.forClass(Role.class);
		detCrit.setProjection(Property.forName("user")).add(
				Restrictions.eq("name", "Trainer"));

		Criteria crit = getSession().createCriteria(User.class).add(
				Property.forName("id").in(detCrit));
		
		return crit.list();
	}
}
