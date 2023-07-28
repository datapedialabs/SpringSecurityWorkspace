package com.packtpub.springsecurity.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Customized configuration to disable both frameset and csrf() in order to
		// access H2 console in web browser
		// http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic().and().headers().frameOptions().disable().and().csrf().disable();

		// Customized Configuration to use our customized login page. The configuration
		// below requires authentication to every URL except login page.
/*		http.authorizeRequests()
        	//The below line is to avoid continuous page-redirection error
        	.antMatchers("/login/form").hasAnyRole("ANONYMOUS", "USER")
        	.antMatchers("/**").hasRole("USER")   // all URLs except login page to require the role "ROLE_USER".
        	.and().formLogin()
        	.loginPage("/login/form")
                   .loginProcessingUrl("/processlogin")
                   .failureUrl("/login/form?error")
                   .usernameParameter("username")
                   .passwordParameter("password")
            .and().logout()
            // .logoutUrl("/logout") //default, so no need
            .logoutSuccessUrl("/login/form?logout")
            .and().httpBasic()
            .and().headers().frameOptions().disable()
            .and().csrf().disable();*/

		//Adding Role-based authorization
		/*http.authorizeRequests()
		.antMatchers("/resources/**").hasAnyRole("ANONYMOUS", "USER")
		.antMatchers("/").hasAnyRole("ANONYMOUS", "USER")
    	.antMatchers("/login/form").hasAnyRole("ANONYMOUS", "USER")
    	.antMatchers("/admin/**").hasAnyRole("ANONYMOUS", "USER")
    	.antMatchers("/events/").hasRole("ADMIN")
    	.antMatchers("/**").hasRole("USER")   
    	.and().formLogin()
    	.loginPage("/login/form")
               .loginProcessingUrl("/processlogin")
               .failureUrl("/login/form?error")
               .usernameParameter("username")
               .passwordParameter("password")
        .and().logout()
        // .logoutUrl("/logout") //default, so no need
        .logoutSuccessUrl("/login/form?logout")
        .and().httpBasic()
        .and().exceptionHandling().accessDeniedPage("/errors/403")
        .and().headers().frameOptions().disable()
        .and().csrf().disable();*/
		
		// Leverage Spring Expression Language (SpEL) 
		http.authorizeRequests()
		.antMatchers("/resources/**").permitAll()
		.antMatchers("/").permitAll()
    	.antMatchers("/login/**").permitAll()
    	.antMatchers("/admin/**").permitAll()
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

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("user1@example.com").password("user1").roles("USER").and()
				.withUser("user2@example.com").password("user2").roles("USER").and()
				.withUser("admin1@example.com").password("admin1").roles("USER", "ADMIN");
	}

}
