package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.UserService;

public class MembershipsBootStrap {

	/**
	 * @param args
	 * @throws FitnessDaoException 
	 */
	public static void main(String[] args) throws FitnessDaoException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/Spring/*.xml");
		UserService userService=ctx.getBean(UserService.class);
		MembershipService mService=ctx.getBean(MembershipService.class);
		BasketDao basketDao=ctx.getBean(BasketDao.class);
		User newUser=userService.addUser("Kicsi Andár Béla", "kicsi007a", "passworda", "kicsi007a@freemail.hu", "203333333", new Date());
		User newUser2=userService.addUser("Kicsi Andár Béla", "kicsi007aaa", "passworda", "kicsi007aa@freemail.hu", "203333333", new Date());
		User newUser3=userService.addUser("Kicsi Andár Béla", "kicsi007aa", "passworda", "kicsi007aaa@freemail.hu", "203333333", new Date());
		
		Basket b1=new Basket(false, newUser);
		Basket b2=new Basket(false, newUser2);
		Basket b3=new Basket(false, newUser3);
		basketDao.save(b1);
		basketDao.save(b2);
		basketDao.save(b3);
		
		mService.addMemberShip(b1, "occasionally", 0, new Date(), 18900.0);
		Membership m1=mService.addMemberShip(b2, "occasionally", 0, new Date(), 18900.0);
		mService.addMemberShip(b2, "occasionally", 0, new Date(), 18900.0);
		Membership m2=mService.addMemberShip(b2, "occasionally", 0, new Date(), 18900.0);
		mService.addMemberShip(b2, "occasionally", 0, new Date(), 18900.0);
		Membership m3=mService.addMemberShip(b3, "occasionally", 0, new Date(), 18900.0);
		List<Membership> result=mService.getMembershipByBasket(b2);
		System.out.println("Membership by basket number: "+result.size()+" values: "+result.toString());
		mService.deleteMembership(m2);
		result=mService.getMembershipByBasket(b2);
		System.out.println("Membership by basket number: "+result.size()+" values: "+result.toString());
		m1.setPrice(21000.0);
		mService.updateMembership(m1);
		mService.increaseClientEntries(m1);
		System.out.println("Membership by id: "+mService.getMembershipById(m1.getId()));
		System.out.println("Membership by User, number:"+mService.getMembershipByUser(newUser).size()+" values:"+mService.getMembershipByUser(newUser));
		System.out.println("Membership by User, number:"+mService.getMembershipByUser(newUser2).size()+" values:"+mService.getMembershipByUser(newUser2));
		
		
		

	}

}
