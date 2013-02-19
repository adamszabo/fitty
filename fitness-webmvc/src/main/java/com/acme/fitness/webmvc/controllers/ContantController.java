package com.acme.fitness.webmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContantController {
	
	@RequestMapping(value="/kapcsolatok")
	public String defaultPage() {
		return "kapcsolatok";
	}
}
