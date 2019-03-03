package com.oreilly.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.oreilly.security.domain.entities.AutoUser;
import com.oreilly.security.domain.repositories.AutoUserRepository;

@Component("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AutoUserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AutoUser user = repo.findByUsername(username);
		
		return new User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()));
	}

}
