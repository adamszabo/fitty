package com.acme.fitness.webmvc.controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.MembershipType;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.ProductImage;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;

@Controller
@RequestMapping(value = "/raktar")
public class StoreController {
	private static final Logger logger = LoggerFactory
			.getLogger(StoreController.class);

	private GeneralProductsService gps;
	private GeneralOrdersService gos;

	@Autowired
	public StoreController(GeneralProductsService gps, GeneralOrdersService gos) {
		this.gps = gps;
		this.gos = gos;
	}

	@RequestMapping(value = "")
	public String defaultPage() {
		return "redirect:/raktar/termek";
	}

	@RequestMapping(value = "termek", method = RequestMethod.GET)
	public String products(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		if (gos.getAllStores().size() != gps.getAllProduct().size()) {
			for (Product p : gps.getAllProduct()) {
				gos.addProductToStore(p, 0);
			}
		}
		List<Store> store = new ArrayList<Store>(gos.getAllStores());
		model.addAttribute("productsInStore", store);
		return "raktar";
	}

	@RequestMapping(value = "termek/ujmennyiseg", method = RequestMethod.POST)
	public String productPlusQuantity(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("productId") long id,
			@ModelAttribute("quantity") int quantity) {
		try {
			gos.putInProduct(gps.getProductById(id), quantity);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return "redirect:/raktar";
	}

	@RequestMapping(value = "termek/ujtermek", method = RequestMethod.POST)
	public String newProduct(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("product") Product product,
			@RequestParam("file") MultipartFile file) {
		ProductImage image = createPictureFromMultipartFile(file);

		Product newProduct = gps.addProduct(product.getName(),
				product.getDetails(), product.getPrice(),
				product.getManufacturer(), new Date(), image);
		logger.info("new product added: " + newProduct);

		return "redirect:/raktar";
	}

	@RequestMapping(value = "termek/torles/{productId}", method = RequestMethod.GET)
	public String deleteProduct(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request,
			@PathVariable("productId") long productId) {
		try {
			gps.deleteProduct(gps.getProductById(productId));
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return "redirect:/raktar";
	}

	@RequestMapping(value = "berlet")
	public String membership(Model model,
			HttpServletResponse response, HttpServletRequest request) {
		model.addAttribute("membershipsInStore", gps.getAllMembershipTypes());
		return "raktar";
	}
	
	@RequestMapping(value = "berlet/ujberlet", method = RequestMethod.POST)
	public String newMembership(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("membership") MembershipType membershipType) {
		gps.saveMembershipType(membershipType);
		logger.info("new membership type added: " + membershipType);
		return "redirect:/raktar/berlet";
	}
	
	@RequestMapping(value = "berlet/torles/{membershipId}", method = RequestMethod.GET)
	public String deleteMembership(@PathVariable("membershipId") long membershipId) {
		try {
			gps.deleteMembershipType(gps.getMembershipTypeById(membershipId));
			logger.info("membership deleted with id: " + membershipId);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return "redirect:/raktar/berlet";
	}
	

	private ProductImage createPictureFromMultipartFile(MultipartFile file) {
		ProductImage pi = null;

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String originalFileName = file.getOriginalFilename();
				String mime = getMimeFromFileName(originalFileName);
				logger.info("picutres uploaded: " + originalFileName
						+ " mime: " + mime);
				pi = new ProductImage(mime, bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return pi;
	}

	private String getMimeFromFileName(String originalFileName) {
		String[] helper = originalFileName.split("\\.");
		String mime = "";
		if (helper.length > 0) {
			mime = helper[1].toLowerCase();
		}
		return mime;
	}
}
