package com.acme.fitness.webmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/beleptetes")
public class CheckInController {
	
	@RequestMapping("")
	public String defaultPage(){
		return "beleptetes";
	}
}
