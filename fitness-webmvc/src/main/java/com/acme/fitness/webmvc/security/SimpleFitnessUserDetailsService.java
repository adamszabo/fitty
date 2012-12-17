package com.acme.fitness.webmvc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;

@Service("simpleFitnessUserDetailsService")
public class SimpleFitnessUserDetailsService implements UserDetailsService {
	
	@Autowired
	private GeneralUsersService gus;
	
	@Autowired
	StandardPasswordEncoder spe;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			User p = (User)this.gus.getUserByUsername(username);
			List<Role> roles = this.gus.getRolesbyUser(p);
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			for(Role r : roles){
				grantedAuthorities.add(new SimpleGrantedAuthority(r.getName()));
			}
			
			org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User
					(p.getUsername(), p.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
			
			return user;
			
		} catch (FitnessDaoException e) {
			throw new UsernameNotFoundException("No such user with username: "+username);
		}	
	}

	public GeneralUsersService getGus() {
		return gus;
	}

	public void setGus(GeneralUsersService gus) {
		this.gus = gus;
	}
	
	@PostConstruct
	public void init(){
		try {
			User admin=gus.getUserByUsername("admin");
			admin.setPassword(spe.encode("admin"));
			gus.updateUser(admin);
			System.out.println("HASHED: "+admin.getPassword());
		} catch (FitnessDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
