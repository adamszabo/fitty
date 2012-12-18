package com.acme.fitness.webmvc.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.dto.CheckUserFieldDTO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private GeneralUsersService gus;
	
	@Autowired
	StandardPasswordEncoder spe;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String registration(Locale locale, Model model, @Valid User user){
		boolean existUsername=existUserName(user.getUsername());
		boolean existEmail=existEmail(user.getEmail());
		
		if(!existUsername && !existEmail){
			gus.addUser(user.getFullName(), user.getUsername(), spe.encode(user.getPassword()), user.getEmail(), user.getMobile(), new Date());
			logger.info("User registered :"+user.toString()+" password: "+user.getPassword()+", mobile: "+user.getMobile());
		}
		else{
			logger.info("The username or email already exists in the database!");
		}
				
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value="/checkUser", method=RequestMethod.POST)
	public CheckUserFieldDTO isUsernameValid(Model model,@RequestParam(value="username") String username, @RequestParam(value="email") String userEmail){
		logger.info("Request param username: "+username+" email: "+userEmail);
		CheckUserFieldDTO result=new CheckUserFieldDTO();
		result.setExistUsername(existUserName(username));
		result.setExistEmail(existEmail(userEmail));
		return result;
	}

	private boolean existEmail(String userEmail) {
		boolean existEmail;
		try {
			gus.getUserByEmail(userEmail);
			existEmail=true;
		} catch (FitnessDaoException e) {
			existEmail=false;
		}
		return existEmail;
	}
	
	private boolean existUserName(String username) {
		boolean existUsername;
		try {
			gus.getUserByUsername(username);
			existUsername=true;
		} catch (FitnessDaoException e) {
			existUsername=false;
		}
		return existUsername;
	}
	
}
