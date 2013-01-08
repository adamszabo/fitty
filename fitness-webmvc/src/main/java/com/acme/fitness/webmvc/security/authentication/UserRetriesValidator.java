package com.acme.fitness.webmvc.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;

@Component
public class UserRetriesValidator {
	private static int MAX_NUMBER_OF_RETRIES=5;
	private static int DEFAULT_NUMBER_OF_RETRIES=0;
	private static Logger logger=LoggerFactory.getLogger(UserRetriesValidator.class);
	
	private GeneralUsersService generalUsersService;
	
	@Autowired
	public UserRetriesValidator(GeneralUsersService generalUsersService){
		super();
		this.generalUsersService=generalUsersService;
	}
	
	public void setUserRetriesToDefault(String username){
		User user=getUserFromDatabase(username);
		user.setNumberOfRetries(DEFAULT_NUMBER_OF_RETRIES);
		updateUser(user);
	}

	public void validate(String username, BadCredentialsException e, MessageSourceAccessor messages) {
		if(e.getMessage().equals(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials"))){
			validateUserInfo(username);
		}
		
		throw e;
	}

	private void validateUserInfo(String username) {
		User user=getUserFromDatabase(username);
		if(user.isEnabled()){
			if(user.getNumberOfRetries() < MAX_NUMBER_OF_RETRIES){
				increaseUserNumberOfRetries(user);
			}
			else{
				disableUser(user);
			}
			updateUser(user);
		}
	}
	
	private void increaseUserNumberOfRetries(User user) {
		user.setNumberOfRetries(user.getNumberOfRetries()+1);
	}

	private void disableUser(User user) {
		user.setEnabled(false);
		logger.info("Disable user with username: "+user.getUsername());
	}
	
	private void updateUser(User user) {
		generalUsersService.updateUser(user);		
	}

	private User getUserFromDatabase(String username){
		User user=null;
		try {
			user=generalUsersService.getUserByUsername(username);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return user;
	}
}
