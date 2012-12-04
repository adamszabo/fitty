package com.acme.fitness.dao.users.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.users.RoleDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;

@Repository
public class HibernateRoleDao extends AbstractHibernateGenericDao<Role> implements RoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() {
		return getSession().createCriteria(Role.class).list();
	}

	@Override
	public Role getRoleById(long id) throws FitnessDaoException {
		Role result = (Role) getSession().createCriteria(Role.class).add(Restrictions.eq("id", id)).uniqueResult();
		if(result!=null)
			return result;
		else
			throw new FitnessDaoException("Role doesn't found with id:"+id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByUser(User user) {
		return (List<Role>)getSession().createCriteria(Role.class).add(Restrictions.eq("user", user)).list();
	}
	
}
