package com.acme.fitness.webmvc.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.calendar.CalendarConnect;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.util.ServiceException;

@Controller
@RequestMapping("/edzesek")
public class TrainingController {

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralUsersService gus;
	
	@Autowired
	private GeneralOrdersService gos;
	
	@RequestMapping(value = "")
	public String training(HttpServletRequest request) {
		request.setAttribute("trainers", gus.getAllTrainers());
		return "edzesek";
	}
	
	@RequestMapping(value = "felvesz")
	public String trainingToTheCalendar(@RequestParam String message, HttpServletRequest request) {
		CalendarConnect connect = new CalendarConnect();
		try {
			connect.createEvent(connect.createConnect(), message, "", null, false, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("trainers", gus.getAllTrainers());
		return "redirect:/edzesek";
	}
	
	@RequestMapping(value = "torles")
	public String removeAllTrainingOnCalendar(HttpServletRequest request) throws ServiceException, IOException {
		CalendarConnect connect = new CalendarConnect();
		CalendarService service = connect.createConnect();
		connect.deleteEvents(service, connect.getAllEvent(service));
		return "redirect:/edzesek";
	}
}
