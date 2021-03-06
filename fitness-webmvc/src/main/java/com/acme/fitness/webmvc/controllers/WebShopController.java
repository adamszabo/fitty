package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acme.fitness.domain.products.Product;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.webmvc.basket.BasketManager;

@Controller
@RequestMapping("/aruhaz")
public class WebShopController {

	@Autowired
	private GeneralProductsService gps;
	
	@Autowired
	private BasketManager basketManager;

	@RequestMapping(value={"", "/",}, method=RequestMethod.GET)
	public String defaultPage(Model model, HttpServletResponse response, HttpServletRequest request) {
		return "redirect:/aruhaz/1";
	}
	
	@RequestMapping(value="/{page}", method = RequestMethod.GET)
	public String setPage(Model model, @PathVariable String page, HttpServletResponse response, HttpServletRequest request) {
		basketManager.addBasketToSessionIfExists(request, response, new ObjectMapper());
		basketManager.isAnonymousBasketIfUserLoggedIn(request, response, new ObjectMapper());
		loadPageNumberAndProducts(model, page);
		return "aruhaz";
	}

	@RequestMapping(value = "/{page}/addToCart", method = RequestMethod.POST)
	public String addProductToCart(@ModelAttribute("productId") long id, @ModelAttribute("quantity") int quantity, @PathVariable String page, HttpServletResponse response,
			HttpServletRequest request, Model model) {
		basketManager.addNewOrderItem(id, quantity, response, request, new ObjectMapper());
		return "redirect:" + redirectedFrom(request);
	}

	@RequestMapping(value = "/torles/{productId}")
	public String removeProduct(@PathVariable long productId, HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeProductFromBasket(productId, request, response);
		return "redirect:" + redirectedFrom(request);
	}

	@RequestMapping(value = "/torles/anonymous/{productId}")
	public String removeAnonymousProduct(@PathVariable long productId, HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeAnonymousProduct(productId, request, response);
		return "redirect:" + redirectedFrom(request);
	}
	
	@RequestMapping(value = "/anonymKosar/torles")
	public String deleteAnonymousBasket(HttpServletRequest request, HttpServletResponse response) {
		basketManager.addBasketToSessionIfExists(request, response, new ObjectMapper());
		basketManager.deleteAnonymousBasket(request, response);
		request.getSession().removeAttribute("anonymousBasket");
		return "redirect:" + redirectedFrom(request);
	}

	@RequestMapping(value = "/anonymKosar/hozzaad")
	public String mergeAnonymousBasket(HttpServletRequest request, HttpServletResponse response) {
		basketManager.AddAnonymousBasketToLoggedInUserBasket(response, request, new ObjectMapper());
		return "redirect:" + redirectedFrom(request);
	}
	
	@RequestMapping(value = "/hianyzo/max")
	public String addMissingProductMaxQuantityToBasket(HttpServletRequest request, HttpServletResponse response) {
		basketManager.updateMissingProductToMaxValue(request, response, new ObjectMapper());
		return "redirect:" + redirectedFrom(request);
	}
	
	@RequestMapping(value = "/hianyzo/torol")
	public String removeMissingProductsFromBasket(HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeProductsFromBasket(request, response, new ObjectMapper());
		return "redirect:" + redirectedFrom(request);
	}

	private String redirectedFrom(HttpServletRequest request) {
		String split[] = request.getHeader("referer").split(request.getContextPath());
		return split[1];
	}
	
	private void loadPageNumberAndProducts(Model model, String page) {
		int pageNumber = validatePageNumber(parsePageNumber(page), gps.getAllProduct().size());
		model.addAttribute("products", getProductsOnPage(pageNumber));
		model.addAttribute("pageNumber", pageNumber);
	}

	private List<Product> getProductsOnPage(int pageNumber) {
		int size = gps.getAllProduct().size();
		List<Product> productsOnPage = new ArrayList<Product>();
		if (size > 0)
			productsOnPage = gps.getAllProduct().subList((pageNumber - 1) * 9, Math.min(pageNumber * 9, size));
		return productsOnPage;
	}

	private int validatePageNumber(int pageNumber, int productSize) {
		if (pageNumber <= 1) {
			pageNumber = 1;
		} else if (pageNumber > (Math.ceil(productSize / 9.0))) {
			pageNumber = (int) Math.ceil(productSize / 9.0);
			if (pageNumber <= 1) {
				pageNumber = 1;
			}
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