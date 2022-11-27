package com.arsal.jwtproject.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.arsal.jwtproject.services.JwtService;
import com.arsal.jwtproject.services.UserDetailService;

@Component
public class JwtRequestInterceptorFilter extends OncePerRequestFilter{

	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	JwtService jwtService;
	
	/**
	 * for every request, we will check if there is a jwt token in the request and try to validate.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get the request header and check if jwt is there
		String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer "))
		{
			jwt = authHeader.substring(7);
			username = jwtService.extractUserName(jwt);
		}
		
		//check if any authentication object already exists
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//if not, validate jwt
		if(username != null && authentication == null)
		{
			UserDetails userDetails = userDetailService.loadUserByUsername(username);
			
			if(jwtService.validateToken(jwt, userDetails))
			{
				//once jwt is validated, we need to set the Authentication object
				
				//create new Authentication object
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				//set the addresses, session id(if any) etc using the request
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//set the Authentication object
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		//job of our filter is done. pass it on to others
		filterChain.doFilter(request, response);
	}
	
	

}
