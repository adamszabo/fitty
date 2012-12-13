package com.acme.fitness.webmvc.controllers;

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
		setPage(locale, model, "1");
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void aruhazWithSlash(Locale locale, Model model) {
		setPage(locale, model, "1");
	}

	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String setPage(Locale locale, Model model, @PathVariable String page) {
		logger.info("Üdvözöljük az ÁRUHÁZBAN! the client locale is "
				+ locale.toString());
		int pageNumber;
		int productSize = gps.getAllProduct().size();
		try {
			pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			pageNumber = 1;
		}
		if(pageNumber < 1 ) {
			pageNumber = 1;
		} else if(pageNumber > (productSize%9)) {
			pageNumber = productSize%9;
		}
		List<Product> productsOnPage = gps.getAllProduct().subList((pageNumber - 1) * 9, Math.min(pageNumber * 9, productSize));
		model.addAttribute("products", productsOnPage);
		model.addAttribute("pageNumber", pageNumber);
		return "aruhaz";
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public String addProductToCart(@ModelAttribute("productId") long id,
			@ModelAttribute("quantity") int quantity, Model model) {
		logger.info("Product added to cart with id: " + id + " - quantity: "
				+ quantity);
		model.addAttribute("products", gps.getAllProduct());
		return "aruhaz";
	}

}
