package com.packtpub.springsecurity.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.LdapUsernameToDnMapper;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
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

	@Value("${spring.ldap.embedded.port}")
	private int LDAP_PORT;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication()
			.userSearchBase("ou=Users")
			.userSearchFilter("(uid={0})")
			.groupSearchBase("ou=Groups")
			.groupSearchFilter("(uniqueMember={0})")
			.contextSource(contextSource())
			.passwordCompare()
	        .passwordEncoder(new LdapShaPasswordEncoder()) //Supports {SHA} and {SSHA}
	        .passwordAttribute("userPassword"); //optional because the default attribute is 'userPassword'
	}

	@Bean
	@Override
	public UserDetailsManager userDetailsService() {
		LdapUserDetailsManager manager = new LdapUserDetailsManager(contextSource());
		LdapUsernameToDnMapper usernameMapper = new DefaultLdapUsernameToDnMapper("ou=Users", "uid");
		manager.setUsernameMapper(usernameMapper);
		manager.setGroupSearchBase("ou=Groups");
		return manager;
	}

	@Bean
	public DefaultSpringSecurityContextSource contextSource() {
		return new DefaultSpringSecurityContextSource(Arrays.asList("ldap://localhost:" + LDAP_PORT + "/"),
				"dc=jbcpcalendar,dc=com");
	}

}
