package com.acme.fitness.dao.users;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.Role;
import com.acme.fitness.domain.User;

public interface RoleDao extends GenericDao<Role> {

	public List<Role> getAllRole();

	public Role getRoleById(long id);

	public List<Role> getRolesByUser(User user);
}
