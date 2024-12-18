package com.example.security.method;

import jakarta.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;

public class AuthorizableObject {

	private final String property = "value";

	@HasRole("ADMIN")
	public String getAdminProperty() {
		return this.property;
	}

	@HasRole("USER")
	public String getUserProperty() {
		return this.property;
	}

	@RolesAllowed("USER")
	public String getUserPropertyJsr250() {
		return this.property;
	}

	@Secured("ROLE_USER")
	public String getUserPropertySecured() {
		return this.property;
	}

}
