package com.acme.fitness.webmvc.controllers;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdvertismentController {
	
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

	@RequestMapping(value="/aruhaz/reklamok")
	@ResponseBody
	public void readAdvertisment(AtmosphereResource atmosphereResource){
		System.out.println("hahaha");
		this.suspend(atmosphereResource);
	 
	    final Broadcaster bc = atmosphereResource.getBroadcaster();
	    
	    bc.scheduleFixedBroadcast(new Callable<String>() {
	    	
	        //@Override
	        public String call() throws Exception {
	        	System.out.println("hehehe");
	            return "{ \"szeva\" : \"pina\"}";
	        }
	 
	    }, 10, TimeUnit.SECONDS);
	}
	
}
