package com.acme.fitness.webmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/rendeles")
public class OrdersDeliverController {
	
	@RequestMapping(value="/atad")
	@ResponseBody
	public void deliverBasketsItems(){
		
	}
	
	@RequestMapping(value="/torol")
	@ResponseBody
	public void stornoBasketsItems(){
		
	}
}
