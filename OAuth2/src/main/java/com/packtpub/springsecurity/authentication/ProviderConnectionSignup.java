package com.packtpub.springsecurity.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.domain.CalendarUser;

/**
 * This ConnectionSignUp can be used to put the Social
 * User into your local database.
 */
@Service
public class ProviderConnectionSignup implements ConnectionSignUp {
	static {
		System.out.println("###static blk in ProviderConnectionSignup class");
	}

    @Autowired
    private CalendarUserDao calendarUserDao;

    @Override
    public String execute(Connection<?> connection) {
    	System.out.println("###execute() in ProviderConnectionSignup class");
        CalendarUser user = SocialAuthenticationUtils.createCalendarUserFromProvider(connection);

        calendarUserDao.createUser(user);
        System.out.println("###execute() in ProviderConnectionSignup class: " +user.getEmail());
        return user.getEmail();
    }
}
