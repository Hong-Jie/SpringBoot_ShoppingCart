package com.elvisjacob.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.elvisjacob.dao.AccountDAO;
import com.elvisjacob.entities.Account;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AccountDAO accountDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountDao.findAccount(username);
		if (account == null) {
			throw new UsernameNotFoundException(
					"User " + username + " not found!");
		}
		
		String role = account.getRole();
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority auth = new SimpleGrantedAuthority(role);
		grantList.add(auth);
		
		UserDetails userDetails = new User(
				account.getUsername(), 
				account.getPassword(), 
				account.isActive(), 
				/*account not expired*/ 	true,
				/*credentials not expired*/ true,
				/*account not locked*/		true, grantList);

		return userDetails;
	}

}
