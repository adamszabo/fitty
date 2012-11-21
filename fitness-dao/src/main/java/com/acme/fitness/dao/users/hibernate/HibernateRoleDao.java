package com.acme.fitness.dao.users.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.users.RoleDao;
import com.acme.fitness.domain.Role;
import com.acme.fitness.domain.User;

@Repository
public class HibernateRoleDao extends AbstractHibernateGenericDao<Role> implements RoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRole() {
		return getSession().createCriteria(Role.class).list();
	}

	@Override
	public Role getRoleById(long id) {
		return (Role) getSession().createCriteria(Role.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByUser(User user) {
		return (List<Role>)getSession().createCriteria(Role.class).add(Restrictions.eq("user", user)).list();
	}
	
}
