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

	private RoleDao roleDao;
	
	@Autowired
	public SimpleRoleService(RoleDao roleDao){
		this.roleDao=roleDao;
	}

	@Override
	public void addRoleToUser(String roleName, User user) {
		boolean persistedEarlier = false;
		Role role = new Role(user, roleName);
		for (Role r : roleDao.getRolesByUser(user)) {
			if (r.getName().equals(roleName)) {
				persistedEarlier = true;
			}
		}
		if (!persistedEarlier) {
			roleDao.save(role);
		}
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
}
