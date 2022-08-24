package com.example.security.method;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import org.springframework.stereotype.Service;

@Service
public class Jsr250ProtectedService {

	public void anonymous() {
		System.out.println("anonymous()");
	}

	@PermitAll
	public void permitAll() {
		System.out.println("permitAll()");
	}

	@DenyAll
	public void denyAll() {
		System.out.println("denyAll()");
	}

	@RolesAllowed("USER")
	public void user() {
		System.out.println("user()");
	}

	@RolesAllowed("ADMIN")
	public void admin() {
		System.out.println("admin()");
	}

}
