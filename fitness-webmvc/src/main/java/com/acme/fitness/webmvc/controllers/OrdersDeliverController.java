package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;

@Controller
@RequestMapping(value="/rendeles")
public class OrdersDeliverController {
	private static Logger logger=LoggerFactory.getLogger(OrdersDeliverController.class);
	
	private GeneralOrdersService generalOrdersService;
	private GeneralProductsService generalProductsService;
	
	@Autowired
	public OrdersDeliverController(GeneralOrdersService generalOrdersService, GeneralProductsService generalProductsService){
		this.generalOrdersService=generalOrdersService;
		this.generalProductsService=generalProductsService;
	}
	
	@ResponseBody
	@RequestMapping(value="/atad" ,method=RequestMethod.POST)
	public List<Membership> deliverBasketsItems(@RequestParam("basketId")long basketId){
		List<Membership> result=new ArrayList<Membership>();
		
		try {
			Basket basket = generalOrdersService.getBasketById(basketId);
			generalOrdersService.deliver(basket);
			result=getValidMemberShipsByBasket(basket);
			logger.info("Deliver basket with id: "+basketId);
		} catch (FitnessDaoException e) {
			logger.info("Deliver basket faild because basket does not exist with id: "+basketId);
		}
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/torol",method=RequestMethod.POST)
	public boolean stornoBasketsItems(@RequestParam("basketId")long basketId){
		boolean result=true;
		
		try {
			Basket basket = generalOrdersService.getBasketById(basketId);
			generalOrdersService.deleteBasket(basket);
			logger.info("Storno basket with id: "+basketId);
		} catch (FitnessDaoException e) {
			result=false;
		}
		
		return result;
	}
	
	private List<Membership> getValidMemberShipsByBasket(Basket basket){
		List<Membership> validMemberships=new ArrayList<Membership>();
		Date date=new Date();
		
		for(Membership membership : generalProductsService.getMembershipByBasket(basket)){
			if(generalProductsService.isValid(membership, date)){
				validMemberships.add(membership);
			}
		}
		
		return validMemberships;
	}
}
