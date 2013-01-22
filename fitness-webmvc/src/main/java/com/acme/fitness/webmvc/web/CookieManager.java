package com.acme.fitness.webmvc.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

	public void writeToCookies(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(60*60*24*7);
		response.addCookie(cookie);
	}
	
	public String readFromCookies(HttpServletRequest request, String key) {
		String value = null;
		if(request.getCookies() != null) {
			for(Cookie c : request.getCookies()) {
				if(c.getName().equals(key)) {
					value = c.getValue();
					break;
				}
			}
		}
		return value;
	}
}