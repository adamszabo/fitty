package com.acme.fitness.webmvc.initialization;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.acme.fitness.domain.users.User;

public class FitnessUserPropertiesLoader {
	private static final String CONFIG_PROPERTIES="fitness-studio.properties";
	
	private Properties properties;
	
	public FitnessUserPropertiesLoader() throws IOException {
		this(PropertiesLoaderUtils.loadAllProperties(CONFIG_PROPERTIES));
	}
	
	FitnessUserPropertiesLoader(Properties properties) throws IOException {
		this.properties=properties;
		
		if(properties.isEmpty()){
			throw new IOException("Property file not found with name: "+CONFIG_PROPERTIES);
		}
	}

	public User getUserDetailsFromProperties() throws FitnessUserPropertyNotFoundException{
		User result=new User();
		
		result.setFullName(getPropertyWithNullCheck("admin.fullname"));
		result.setUsername(getPropertyWithNullCheck("admin.username"));
		result.setEmail(getPropertyWithNullCheck("admin.email"));
		result.setPassword(getPropertyWithNullCheck("admin.password"));
		result.setMobile(getPropertyWithNullCheck("admin.phone"));
		
		return result;
	}
	
	private String getPropertyWithNullCheck(String key) throws FitnessUserPropertyNotFoundException{
		String result=properties.getProperty(key);
		
		if(result==null){
			throw new FitnessUserPropertyNotFoundException(key);
		}
		
		return result;
	}
}
