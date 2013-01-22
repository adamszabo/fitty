package com.acme.fitness.webmvc.initialization;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.Roles;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;

@Component
public class FitnessInitializationService implements InitializingBean {
	private static Logger LOGGER=LoggerFactory.getLogger(FitnessInitializationService.class);
	
	private GeneralUsersService generalUsersService;
	private StandardPasswordEncoder standardPE;
	private FitnessUserPropertiesLoader fitnessUserPropertiesLoader;
	
	@Autowired
	public FitnessInitializationService(GeneralUsersService generalUsersService, StandardPasswordEncoder standardPE){
		super();
		this.generalUsersService=generalUsersService;
		this.standardPE=standardPE;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception{
		LOGGER.info("Initializing Users and Roles");
		
		try {
			this.fitnessUserPropertiesLoader=new FitnessUserPropertiesLoader();
			
			User userFromProperteis = fitnessUserPropertiesLoader.getUserDetailsFromProperties();
			User existedUser=initUserWithUsername(userFromProperteis);
			initUserRoles(existedUser);
		} catch (IOException e) {
			LOGGER.info("Error while loading properties file --> "+e.getMessage());
//			throw e;
		}	
		catch (FitnessUserPropertyNotFoundException e) {
			LOGGER.info("Error in properties file --> "+e.getMessage());
		}
		
		
	}

	private void initUserRoles(User user) {
		List<Role> roles=generalUsersService.getRolesbyUser(user);
		if(!hasUserAdminRole(roles)){
			generalUsersService.addUserRole(Roles.SystemAdmin.toString(), user);
			LOGGER.info("Add role: "+Roles.SystemAdmin+" to user: "+user);
		}
		else{
			LOGGER.info("The user already has "+Roles.SystemAdmin+" role! user id: "+user.getId());
		}
	}


	private User initUserWithUsername(User userFromProperteis){
		User user = null;
		try {
			user = generalUsersService.getUserByUsername(userFromProperteis.getUsername());
			LOGGER.info("Administrator already exists! "+user.toString());
		} catch (FitnessDaoException e) {
			user=generalUsersService.addUser(userFromProperteis.getFullName(), userFromProperteis.getUsername(),
					standardPE.encode(userFromProperteis.getPassword()), userFromProperteis.getEmail(), userFromProperteis.getMobile(), new Date());
			
			LOGGER.info("Save new User: "+user.toString());
		}
		
		return user;
	}

	private boolean hasUserAdminRole(List<Role> roles) {
		boolean result=false;
		for(Role r : roles){
			if(r.getName().equals(Roles.SystemAdmin.toString())){
				result=true;
				break;
			}
		}
		
		return result;
	}
}
