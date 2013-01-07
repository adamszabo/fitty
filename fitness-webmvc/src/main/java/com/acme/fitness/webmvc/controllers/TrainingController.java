package com.acme.fitness.webmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/edzesek")
public class TrainingController {

	@RequestMapping(value = "")
	public String training() {
		return "edzesek";
	}
}
