package com.acme.fitness.users;

import java.util.List;

import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;

public interface RoleService {
	void addRoleToUser(Role role, User user);
	void removeRoleFromUser(Role role, User user);
	List<Role> getRolesByUser(User user);
}
