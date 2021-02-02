package br.com.logisticawmj.wmj.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.logisticawmj.wmj.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
