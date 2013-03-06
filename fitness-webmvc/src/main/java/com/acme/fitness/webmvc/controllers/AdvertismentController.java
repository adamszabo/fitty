package com.acme.fitness.webmvc.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/reklam")
public class AdvertismentController {
	
	private Logger logger = LoggerFactory.getLogger(AdvertismentController.class);
	
	private void suspend(final AtmosphereResource resource) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {
            @Override
            public void onSuspend(AtmosphereResourceEvent event) {
                countDownLatch.countDown();
                resource.removeEventListener(this);
            }
        });
        resource.suspend();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping(value = {"", "/",}) 
	public String defaultPage() {
		return "reklam";
	}

	@RequestMapping(value="/feliratkoz", method=RequestMethod.GET)
	@ResponseBody
	public void readAdvertisment(AtmosphereResource atmosphereResource){
		logger.info("Subcribed");
		this.suspend(atmosphereResource);
	}
	
	@RequestMapping(value="/feliratkoz", method=RequestMethod.POST)
	@ResponseBody
	public void addAdvertisment(AtmosphereResource atmosphereResource){
		String message = null;
		try {
			atmosphereResource.getRequest().setCharacterEncoding("UTF-8");
			message = atmosphereResource.getRequest().getReader().readLine();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Added new message: " + message);
	    final Broadcaster bc = atmosphereResource.getBroadcaster();
	    bc.scheduleFixedBroadcast("{ \"message\" : \"" + message + "\"}", 10, TimeUnit.SECONDS);
	}
	
}
