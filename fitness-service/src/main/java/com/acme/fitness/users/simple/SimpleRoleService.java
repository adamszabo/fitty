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
	public List<Role> getRolesByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeRoleFromUser(String roleName, User user) {
		// TODO Auto-generated method stub
		
	}

}
