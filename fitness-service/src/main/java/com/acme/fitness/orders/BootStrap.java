package com.acme.fitness.orders;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.users.Roles;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.ProductService;
import com.acme.fitness.users.RoleService;
import com.acme.fitness.users.UserService;

public class BootStrap {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/Spring/*.xml");
		
		UserService userService=ctx.getBean(UserService.class);
		User newUser = userService.addUser("Kicsi Andár Béla", "kicsi007a", "passworda", "kicsi007a@freemail.hu", "203333333", new Date());
		
		RoleService rs = ctx.getBean(RoleService.class);
		rs.addRoleToUser(Roles.Client.toString(), newUser);
		rs.addRoleToUser(Roles.SystemAdmin.toString(), newUser);
		
		//rs.removeRoleFromUser(Roles.Recepcionist.toString(), newUser);
		//rs.removeRoleFromUser(Roles.Recepcionist.toString(), u);
		
		System.out.println(rs.getRolesByUser(newUser));
		
		ProductService ps = ctx.getBean(ProductService.class);
		Date date = new Date();
		date.setTime(1351321321221L);
		System.out.println(date);
		Product test1 = ps.addProduct("labda", "piros", 5600.0, "Nike", new Date(1351321321221L));
		Product test2 = ps.addProduct("kesztyű", "nagy drága lila macsó kesztyű", 11111L, "Drága kesztyű gyártó", new Date());
		System.out.println("Before update "  + test1);
		test1.setName("new name");
		test1.setCreation(new Date(123213412412L));
		test1.setDetails("new detail");
		test1.setPrice(4L);
		test1.setManufacturer("new manufacturer");
		ps.updateProduct(test1);
		System.out.println("After update " + test1);
		try {
			System.out.println(ps.getProductById(5L));
		} catch (FitnessDaoException e) {
			// TODO Auto-generated catch block
			e.fillInStackTrace().printStackTrace();
		}
		System.out.println(rs.getRolesByUser(newUser));
		System.out.println(ps.getProductsByPriceInterval(10000.0, 12000.0));
	}
}
