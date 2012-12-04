package com.acme.fitness.users;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.Roles;
import com.acme.fitness.domain.users.User;

public class UsersBootStrap {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/Spring/*.xml");
		UserService userService=ctx.getBean(UserService.class);
		User newUser=userService.addUser("Kicsi Andár Béla", "kicsi007a", "passworda", "kicsi007a@freemail.hu", "203333333", new Date());
		User newUser2=userService.addUser("Kicsi Andár Béla", "kicsi007aaa", "passworda", "kicsi007aa@freemail.hu", "203333333", new Date());
		User newUser3=userService.addUser("Kicsi Andár Béla", "kicsi007aa", "passworda", "kicsi007aaa@freemail.hu", "203333333", new Date());
		
		System.out.println("getAllUsers: "+userService.getAllUsers().size());
		userService.deleteUser(newUser3);
		newUser2.setEmail("XXXXXXXXXXXXXXXX"); newUser2.setUsername("XXXXXXXXXXXXX");
		userService.updateUser(newUser2);
		
		try {
			System.out.println("getUserByUsername: "+userService.getUserByUserName(newUser.getUsername()));
			System.out.println("getUserById:"+userService.getUserById(newUser2.getId()));
			System.out.println("getUserByEmail:"+userService.getUserById(newUser.getId()));
			System.out.println("getUserByFullname: "+userService.getUsersByFullName(newUser2.getFullName()));
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		
		RoleService rs = ctx.getBean(RoleService.class);
		rs.addRoleToUser(Roles.Client.toString(), newUser);
		rs.addRoleToUser(Roles.SystemAdmin.toString(), newUser);
		
		rs.removeRoleFromUser(Roles.Recepcionist.toString(), newUser);
		
		System.out.println(rs.getRolesByUser(newUser));
		
		/**
		 * GeneralUserService test
		 */
		GeneralUsersService gus=ctx.getBean(GeneralUsersService.class);
		User u1=gus.addUser("Kicsi Andár Béla", "kicsi07a", "passworda", "kicsi07a@freemail.hu", "203333333", new Date());
		User u2=gus.addUser("Kicsi Andár Béla", "kicsi07aa", "passworda", "kicsi07aa@freemail.hu", "203333333", new Date());
		User u3=gus.addUser("Kicsi Andár Béla", "kicsi07aaa", "passworda", "kicsi07aaa@freemail.hu", "203333333", new Date());
		System.out.println("All users: "+gus.getAllUsers());
		gus.deleteUser(u2);
		u2.setFullName("XXXXXXXXXXXXXXXXXXXXXXXXXX");
		gus.updateUser(u1);
		try {
			System.out.println("GetUser by Username: "+gus.getUserByUsername(u3.getUsername()));
			System.out.println("GetUser by Email: "+gus.getUserByEmail(u1.getEmail()));
			gus.addLastLoginInfo(u1, "LASTIP", new Date(1000000));
			System.out.println("GetUser by Id: "+gus.getUserById(u1.getId()));
			System.out.println("GetUser by Id: "+gus.getUserById(u2.getId()));
		} catch (FitnessDaoException e) {
			e.fillInStackTrace().printStackTrace();
		}
		gus.addUserRole(Roles.Client.toString(), u3);
		gus.addUserRole(Roles.ProductAdmin.toString(), u3);
		gus.addUserRole(Roles.Recepcionist.toString(), u3);
		System.out.println("All users: "+gus.getAllUsers());
		System.out.println("All roles: "+gus.getRolesbyUser(u3));
		gus.removeUserRole(Roles.Client.toString(), u3);
		gus.removeUserRole(Roles.Recepcionist.toString(), u3);
		System.out.println("All roles: "+gus.getRolesbyUser(u3));
		
	}
}
