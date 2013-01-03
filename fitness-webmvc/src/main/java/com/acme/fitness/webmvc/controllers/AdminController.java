package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralOrdersService gos;

	@Autowired
	private GeneralUsersService gus;
	
	@RequestMapping(value = "/raktar", method = RequestMethod.GET)
	public String store(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		if(gos.getAllStores().size() != gps.getAllProduct().size()) {
			for(Product p : gps.getAllProduct()) {
				gos.addProductToStore(p, 0);
			}
		}
		List<Store> store = new ArrayList<Store>(gos.getAllStores());
		model.addAttribute("productsInStore", store);
		return "raktar";
	}
	
	@RequestMapping(value = "/ujmennyiseg", method = RequestMethod.POST)
	public String plusQuantity(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request, @ModelAttribute("productId") long id, @ModelAttribute("quantity") int quantity ) {
		try {
			gos.putInProduct(gps.getProductById(id), quantity);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/raktar";
	}
	
	@RequestMapping(value = "/ujtermek", method = RequestMethod.POST)
	public String newProduct(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request, @ModelAttribute("product") Product product) {
		Product newProduct = gps.addProduct(product.getName(), product.getDetails(), product.getPrice(), product.getManufacturer(), new Date());
		logger.info("new product added: " + newProduct);
		return "redirect:/admin/raktar";
	}
	
	@RequestMapping(value = "/termektorles/{productId}", method = RequestMethod.GET)
	public String deleteProduct(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request, @PathVariable("productId") long productId) {
		try {
			gps.deleteProduct(gps.getProductById(productId));
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/raktar";
	}
	
}	
