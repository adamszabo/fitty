package com.acme.fitness.orders;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.products.ProductService;

public class OrdersBootStrap {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"META-INF/Spring/*.xml");
		StoreService ss = ctx.getBean(StoreService.class);
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
	}
}
