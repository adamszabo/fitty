package com.acme.fitness.orders;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.domain.users.Roles;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.ProductService;
import com.acme.fitness.users.RoleService;

public class BootStrap {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/Spring/*.xml");
		User u=new User("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", new Date(1100), new Date(1100), "127.0.0.1");
		
		
		UserDao userDao=ctx.getBean(UserDao.class);
		userDao.save(u);
		
		RoleService rs = ctx.getBean(RoleService.class);
		rs.addRoleToUser(Roles.Client.toString(), u);
		rs.addRoleToUser(Roles.SystemAdmin.toString(), u);
		
		rs.removeRoleFromUser(Roles.Recepcionist.toString(), u);
		
		System.out.println(rs.getRolesByUser(u));
		
		ProductService ps = ctx.getBean(ProductService.class);
		Date date = new Date();
		date.setTime(1351321321221L);
		System.out.println(date);
		ps.addProduct("labda", "piros", 5600.0, "Nike", new Date(1351321321221L));
	}
}
