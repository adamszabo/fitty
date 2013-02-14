package com.acme.fitness.webmvc.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/edzo")
public class TrainerController {
	private static Logger logger=LoggerFactory.getLogger(TrainingController.class);
	
	@ResponseBody
	@RequestMapping(value="/vakacio",method=RequestMethod.POST)
	public String addVacation(HttpServletRequest request, @RequestParam("trainingDate") long dateInMS){
		Date result=new Date(dateInMS);
		logger.info("valami VAKÁCIÓ!!! Dátum: "+result);
		return "valami VAKÁCIÓ!!! Dátum: "+result;
	}
}
