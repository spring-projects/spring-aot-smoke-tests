package com.example.security.method;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private final ProtectedService protectedService;

	private final UserDetailsManager userDetailsManager;

	public CLR(ProtectedService protectedService, UserDetailsManager userDetailsManager) {
		this.protectedService = protectedService;
		this.userDetailsManager = userDetailsManager;
	}

	@Override
	public void run(String... args) {
		testAnonymous();
		testUser();
		testAdmin();
	}

	private void testAdmin() {
		impersonateAnonymous();
		try {
			this.protectedService.admin();
			throw new IllegalStateException("testAdmin(): protectedService.admin() worked as anonymous");
		}
		catch (AuthenticationException ex) {
			System.out.println("testAdmin(): protectedService.admin() failed as anonymous");
		}

		impersonateUser();
		try {
			this.protectedService.admin();
			throw new IllegalStateException("testAdmin(): protectedService.admin() worked as user");
		}
		catch (AccessDeniedException ex) {
			System.out.println("testAdmin(): protectedService.admin() failed as user");
		}

		impersonateAdmin();
		this.protectedService.admin();
		System.out.println("testAdmin(): protectedService.admin() worked as admin");
	}

	private void testAnonymous() {
		impersonateAnonymous();
		this.protectedService.anonymous();
		System.out.println("testAnonymous(): protectedService.anonymous() worked as anonymous");
	}

	private void testUser() {
		impersonateAnonymous();
		try {
			this.protectedService.user();
			throw new IllegalStateException("testUser(): protectedService.user() worked as anonymous");
		}
		catch (AuthenticationException ex) {
			System.out.println("testUser(): protectedService.user() failed as anonymous");
		}

		impersonateUser();
		this.protectedService.user();
		System.out.println("testUser(): protectedService.user() worked as user");
	}

	private void impersonateAnonymous() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	private void impersonateUser() {
		UserDetails user = this.userDetailsManager.loadUserByUsername("user");
		SecurityContextHolder.getContext().setAuthentication(
				new TestingAuthenticationToken(user, user.getPassword(), List.copyOf(user.getAuthorities())));
	}

	private void impersonateAdmin() {
		UserDetails user = this.userDetailsManager.loadUserByUsername("admin");
		SecurityContextHolder.getContext().setAuthentication(
				new TestingAuthenticationToken(user, user.getPassword(), List.copyOf(user.getAuthorities())));
	}

}
