package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;

@Controller
@RequestMapping("/aruhaz")
public class WebShopController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralOrdersService gos;
	
	@SuppressWarnings("unused")
	@Autowired
	private GeneralUsersService gus;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void aruhaz(Locale locale, Model model, HttpServletRequest request) {
		setPage(model, "1", request);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void aruhazWithSlash(Locale locale, Model model, HttpServletRequest request) {
		setPage(model, "1", request);
		
	}

	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String setPage(Model model, @PathVariable String page, HttpServletRequest request) {
		int pageNumber = parsePageNumber(page);
		int productSize = gps.getAllProduct().size();
		pageNumber = validatePageNumber(pageNumber, productSize);
		List<Product> productsOnPage = getProductsSubListOnPage(pageNumber,	productSize);
		model.addAttribute("products", productsOnPage);
		model.addAttribute("pageNumber", pageNumber);
		return "aruhaz";
	}

	@RequestMapping(value = "/{page}/addToCart", method = RequestMethod.POST)
	public String addProductToCart(@ModelAttribute("productId") long id, @ModelAttribute("quantity") int quantity, @PathVariable String page, Model model, HttpServletRequest request) {
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		if(basket == null) {
			basket = makeNewBasketByUser(new User());
		}
		addProductToBasketByProductId(id, quantity, basket);
		request.getSession().setAttribute("basket", basket);
		logger.info("Product added to cart with id: " + id + " - quantity: "
				+ quantity);
		return setPage(model, page, request);
	}

	@RequestMapping(value =  "/{page}/deleteBasket", method=RequestMethod.GET)
	public String deleteBasket(@PathVariable String page, Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("basket");
		return setPage(model, page, request);
	}
	
	private List<Product> getProductsSubListOnPage(int pageNumber, int productSize) {
		List<Product> productsOnPage = new ArrayList<Product>();
		if(productSize > 0)
			productsOnPage = gps.getAllProduct().subList((pageNumber - 1) * 9, Math.min(pageNumber * 9, productSize));
		return productsOnPage;
	}

	private void addProductToBasketByProductId(long id, int quantity,
			Basket basket) {
		Product product = null;
		try {
			product = gps.getProductById(id);
		} catch (FitnessDaoException e) {
			
		}
		OrderItem oi = gos.newOrderItem(product, quantity);
		gos.addOrderItemToBasket(basket, oi);
	}

	private Basket makeNewBasketByUser(User user) {
		Basket basket;
		basket = gos.newBasket(user);
		return basket;
	}
	
	private int validatePageNumber(int pageNumber, int productSize) {
		if(pageNumber < 1 ) {
			pageNumber = 1;
		} else if(pageNumber > (Math.ceil(productSize/9.0))) {
			pageNumber = (int) Math.ceil(productSize/9.0);
		}
		return pageNumber;
	}

	private int parsePageNumber(String page) {
		int pageNumber;
		try {
			pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			pageNumber = 1;
		}
		return pageNumber;
	}
}
