Your code should now look like this: calendar06.01-calendar.

Ref Project:
===========
	-- JdbcAuthentication

Goals
=====
   -- Ldap Authentication
   -- Embedded Apache DS
   -- Configuring LDIF browser (optional)	
   -- Signup & sign-in
   -- Create a new entry in Ldap and insert calendar user details in CALENDAR_USERS table in database
   -- Using LdapAuthenticationProvider during sing-in and LdapUserDetailsManager during sign-up

Modified/Added Files
====================
    -- build.gradle  												[modifications]
    -- application.yml 												[modifications] 
    -- src/main/resources/database/h2/security-schema.sql  			[removed]
    -- src/main/resources/database/h2/security-users.sql  			[removed]
    -- src/main/resources/database/h2/security-user-authorities.sql [removed]
    -- src/main/resources/ldif/calendar.ldif						[new]
	-- DataSourceConfig.java 										[modifications]	
	-- SecurityConfig.java 											[modifications]
	-- DefaultCalendarService.java 									[modifications]
	-- CalendarUserContext.java 									[modifications]

