package com.acme.fitness.webmvc.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.acme.fitness.webmvc.cookie.CookieManager;

public class UserManager {
	
	public String getLoggedInUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
	
	public Map<String, Map<String, Map<String, String>>> loadUserNamesCookieValue(HttpServletRequest request, ObjectMapper mapper, CookieManager cookieManager) {
		return cookieManager.loadValueToMap(request, mapper, "userNames");
	}
}
