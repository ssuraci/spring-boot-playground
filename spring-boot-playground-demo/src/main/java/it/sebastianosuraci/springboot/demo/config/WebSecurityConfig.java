package it.sebastianosuraci.springboot.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	protected static final String API_CONTEXT="/api/demo/**";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().and()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, API_CONTEXT).permitAll()
				.antMatchers(HttpMethod.POST, API_CONTEXT).permitAll()
				.antMatchers(HttpMethod.DELETE, API_CONTEXT).permitAll()
				.antMatchers(HttpMethod.PUT, API_CONTEXT).permitAll()
	
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	    auth.inMemoryAuthentication()
	        .withUser("user1")
	        .password("password1")
	        .roles("ADMIN")
	        .and()
	        .withUser("user2")
	        .password("password1")
	        .roles("USER");
	}
	
}