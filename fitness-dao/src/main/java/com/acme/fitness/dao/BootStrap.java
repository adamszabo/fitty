package com.acme.fitness.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.dao.orders.OrderItemDao;
import com.acme.fitness.dao.orders.StoreDao;
import com.acme.fitness.dao.products.MemberShipDao;
import com.acme.fitness.dao.products.ProductDao;
import com.acme.fitness.dao.products.TrainingDao;
import com.acme.fitness.dao.users.RoleDao;
import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.MemberShip;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;

public class BootStrap {
	
	private static Logger logger=LoggerFactory.getLogger(BootStrap.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/Spring/*.xml");
		UserDao userDao=ctx.getBean(UserDao.class);
		RoleDao roleDao=ctx.getBean(RoleDao.class);
		ProductDao productDao=ctx.getBean(ProductDao.class);
		StoreDao storeDao=ctx.getBean(StoreDao.class);
		OrderItemDao orderItemDao=ctx.getBean(OrderItemDao.class);
		BasketDao orderDao=ctx.getBean(BasketDao.class);
		MemberShipDao memberShipDao=ctx.getBean(MemberShipDao.class);
		TrainingDao trainingDao=ctx.getBean(TrainingDao.class);
		
		/*--- User and Role ---*/
		User u=new User("Kicsi Andár Béla", "kicsi007", "password", "kicsi007@freemail.hu", "203333333", new Date(), new Date(), "127.0.0.1",true);
		User u2=new User("Kicsi Ádám", "kicsi_adam", "password", "kicsi_adam@freemail.hu", "203333355", new Date(), new Date(), "127.0.0.1",true);
		userDao.save(u);
		userDao.save(u2);
		
		Role r1=new Role(u, "Admin");
		Role r2=new Role(u2, "Receptionist");
		Role r3=new Role(u2, "Client");
		Role r4=new Role(u2, "System admin");
		roleDao.save(r1);
		roleDao.save(r2);
		roleDao.save(r3);
		roleDao.save(r4);
		r3.setUser(u);
		roleDao.update(r3);
		
		System.out.println(userDao.getAllUsers());
		System.out.println(roleDao.getAllRoles());
		
		//userDao.delete(u2);
		roleDao.delete(r4);
		System.out.println(userDao.getAllUsers());
		System.out.println(roleDao.getAllRoles());
		/*--- User and Role ---*/
		
		Product p1=new Product("Prod1", "Lorem ipsum", 12000, "manufacturer", new Date());
		Product p2=new Product("Prod2", "Lorem ipsum", 13000, "manufacturer", new Date());
		Product p3=new Product("Prod3", "Lorem ipsum", 14000, "manufacturer", new Date());
		productDao.save(p1);
		productDao.save(p2);
		productDao.save(p3);
		System.out.println(productDao.getAllProduct());
		
		Store s1=new Store(p1, 2);
		Store s2=new Store(p3,5);
		Store s3=new Store(p2,10);
		storeDao.save(s1);
		storeDao.save(s2);
		storeDao.save(s3);
		
		logger.info(storeDao.getAllStores().toString());
		logger.info(storeDao.getStoreByProdctId(p1.getId()).toString());
		logger.info(storeDao.getStoreByProdctId(p2.getId()).toString());
		logger.info("--getStoreByProductName: "+storeDao.getStoresByProductName(p1.getName()));
		logger.info("--getStoreByProductManufacturer: "+storeDao.getStoresByProductManufacturer(p1.getManufacturer()));
		
		storeDao.delete(s2);
		s1.setQuantity(10000);
		storeDao.update(s1);
		System.out.println(storeDao.getAllStores());
		
		//productDao.delete(p2);
		p1.setManufacturer("asdfasdf");
		p1.setName("fffffffff");
		productDao.update(p1);
		logger.info("All products:"+productDao.getAllProduct());
		logger.info("All stores:"+storeDao.getAllStores());
		
		Basket o1=new Basket(false, u);
		OrderItem oi1=new OrderItem(p1, 10, o1);
		OrderItem oi2=new OrderItem(p3, 30, o1);
		orderDao.save(o1);
		o1.getOrderItems().add(oi1);
		o1.getOrderItems().add(oi2);
		orderItemDao.save(oi1);
		orderItemDao.save(oi2);
		
		orderDao.update(o1);
		
		MemberShip ms=new MemberShip("alkalmi", 0, 12, new Date(), 10000, o1);
		MemberShip ms2=new MemberShip("alkalmi", 0, 10, new Date(), 10000, o1);
		memberShipDao.save(ms);
		memberShipDao.save(ms2);
		
		Training t1=new Training(u2, u, new Date(), false, 150, "lorem ipsum", o1);
		Training t2=new Training(u2, u, new Date(), false, 170, "xyllll", o1);
		trainingDao.save(t1);
		trainingDao.save(t2);
		
		memberShipDao.delete(ms);
		logger.info("All MemberShip: "+memberShipDao.getAllMemberShips().toString());
		logger.info("MemberShip by id: "+memberShipDao.getMemberShipById(ms2.getId()).toString());
		logger.info("MemberShips by Order: "+memberShipDao.getMemberShipsByOrder(o1).toString());
		
		logger.info("Training by id: "+trainingDao.getTrainingById(t1.getId()).toString());
		logger.info("Training by Order: "+trainingDao.getTrainingByOrder(o1).toString());
		logger.info("Training by trainer: "+trainingDao.getTrainingByTrainer(u2).toString());
		t1.setBurnedCalories(10000);
		trainingDao.update(t1);
		//trainingDao.delete(t2);
		logger.info("All Training: "+trainingDao.getAllTraining().toString());
		
		List<Basket> orders=orderDao.getAllBaskets();
		logger.info("Orders size:"+orders.size());
		logger.info("Orders:"+orders.toString());
		logger.info("Orders by user:"+orderDao.getBasketsByUser(u));
		logger.info("First order orderItems:" + orders.get(0).getOrderItems());
		
		logger.info("User trainings: "+trainingDao.getTrainingByClient(u));
		logger.info("User trainings: "+trainingDao.getTrainingByClient(u2));
		
		logger.info("Trainer trainings: "+trainingDao.getTrainingByTrainer(u));
		logger.info("Trainer trainings: "+trainingDao.getTrainingByTrainer(u2));
		
		List<MemberShip> memberShips=memberShipDao.getMemberShipsByUser(u);
		logger.info("User memberShips quantity:"+memberShips.size()+" products:"+memberShips);
		
			
	}

}
