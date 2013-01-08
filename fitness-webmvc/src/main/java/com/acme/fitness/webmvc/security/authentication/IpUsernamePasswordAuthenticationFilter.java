package com.acme.fitness.webmvc.security.authentication;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger logger=LoggerFactory.getLogger(IpUsernamePasswordAuthenticationFilter.class);
	
	private GeneralUsersService generalUsersService;
	private SessionRegistryImpl sessionRegistryImpl;
	private UserRetriesValidator userRetriesValidator;

	@Autowired
	public IpUsernamePasswordAuthenticationFilter(GeneralUsersService generalUsersService, SessionRegistryImpl sessionRegistryImpl, UserRetriesValidator userRetriesValidator) {
		super();
		this.generalUsersService=generalUsersService;
		this.sessionRegistryImpl=sessionRegistryImpl;
		this.userRetriesValidator=userRetriesValidator;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication result=null;
		String username = obtainUsername(request);
		String ipAddress=request.getRemoteAddr();
        boolean isLoggedIn=checkUserInSessionRegistry(username);
        boolean isLoginValid=checkLoginIsValidWithIp(username, ipAddress, isLoggedIn);
        
        if(isLoginValid){
        	try {
				result = super.attemptAuthentication(request, response);
				saveLoginInformation(request, username);
				userRetriesValidator.setUserRetriesToDefault(username);
				logger.info("User logged in with username: "+username+" with ip: "+ipAddress);
			} catch (BadCredentialsException e) {
				logger.info("User unsuccessfully log in with username: "+username+" with ip: "+ipAddress);
				userRetriesValidator.validate(username, e, messages);
			}
        }
        else{
        	throw new BadCredentialsException(messages.getMessage(
                    "IpUsernamePasswordAuthenticationFilter.ipConflict", "A user already logged in from different ip."));
        }
        
		return result;
	}
	
	private boolean checkLoginIsValidWithIp(String username, String address, boolean isLoggedIn) {
		boolean isLoginValid=false;
		try {
        	isLoginValid=generalUsersService.isLoginValidByUser(username, address, isLoggedIn);
		} catch (FitnessDaoException e) {
			throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		
		return isLoginValid;
	}

	private void saveLoginInformation(HttpServletRequest request, String username){
		try {
			User user=generalUsersService.getUserByUsername(username);
			generalUsersService.addLastLoginInfo(user, request.getRemoteAddr(), new Date());
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
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
