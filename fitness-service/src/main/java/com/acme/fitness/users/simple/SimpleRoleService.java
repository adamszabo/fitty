package com.acme.fitness.users.simple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.users.RoleDao;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.RoleService;

@Service
public class SimpleRoleService implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public void addRoleToUser(String roleName, User user) {
		Role role = new Role(user, roleName);
		roleDao.save(role);
	}

	@Override
	public void removeRoleFromUser(String roleName, User user) {
		List<Role> roles = roleDao.getRolesByUser(user);
		for (Role r : roles) {
			if (r.getName().equals(roleName)) {
				roleDao.delete(r);
			}
		}
	}

	@Override
	public List<Role> getRolesByUser(User user) {
		return roleDao.getRolesByUser(user);
	}
	

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
