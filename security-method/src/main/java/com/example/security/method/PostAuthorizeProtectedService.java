package com.example.security.method;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PostAuthorizeProtectedService {

	public void anonymous() {
		System.out.println("anonymous()");
	}

	@PostAuthorize("returnObject == authentication?.name")
	public String user() {
		System.out.println("user()");
		return "user";
	}

	@PostAuthorize("returnObject == authentication?.name")
	public String admin() {
		System.out.println("admin()");
		return "admin";
	}

}
