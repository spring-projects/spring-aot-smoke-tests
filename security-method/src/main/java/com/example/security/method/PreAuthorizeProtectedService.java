package com.example.security.method;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PreAuthorizeProtectedService {

	public void anonymous() {
		System.out.println("anonymous()");
	}

	@PreAuthorize("hasRole('USER')")
	public void user() {
		System.out.println("user()");
	}

	@PreAuthorize("hasRole('ADMIN')")
	public void admin() {
		System.out.println("admin()");
	}

}
