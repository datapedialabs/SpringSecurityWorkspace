package com.packtpub.springsecurity.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.packtpub.springsecurity.core.authority.CalendarUserAuthorityUtils;
import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.domain.CalendarUser;

@Service
public class CalendarUserDetailsService implements UserDetailsService {
	private final CalendarUserDao calendarUserDao;

	@Autowired
	public CalendarUserDetailsService(CalendarUserDao calendarUserDao) {
		this.calendarUserDao = calendarUserDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CalendarUser user = calendarUserDao.findUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username");
		}
		Collection<? extends GrantedAuthority> authorities = CalendarUserAuthorityUtils.createAuthorities(user);
		return new User(user.getEmail(), user.getPassword(), authorities);
	}
}
