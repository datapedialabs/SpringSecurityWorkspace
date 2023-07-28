package com.packtpub.springsecurity.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.packtpub.springsecurity.authentication.ProviderConnectionSignup;
import com.packtpub.springsecurity.authentication.SocialAuthenticationUtils;

@Configuration
public class SocialConfig {
	@Autowired
    private Environment env;
	
    public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        
		// Register Twitter with connection factory 
        registry.addConnectionFactory(new TwitterConnectionFactory(
        		env.getProperty("spring.social.twitter.appId"),
        		env.getProperty("spring.social.twitter.appSecret")));
        
        // Register Facebook with connection factory 
        registry.addConnectionFactory(new FacebookConnectionFactory(
        		env.getProperty("spring.social.facebook.appId"),
        		env.getProperty("spring.social.facebook.appSecret")));
		
        // Register LinkedIn with connection factory 
        registry.addConnectionFactory(new LinkedInConnectionFactory(
        		env.getProperty("spring.social.linkedin.app-id"),
        		env.getProperty("spring.social.linkedin.app-secret")));
        
        // Register google with connection factory
        registry.addConnectionFactory(new GoogleConnectionFactory(
        		env.getProperty("spring.social.google.appId"),
        		env.getProperty("spring.social.google.appSecret")));
        
        // Register github with connection factory
        registry.addConnectionFactory(new GitHubConnectionFactory(
				env.getProperty("spring.social.github.appId"),
				env.getProperty("spring.social.github.appSecret")));
            
        return registry;
    }
    
    @Autowired
	private DataSource dataSource;
	
	@Bean
	public UsersConnectionRepository getUsersConnectionRepository() {
		//ConnectionRepository uses TextEncryptor to encrypt credentials when persisting connections.
        TextEncryptor textEncryptor = Encryptors.noOpText();
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), textEncryptor);
    }

	/**
	 * create a custom authenticate utility method to create an Authentication
	 * object and add it to our SecurityContext based on the returned provider
	 * details.
	 */
	@Bean
	public SignInAdapter authSignInAdapter() {
		return (userId, connection, request) -> {
			SocialAuthenticationUtils.authenticate(connection);
			//return null; //Home page will be displayed
			//return "/"; //Home page will be displayed
			return "/events/my"; //My events page will be displayed
		};
	}

    @Autowired
	private ProviderConnectionSignup providerConnectionSignup;

	/**
	 * Configuring a {@link ProviderSignInController} to intercept OAuth2 requests
	 * that will be used to initiate an OAuth2 handshake with the specified OAuth2
	 * provider.
	 * 
	 * @return
	 */
	@Bean
	public ProviderSignInController providerSignInController() {
		System.out.println("###providerSignInController() in SocialConfig class");
		((JdbcUsersConnectionRepository)getUsersConnectionRepository()).setConnectionSignUp(providerConnectionSignup);
		return new ProviderSignInController(connectionFactoryLocator(), getUsersConnectionRepository(), authSignInAdapter());
	}
}
