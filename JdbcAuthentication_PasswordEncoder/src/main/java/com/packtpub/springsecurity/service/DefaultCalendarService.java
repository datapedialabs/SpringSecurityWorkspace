package com.packtpub.springsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.dataaccess.EventDao;
import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;

/**
 * A default implementation of {@link CalendarService} that delegates to
 * {@link EventDao} and {@link CalendarUserDao}.
 *
 * @author Rob Winch
 *
 */
@Service
public class DefaultCalendarService implements CalendarService {
	private final EventDao eventDao;
	private final CalendarUserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsManager userDetailsManager;

	@Autowired
	public DefaultCalendarService(EventDao eventDao, CalendarUserDao userDao) {
		if (eventDao == null) {
			throw new IllegalArgumentException("eventDao cannot be null");
		}
		if (userDao == null) {
			throw new IllegalArgumentException("userDao cannot be null");
		}
		this.eventDao = eventDao;
		this.userDao = userDao;
	}

	public Event getEvent(int eventId) {
		return eventDao.getEvent(eventId);
	}

	public int createEvent(Event event) {
		System.out.println("###createEvent() DefaultCalendarService class");
		return eventDao.createEvent(event);
	}

	public List<Event> findForUser(int userId) {
		return eventDao.findForUser(userId);
	}

	public List<Event> getEvents() {
		return eventDao.getEvents();
	}

	public CalendarUser getUser(int id) {
		return userDao.getUser(id);
	}

	public CalendarUser findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	public List<CalendarUser> findUsersByEmail(String partialEmail) {
		return userDao.findUsersByEmail(partialEmail);
	}

	public int createUser(CalendarUser user) {
		System.out.println("###createUser() DefaultCalendarService class");
        String encodedPassword = passwordEncoder.encodePassword(user.getPassword(), null);
        user.setPassword(encodedPassword);
		int userId = userDao.createUser(user);
		userDao.createAuthorities(userId);
		return userId;
	}
}