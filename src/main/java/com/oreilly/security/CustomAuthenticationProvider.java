package com.oreilly.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.oreilly.security.domain.entities.AutoUser;
import com.oreilly.security.domain.repositories.AutoUserRepository;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private AutoUserRepository repo;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;
		AutoUser user = repo.findByUsername(token.getName());
		if(user==null || !user.getPassword().equalsIgnoreCase(token.getCredentials().toString())
				||!token.getMake().equalsIgnoreCase("subaru")){
			throw new BadCredentialsException("The credentials are invalid");
		}
		return new CustomAuthenticationToken(user, user.getPassword(),user.getAuthorities(),token.getMake());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return CustomAuthenticationToken.class.equals(authentication);
	}

}
