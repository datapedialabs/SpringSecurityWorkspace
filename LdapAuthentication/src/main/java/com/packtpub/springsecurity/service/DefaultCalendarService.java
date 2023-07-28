package com.packtpub.springsecurity.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.packtpub.springsecurity.core.authority.CalendarUserAuthorityUtils;
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
		Collection<? extends GrantedAuthority> authorities = CalendarUserAuthorityUtils.createAuthorities(user);

		InetOrgPerson.Essence essence = new InetOrgPerson.Essence();
        essence.setUsername(user.getEmail());
        essence.setPassword(user.getPassword());
        essence.setOu("Users");
        essence.setUid(user.getEmail());
        essence.setSn("User");
        essence.setDn("dc=jbcpcalendar,dc=com");
        //essence.setO(orgMrn);
        essence.setCn(new String[] {user.getFirstName(), user.getLastName()});
        essence.setAuthorities(authorities);
        userDetailsManager.createUser(essence.createUserDetails()); //insert entry into ldap tree

		return userDao.createUser(user); //insert new record into CALENDAR_USERS table
	}
}