package com.acme.fitness.webmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;


@Controller
@RequestMapping("/berletek")
public class MembershipController {
	
	@Autowired
	public GeneralProductsService gps;
	
	@Autowired
	public GeneralUsersService gus;
	
	@RequestMapping("")
	public String defaultPage() {
		
		return "berletek";
	}
	
	
}
