package com.acme.fitness.orders;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.ProductService;
import com.acme.fitness.users.UserService;

public class OrdersBootStrap {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/Spring/*.xml");
		StoreService ss = ctx.getBean(StoreService.class);
		
		BasketDao bd=ctx.getBean(BasketDao.class);
		
		UserService userService=ctx.getBean(UserService.class);
		OrderItemService os=ctx.getBean(OrderItemService.class);
		ProductService ps = ctx.getBean(ProductService.class);
		Product product = ps.addProduct("labda", "detail", 12340.0, "manufacturer", new Date());
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
		User u1=userService.addUser("Kicsi Andár Béla", "kicsi007a", "passworda", "kicsi007a@freemail.hu", "203333333", new Date());
		User u2=userService.addUser("Kicsi Andár Béla", "kicsi007aaa", "passworda", "kicsi007aa@freemail.hu", "203333333", new Date());
		Basket b1=new Basket(false, u1);
		Basket b2=new Basket(false, u2);
		bd.save(b1);
		bd.save(b2);
		
		OrderItem oi1=os.addOrderItem(product, 0, b1);
		OrderItem oi2=os.addOrderItem(product, 0, b2);
		
		try {
			System.out.println("Get OrderItem by id: "+os.getOrderItemById(oi1.getId()));
			oi1.setQuantity(1111);
			os.updateOrderItem(oi1);
			System.out.println("After update get OrderItem by id: "+os.getOrderItemById(oi1.getId()));
			System.out.println("Get OrderItem by id: "+os.getOrderItemById(oi2.getId()));
			os.deleteOrderItem(oi2);
			System.out.println("After delete try to get OrderItem by id: "+os.getOrderItemById(oi2.getId()));
		} catch (FitnessDaoException e) {
			e.fillInStackTrace().printStackTrace();
		}
		
		
	}
}
