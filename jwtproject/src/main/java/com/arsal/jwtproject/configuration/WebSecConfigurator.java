package com.arsal.jwtproject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.arsal.jwtproject.filters.JwtRequestInterceptorFilter;
import com.arsal.jwtproject.services.UserDetailService;

@EnableWebSecurity
public class WebSecConfigurator extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private JwtRequestInterceptorFilter jwtRequestInterceptorFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailService);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	/**
	 * when /authenticate endpoint is hit, we want user to be authenticated always, so that we can get the UserDetails and genenrate a 
	 * jwt token.
	 * So this method disables auth check for /authenticate and requires auth for any other requests
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll()
												 .anyRequest().authenticated()
												 .and()
												 .sessionManagement()
												 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//we want jwt validation, which we will do ourself. (SessionCreationPolicy.STATELESS)
		//we dont want spring security to manage sessions for us 
		//effectively skip parts of the Spring Security filter chain — mainly the session-related parts 
		//such as HttpSessionSecurityContextRepository, SessionManagementFilter and RequestCacheFilter.
		//direct implication that cookies are not used, and so each and every request needs to be re-authenticated.
		http.addFilterBefore(jwtRequestInterceptorFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	

}
