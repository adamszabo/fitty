package com.acme.fitness.webmvc.cookie;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.acme.fitness.webmvc.json.JsonManager;

public class CookieManager {

	private static final int ONE_DAY = 60 * 60 * 24 * 7;
	

	public <T> Map<String, T> loadValueToMap(HttpServletRequest request, ObjectMapper mapper, String cookieName) {
		String cookieValue = readFromCookies(request, cookieName);

		JsonManager<T> jsonManager = new JsonManager<T>(mapper);
		Map<String, T> map = jsonManager.unwrapFromJsonString(cookieValue);

		return map;
	}

	public void writeToCookies(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(ONE_DAY);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public <T> void writeMapToCookie(HttpServletResponse response, ObjectMapper mapper, String cookieName, Map<String, T> map) {
		JsonManager<T> jsonManager = new JsonManager<T>(mapper);
		String json = jsonManager.wrapJsonToString(map);
		
		writeToCookies(response, cookieName, json);
	}
	
	public String readFromCookies(HttpServletRequest request, String key) {
		return request.getCookies() == null ? null : getCookieValueByKey(request, key);
	}
	
	public void removeTheCookieByName(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		for (Cookie c : request.getCookies()) {
			if (c.getName().equals(cookieName)) {
				c.setPath("/");
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
	}

	private String getCookieValueByKey(HttpServletRequest request, String key) {
		String value = null;
		for (Cookie c : request.getCookies()) {
			if (c.getName().equals(key)) {
				value = c.getValue();
				break;
			}
		}
		return value;
	}
}