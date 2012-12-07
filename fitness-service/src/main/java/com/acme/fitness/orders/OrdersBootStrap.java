package com.acme.fitness.orders;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.products.MembershipService;
import com.acme.fitness.products.ProductService;
import com.acme.fitness.products.TrainingService;
import com.acme.fitness.users.UserService;

public class OrdersBootStrap {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"META-INF/Spring/*.xml");
		StoreService ss = ctx.getBean(StoreService.class);
		BasketDao bd = ctx.getBean(BasketDao.class);

		UserService userService = ctx.getBean(UserService.class);
		OrderItemService os = ctx.getBean(OrderItemService.class);
		ProductService ps = ctx.getBean(ProductService.class);
		MembershipService ms = ctx.getBean(MembershipService.class);
		TrainingService ts = ctx.getBean(TrainingService.class);

		GeneralProductsService gps = ctx.getBean(GeneralProductsService.class);
		GeneralOrdersService gos = ctx.getBean(GeneralOrdersService.class);

		/**
		 * Tests for StoreService
		 */
		Product product = ps.addProduct("labda", "detail", 12340.0,
				"manufacturer", new Date());
		Store store = ss.addProduct(product, 10);
		try {
			System.out.println(ss.getStoreById(store.getId()));
			System.out.println(ss.getStoreByProduct(product));
			System.out.println(ss.takeOutProduct(product, 11));
			System.out.println(ss.takeOutProduct(product, 7));
			System.out.println(store);
			ss.putInProduct(product, 45);
			System.out.println(store);
		} catch (FitnessDaoException e) {
			e.fillInStackTrace().printStackTrace();
		}

		/**
		 * Tests for OrderItemService
		 */
		User u1 = userService.addUser("Kicsi Andár Béla", "kicsi007a",
				"passworda", "kicsi007a@freemail.hu", "203333333", new Date());
		User u2 = userService.addUser("Kicsi Andár Béla", "kicsi007aaa",
				"passworda", "kicsi007aa@freemail.hu", "203333333", new Date());
		Basket b1 = new Basket(false, u1);
		Basket b2 = new Basket(false, u2);
		bd.save(b1);
		bd.save(b2);

		OrderItem oi1 = os.saveNewOrderItem(product, 0, b1);
		OrderItem oi2 = os.saveNewOrderItem(product, 0, b2);

		try {
			System.out.println("Get OrderItem by id: "
					+ os.getOrderItemById(oi1.getId()));
			oi1.setQuantity(1111);
			os.updateOrderItem(oi1);
			System.out.println("After update get OrderItem by id: "
					+ os.getOrderItemById(oi1.getId()));
			System.out.println("Get OrderItem by id: "
					+ os.getOrderItemById(oi2.getId()));
			os.deleteOrderItem(oi2);
			System.out.println("After delete try to get OrderItem by id: "
					+ os.getOrderItemById(oi2.getId()));
		} catch (FitnessDaoException e) {
			e.fillInStackTrace().printStackTrace();
		}

		/**
		 * Tests for BasketService
		 */
		// Basket basket1 = bs.newBasket(u1);
		Membership membership1 = ms.newMemberShip("ocassionaly", 10, null,
				18000);
		@SuppressWarnings("unused")
		Membership membership2 = ms.newMemberShip("type", 12, null, 13000);

		// bs.addMembershipToBasket(basket1, membership1);
		// bs.addMembershipToBasket(basket1, membership2);
		// bs.checkOutBasket(basket1);
		// bs.deliver(basket1);

		/**
		 * Tests for GeneralOrdersService
		 */

		Basket basket = gos.newBasket(u1);
		gos.addMembershipToBasket(basket, membership1);
		Training t1 = ts.newTraining(u1, u2, new Date());
		gos.addTrainingToBasket(basket, t1);

		Product p1 = gps.addProduct("pálcika", "kici ócó", 1.0, "csína",
				new Date());
		Product p2 = gps.addProduct("evő", "asd", 12321.0, "sd", new Date());

		gos.addProductToStore(p1, 10);
		gos.addProductToStore(p2, 15);
		
		try {
			gos.putInProduct(p2, 3);
		} catch (FitnessDaoException e) {
			e.fillInStackTrace().printStackTrace();
		}
		
		OrderItem o1 = gos.newOrderItem(p1, 9);
		gos.addOrderItemToBasket(basket, o1);
		OrderItem o2 = gos.newOrderItem(p2, 18);
		gos.addOrderItemToBasket(basket, o2);
		
		
		try {
			gos.checkOutBasket(basket);
		} catch (StoreQuantityException e) {
			System.out.println("!!!!!!!!!!!MiSSING PRODUCTS: " + e.getProduct());
		}
		
		System.out.println("**Get Memberships by Basket: ** " + gos.getMemberships(basket));
		System.out.println("**Get Baskets by User: ** " + gos.getBasketsByUser(u1));
		
		gos.deliver(basket);

		System.out.println(gos.getBasketsByUser(u1));
		System.out.println(gos.getAllStores());
		try {
			System.out.println("**Get Store by Product: ** " + gos.getStoreByProduct(p2));
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
	}

}
