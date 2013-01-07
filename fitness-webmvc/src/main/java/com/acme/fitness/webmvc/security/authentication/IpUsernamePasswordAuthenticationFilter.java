package com.acme.fitness.webmvc.security.authentication;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;

public class IpUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private GeneralUsersService generalUsersService;
	
	private SessionRegistryImpl sessionRegistryImpl;

	@Autowired
	public IpUsernamePasswordAuthenticationFilter(GeneralUsersService generalUsersService, SessionRegistryImpl sessionRegistryImpl) {
		super();
		this.generalUsersService=generalUsersService;
		this.sessionRegistryImpl=sessionRegistryImpl;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication result=null;
		
		String username = obtainUsername(request);
        boolean isLoggedIn=checkUserInSessionRegistry(username);
        boolean isLoginValid=true;
        
        try {
        	isLoginValid=generalUsersService.isLoginValidByUser(username, request.getRemoteAddr(), isLoggedIn);
		} catch (FitnessDaoException e) {
			isLoginValid=false;
		}
        
        if(isLoginValid){
        	saveLoginInformation(request, username);
        	result=super.attemptAuthentication(request, response);
        }
        else{
        	invalidateLogin();
        }
        
		return result;
	}
	
	private void saveLoginInformation(HttpServletRequest request, String username){
		try {
			User user=generalUsersService.getUserByUsername(username);
			generalUsersService.addLastLoginInfo(user, request.getRemoteAddr(), new Date());
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
	}

	private void invalidateLogin() {
			throw new BadCredentialsException(messages.getMessage(
                    "IpUsernamePasswordAuthenticationFilter.ipConflict", "A user already logged in from different ip."));
	}

	private boolean checkUserInSessionRegistry(String username) {
		boolean result=false;
		for(Object o: sessionRegistryImpl.getAllPrincipals()){
			org.springframework.security.core.userdetails.User user=(org.springframework.security.core.userdetails.User)o;
			
			if(user.getUsername().equals(username)){
				result=true;
				break;
			}
		}
		return result;
	}

}
