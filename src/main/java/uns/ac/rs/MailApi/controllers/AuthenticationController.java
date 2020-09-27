package uns.ac.rs.MailApi.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.security.TokenHelper;
import uns.ac.rs.MailApi.security.model.AuthenticationRequest;
import uns.ac.rs.MailApi.security.model.UserAuthenticationToken;

@CrossOrigin
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	@PostMapping
	public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest tokenRequest) throws AuthenticationException, IOException {
		UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword());
		
		final Authentication authentication;
		
		try {
			authentication = authenticationManager.authenticate(authenticationRequest);
		} catch (AuthenticationException ex) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		User user = (User) authentication.getPrincipal();
		
		String token = tokenHelper.generateToken(user.getUsername());
		long expiresIn = tokenHelper.getExiprationDate(token);
		
		return ResponseEntity.ok(new UserAuthenticationToken(token, expiresIn));
	}
	
}
