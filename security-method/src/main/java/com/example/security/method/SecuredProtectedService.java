package com.example.security.method;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class SecuredProtectedService {

	public void anonymous() {
		System.out.println("anonymous()");
	}

	@Secured("ROLE_USER")
	public void user() {
		System.out.println("user()");
	}

	@Secured("ROLE_ADMIN")
	public void admin() {
		System.out.println("admin()");
	}

}
