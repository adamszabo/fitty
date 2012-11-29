package com.acme.fitness.users;

import java.util.List;

import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;

public interface RoleService {
	void addRoleToUser(String roleName, User user);
	void removeRoleFromUser(String roleName, User user);
	List<Role> getRolesByUser(User user);
}
