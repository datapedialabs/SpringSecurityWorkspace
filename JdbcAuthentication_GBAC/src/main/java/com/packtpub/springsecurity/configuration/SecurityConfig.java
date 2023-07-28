package com.packtpub.springsecurity.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

	@Bean
	@Override
	public UserDetailsManager userDetailsService() {
       JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
       manager.setDataSource(dataSource);
       manager.setEnableAuthorities(false);
       manager.setEnableGroups(true);
       manager.setGroupAuthoritiesByUsernameQuery(JdbcUserDetailsManager.DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY);
       return manager;
	}
}
