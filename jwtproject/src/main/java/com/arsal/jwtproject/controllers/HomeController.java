package com.arsal.jwtproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.arsal.jwtproject.models.AuthenticationRequest;
import com.arsal.jwtproject.models.AuthenticationResponse;
import com.arsal.jwtproject.services.JwtService;
import com.arsal.jwtproject.services.UserDetailService;

@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	AuthenticationManager authMgr;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	JwtService jwtService;
	
	@RequestMapping("/home")
	public String getHome()
	{
		return "voila. you are at home page";
	}
	
	/**
	 * this method is basically acting like a target server which needs to fetch user details from db, check if it matches the request credentials,
	 * authenticate, generate jwt token, send back to client.
	 */
	
	/**
	 * we want user to access '/authenticate' endpoint , without any login check, so that we can get the UserDetails loaded.
	 * From that we generate jwt token ourself
	 * see WebSecConfigurator class
	 */
	
	/**
	 * send a postman request for this. POST
	 * body:
	 *  {
     *   "username": "user1",
     *   "password": "pass1"
     *  }
     * url : http://localhost:8069/authenticate
     * No Auth
     * Accept/Content-type : application/json
     * The response header body contain a jwt token
	 */
	
	/**
	 * send a postman request GET
	 * url : http://localhost:8069/home
	 * No Auth
	 * Accept/Content-type : application/json
	 * headers:
	 *  Authorization : Bearer <jwt>
	 */
	@RequestMapping(value="/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> getJwt(@RequestBody AuthenticationRequest authReq) throws Exception
	{
		try {
			authMgr.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUserName(), authReq.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("wrong creds brother.", e);
		}
		
		//anyhow we have hard coded username and password
		UserDetails userDetails = userDetailService.loadUserByUsername(authReq.getUserName());
		
		String jwt = jwtService.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
