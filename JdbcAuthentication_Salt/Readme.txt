Your code should now look like this: calendar04.05-calendar.

Ref Project
===========
	-- JdbcAuthetication_PasswordEncoder

Goals
=====
	-- Password Hashing using Salt

Modified/Added Files
====================
	-- SecurityConfig.java						[modified]
	-- /databse/h2/calendar-sha256.sql			[removed]
	-- /databse/h2/calendar-saltedsha256.sql 	[new]
	-- DataSourceConfig.java					[modified]
	-- Sha256PasswordEncoderMain.java			[removed]
	-- CryptoSha256PasswordEncoderMain.java		[new]
	-- DefaultCalendarService.java				[modified]


		
