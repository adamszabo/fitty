package com.acme.fitness.webmvc.controllers;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value="/aruhaz/reklam/reklamok", method=RequestMethod.GET)
	@ResponseBody
	public void readAdvertisment(AtmosphereResource atmosphereResource){
		System.out.println("Subscribe to websocket....");
		this.suspend(atmosphereResource);
	}
	
	@RequestMapping(value="/aruhaz/reklam/reklamok", method=RequestMethod.POST)
	@ResponseBody
	public void sendAdvertisment(@RequestBody String adv, AtmosphereResource atmosphereResource){
		final String msg=adv;
		final Broadcaster bc = atmosphereResource.getBroadcaster();
		
		
		bc.broadcast(new Callable<String>() {
	        public String call() throws Exception {
	        	Random rand=new Random();
	        	System.out.println("Send advertisment message.....");
	            return "{ \"msg\" : \""+msg+" "+rand.nextInt()+"\"}";
	        }
	    });
	}
	
}
