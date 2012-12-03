package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.UserService;

public class ProductsBootStrap {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"META-INF/Spring/*.xml");
		
		/*
		 * Product Service Test
		 */
		ProductService ps = ctx.getBean(ProductService.class);
		Date date = new Date();
		date.setTime(1351321321221L);
		System.out.println(date);
		Product test1 = ps.addProduct("labda", "piros", 5600.0, "Nike", new Date(1351321321221L));
		@SuppressWarnings("unused")
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
			e.fillInStackTrace().printStackTrace();
		}
		System.out.println(ps.getProductsByPriceInterval(10000.0, 12000.0));

		/*
		 * Training Service Test
		 */
		TrainingService ts = ctx.getBean(TrainingService.class);
		UserService us = ctx.getBean(UserService.class);
		User client = us.addUser("Thomas", "thoom", "1234", "thomi@email.hu", "+34864611", new Date());
		User trainer = us.addUser("Feri", "ferko", "pass", "feri@email.hu", "+361646511", new Date());
		Basket basket = new Basket(false, client);
		BasketDao basketDao = ctx.getBean(BasketDao.class);
		basketDao.save(basket);
		Training training = ts.addTraining(trainer, client, date, basket);
		ts.deleteTraining(training);
		training = ts.addTraining(trainer, client, date, basket);
		trainer.setUsername("ferenc");
		training.setTrainer(trainer);
		training.setBurnedCalories(12);
		ts.updateTraining(training);
		System.out.println(ts.getTrainingsByTrainer(trainer));
		System.out.println(ts.getTrainingsByClient(client));
		
		/*
		 * Membership Service Test
		 */
		MembershipService mService=ctx.getBean(MembershipService.class);
		User newUser=us.addUser("Kicsi Andár Béla", "kicsi007a", "passworda", "kicsi007a@freemail.hu", "203333333", new Date());
		User newUser2=us.addUser("Kicsi Andár Béla", "kicsi007aaa", "passworda", "kicsi007aa@freemail.hu", "203333333", new Date());
		User newUser3=us.addUser("Kicsi Andár Béla", "kicsi007aa", "passworda", "kicsi007aaa@freemail.hu", "203333333", new Date());
		
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
		List<Membership> result=mService.getMembershipByBasket(b2);
		System.out.println("Membership by basket number: "+result.size()+" values: "+result.toString());
		mService.deleteMembership(m2);
		result=mService.getMembershipByBasket(b2);
		System.out.println("Membership by basket number: "+result.size()+" values: "+result.toString());
		m1.setPrice(21000.0);
		mService.updateMembership(m1);
		mService.increaseClientEntries(m1);
		try {
			System.out.println("Membership by id: "+ mService.getMembershipById(m1.getId()));
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		System.out.println("Membership by User, number:"+mService.getMembershipByUser(newUser).size()+" values:"+mService.getMembershipByUser(newUser));
		System.out.println("Membership by User, number:"+mService.getMembershipByUser(newUser2).size()+" values:"+mService.getMembershipByUser(newUser2));
		
		
	}
}
