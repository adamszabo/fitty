package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acme.fitness.domain.products.Product;
import com.acme.fitness.products.GeneralProductsService;

@Controller
@RequestMapping("/aruhaz")
public class WebShopController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private GeneralProductsService gps;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void aruhaz(Locale locale, Model model) {
		setPage(model, "1");
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void aruhazWithSlash(Locale locale, Model model) {
		setPage(model, "1");
		
	}

	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String setPage(Model model, @PathVariable String page) {
		int pageNumber = parsePageNumber(page);
		int productSize = gps.getAllProduct().size();
		pageNumber = validatePageNumber(pageNumber, productSize);
		List<Product> productsOnPage = new ArrayList<Product>();
		if(productSize > 0)
			productsOnPage = gps.getAllProduct().subList((pageNumber - 1) * 9, Math.min(pageNumber * 9, productSize));
		model.addAttribute("products", productsOnPage);
		model.addAttribute("pageNumber", pageNumber);
		return "aruhaz";
	}


	@RequestMapping(value = "/{page}/addToCart", method = RequestMethod.POST)
	public String addProductToCart(@ModelAttribute("productId") long id, @ModelAttribute("quantity") int quantity, @PathVariable String page, Model model) {
		logger.info("Product added to cart with id: " + id + " - quantity: "
				+ quantity);
		return setPage(model, page);
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
