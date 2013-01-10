package com.acme.fitness.webmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping("/ujberlet")
	public String newMembership(@RequestParam("datepicker") String datepicker, @RequestParam("membership") String type) {
		System.out.println(datepicker);
		System.out.println(type);
		return "berletek";
	}
	
}
