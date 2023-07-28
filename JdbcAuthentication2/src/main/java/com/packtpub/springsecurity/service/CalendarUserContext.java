package com.packtpub.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.packtpub.springsecurity.domain.CalendarUser;

@Component
public class CalendarUserContext implements UserContext {
	private final CalendarService calendarService;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
	public CalendarUserContext(CalendarService userService) {
		if (userService == null) {
			throw new IllegalArgumentException("userService cannot be null");
		}
		this.calendarService = userService;
	}

	@Override
	public CalendarUser getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
          	return null;
        }
        String email = ((UserDetails)authentication.getPrincipal()).getUsername();           
        return calendarService.findUserByEmail(email);
	}

	@Override
	public void setCurrentUser(CalendarUser user) {
	      UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
	      Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,  
	                                                    userDetails.getPassword(), userDetails.getAuthorities());
	      SecurityContextHolder.getContext().setAuthentication(authentication);

	}
}