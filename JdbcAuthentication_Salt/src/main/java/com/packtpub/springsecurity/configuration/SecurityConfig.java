package com.packtpub.springsecurity.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Leverage Spring Expression Language (SpEL) 
		http.authorizeRequests()
		.antMatchers("/resources/**").permitAll()
		.antMatchers("/").permitAll()
    	.antMatchers("/login/**").permitAll()
    	.antMatchers("/admin/**").permitAll()
    	.antMatchers("/signup/*").permitAll()
    	.antMatchers("/events/").hasRole("ADMIN")
    	.antMatchers("/**").hasRole("USER")   
    	.and()
    	.formLogin()
    	   .loginPage("/login/form")
               .loginProcessingUrl("/processlogin")
               .failureUrl("/login/form?error")
               .usernameParameter("username")
               .passwordParameter("password")
               .defaultSuccessUrl("/default", true)
        .and().logout()
        // .logoutUrl("/logout") //default, so no need
        .logoutSuccessUrl("/login/form?logout")
        .and().httpBasic()
        .and().exceptionHandling().accessDeniedPage("/errors/403")
        .and().headers().frameOptions().disable()
        .and().csrf().disable();
	}

	@Autowired
	private DataSource dataSource;
	
	private static String CUSTOM_USERS_BY_USERNAME_QUERY = "select email, password, true " +
            "from calendar_users where email = ?";

	private static String CUSTOM_AUTHORITIES_BY_USERNAME_QUERY = "select cua.id, cua.authority " +
                  "from calendar_users cu, calendar_user_authorities "+
                  "cua where cu.email = ? "+
                  "and cu.id = cua.calendar_user";


	@Bean
	@Override
	public UserDetailsManager userDetailsService() {
       JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
       manager.setDataSource(dataSource);
       manager.setUsersByUsernameQuery(CUSTOM_USERS_BY_USERNAME_QUERY);
       manager.setAuthoritiesByUsernameQuery(CUSTOM_AUTHORITIES_BY_USERNAME_QUERY);
       return manager;
	}
	
	/*@Bean
	public PasswordEncoder passwordEncoder(){
	       return new ShaPasswordEncoder(256);
	}*/
	
	@Bean
	 public PasswordEncoder passwordCryptoEncoder(){
	       return new StandardPasswordEncoder();
	 }

	
	@Override
	 public void configure(final AuthenticationManagerBuilder auth) throws Exception {
	        auth.jdbcAuthentication()
	                .dataSource(dataSource)	                
	                .usersByUsernameQuery(CUSTOM_USERS_BY_USERNAME_QUERY)
	                .authoritiesByUsernameQuery(CUSTOM_AUTHORITIES_BY_USERNAME_QUERY)
	        		//.passwordEncoder(passwordEncoder());
	                .passwordEncoder(passwordCryptoEncoder());
	 }

}
