package com.acme.fitness.products;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
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
	}
}
