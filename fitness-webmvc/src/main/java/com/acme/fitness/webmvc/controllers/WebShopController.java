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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acme.fitness.domain.exceptions.BasketCheckOutException;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.webmvc.basket.BasketManager;

@Controller
@RequestMapping("/aruhaz")
public class WebShopController {

	@Autowired
	private GeneralProductsService gps;
	
	@Autowired
	private GeneralOrdersService gos;

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
		return "redirect:/aruhaz";
	}

	@RequestMapping(value = "/deleteBasket", method = RequestMethod.GET)
	public String deleteBasketDefault(HttpServletRequest request, HttpServletResponse response, Model model) {
		return deleteBasket("1", request, response, model);
	}

	@RequestMapping(value = "/{page}/deleteBasket", method = RequestMethod.GET)
	public String deleteBasket(@PathVariable String page, HttpServletRequest request, HttpServletResponse response, Model model) {
//		basketManager.deleteBasket(request, response);
		System.out.println(request.getHeader("referer"));
		loadPageNumberAndProducts(model, page);
		return "aruhaz";
	}

	@RequestMapping(value = "/confirmBasket", method = RequestMethod.GET)
	public String confirmOrderDefault(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
		return confirmOrder("1", request, response, redirectAttributes, model);
	}

	@RequestMapping(value = "/{page}/confirmBasket", method = RequestMethod.GET)
	public String confirmOrder(@PathVariable String page, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
		try {
			basketManager.checkOutBasket(response, request);
		} catch (StoreQuantityException e) {
			addMissingProductsMessages(redirectAttributes, e.getProduct());
			return "redirect:/aruhaz/1";
		} catch (BasketCheckOutException e) {
			return failToCheckOut(page, redirectAttributes);
		}
		loadPageNumberAndProducts(model, page);
		return "aruhaz";
	}

	@RequestMapping(value = "/torles/{productId}")
	public String removeProduct(@PathVariable long productId, HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeProductFromBasket(productId, request, response);
		return "redirect:/aruhaz/";
	}

	@RequestMapping(value = "/torles/anonymous/{productId}")
	public String removeAnonymousProduct(@PathVariable long productId, HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeAnonymousProduct(productId, request, response);
		return "redirect:/aruhaz/";
	}
	
	@RequestMapping(value = "/anonymKosar/torles")
	public String deleteAnonymousBasket(HttpServletRequest request, HttpServletResponse response) {
		basketManager.addBasketToSessionIfExists(request, response, new ObjectMapper());
		basketManager.deleteAnonymousBasket(request, response);
		request.getSession().removeAttribute("anonymousBasket");
		return "redirect:/aruhaz";
	}

	@RequestMapping(value = "/anonymKosar/hozzaad")
	public String mergeAnonymousBasket(HttpServletRequest request, HttpServletResponse response) {
		basketManager.AddAnonymousBasketToLoggedInUserBasket(response, request, new ObjectMapper());
		return "redirect:/aruhaz";
	}
	
	@RequestMapping(value = "/hianyzo/max")
	public String addMissingProductMaxQuantityToBasket(HttpServletRequest request, HttpServletResponse response) {
		basketManager.updateMissingProductToMaxValue(request, response, new ObjectMapper());
		return "redirect:/aruhaz";
	}
	
	@RequestMapping(value = "/hianyzo/torol")
	public String removeMissingProductsFromBasket(HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeProductsFromBasket(request, response, new ObjectMapper());
		return "redirect:/aruhaz";
	}

	private void loadPageNumberAndProducts(Model model, String page) {
		int pageNumber = validatePageNumber(parsePageNumber(page), gps.getAllProduct().size());
		model.addAttribute("products", getProductsOnPage(pageNumber));
		model.addAttribute("pageNumber", pageNumber);
	}

	private String failToCheckOut(String page, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Termék rendeléséhez be kell jelentkezni!");
		return "redirect:/aruhaz/" + page;
	}

	private void addMissingProductsMessages(RedirectAttributes redirectAttributes, List<Product> list) {
		List<Store> stores = new ArrayList<Store>();
		for(Product p : list) {
			try {
				stores.add(gos.getStoreByProduct(p));
			} catch (FitnessDaoException e) {
				e.printStackTrace();
			}
		}
		redirectAttributes.addFlashAttribute("missingProduct", stores);
		redirectAttributes.addFlashAttribute("message", "Egyes termékekből nincsen elegendő mennyiség a raktáron. További információk az alábbi linken!");
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