package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.dto.UserWithMemberships;

@Controller
@RequestMapping("/beleptetes")
public class CheckInController {
	private static Logger logger=LoggerFactory.getLogger(CheckInController.class);
	
	private GeneralUsersService gus;
	private GeneralProductsService gps;
	
	@Autowired
	public CheckInController(GeneralUsersService gus, GeneralProductsService gps){
		this.gus=gus;
		this.gps=gps;
	}
	
	@RequestMapping("")
	public String defaultPage(HttpServletRequest request, HttpServletResponse response){
		return "beleptetes";
	}
	
	@RequestMapping("/kereses")
	public String search(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam("searchType") String searchType, @RequestParam("searchInput") String searchInput){
		
		if(!searchInput.equals("") && !searchType.equals("")){
			logger.info("Search with type: "+searchType+" for param: "+searchInput);
			List<User> users=getUsersWithDetails(searchType, searchInput);
			if(users!=null){
				redirectAttributes.addFlashAttribute("searchResult", createAllUserWithMemberships(users));
			}
		}
		
		return "redirect:/beleptetes";
	}
	

	List<UserWithMemberships> createAllUserWithMemberships(List<User> users){
		List<UserWithMemberships> result=new ArrayList<UserWithMemberships>();
		
		for(User u : users){
			result.add(createUserWithMemberships(u));
		}
		
		return result;
	}
	
	UserWithMemberships createUserWithMemberships(User user){
		UserWithMemberships uwm=new UserWithMemberships();
		List<Membership> memberships=gps.getMembershipByUser(user);
		uwm.setUser(user);
		uwm.setMemberships(memberships);
		
		return uwm;
	}
	
	private List<User> getUsersWithDetails(String searchType, String searchInput) {
		
		return gus.getUsersByStringParamter(searchType, searchInput);
	}
}
