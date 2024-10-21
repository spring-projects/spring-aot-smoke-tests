/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

	private final PreAuthorizeProtectedService preAuthorizeProtectedService;

	private final SecuredProtectedService securedProtectedService;

	private final Jsr250ProtectedService jsr250ProtectedService;

	private final PostAuthorizeProtectedService postAuthorizeProtectedService;

	private final UserDetailsManager userDetailsManager;

	public CLR(PreAuthorizeProtectedService preAuthorizeProtectedService,
			SecuredProtectedService securedProtectedService, Jsr250ProtectedService jsr250ProtectedService,
			PostAuthorizeProtectedService postAuthorizeProtectedService, UserDetailsManager userDetailsManager) {
		this.preAuthorizeProtectedService = preAuthorizeProtectedService;
		this.securedProtectedService = securedProtectedService;
		this.jsr250ProtectedService = jsr250ProtectedService;
		this.postAuthorizeProtectedService = postAuthorizeProtectedService;
		this.userDetailsManager = userDetailsManager;
	}

	@Override
	public void run(String... args) {
		testAnonymous();
		testUser();
		testAdmin();
		testPostAuthorizeAnonymous();
		testPostAuthorizeUser();
		testPostAuthorizeAdmin();
		testSecuredAnonymous();
		testSecuredUser();
		testSecuredAdmin();
		testJsr250Anonymous();
		testJsr250User();
		testJsr250Admin();
		testJsr250PermitAll();
		testJsr250DenyAll();
	}

	private void testAnonymous() {
		impersonateAnonymous();
		this.preAuthorizeProtectedService.anonymous();
		System.out.println("testAnonymous(): preAuthorizeProtectedService.anonymous() worked as anonymous");
	}

	private void testPostAuthorizeAnonymous() {
		impersonateAnonymous();
		this.postAuthorizeProtectedService.anonymous();
		System.out
			.println("testPostAuthorizeAnonymous(): postAuthorizeProtectedService.anonymous() worked as anonymous");
	}

	private void testSecuredAnonymous() {
		impersonateAnonymous();
		this.securedProtectedService.anonymous();
		System.out.println("testSecuredAnonymous(): securedProtectedService.anonymous() worked as anonymous");
	}

	private void testJsr250Anonymous() {
		impersonateAnonymous();
		this.jsr250ProtectedService.anonymous();
		System.out.println("testJsr250Anonymous(): jsr250ProtectedService.anonymous() worked as anonymous");
	}

	private void testUser() {
		impersonateAnonymous();
		try {
			this.preAuthorizeProtectedService.user();
			throw new IllegalStateException("testUser(): preAuthorizeProtectedService.user() worked as anonymous");
		}
		catch (AuthenticationException ex) {
			System.out.println("testUser(): preAuthorizeProtectedService.user() failed as anonymous");
		}

		impersonateUser();
		this.preAuthorizeProtectedService.user();
		System.out.println("testUser(): preAuthorizeProtectedService.user() worked as user");
	}

	private void testPostAuthorizeUser() {
		impersonateAnonymous();
		try {
			this.postAuthorizeProtectedService.user();
			throw new IllegalStateException(
					"testPostAuthorizeUser(): postAuthorizeProtectedService.user() worked as anonymous");
		}
		catch (IllegalArgumentException ex) {
			System.out.println("testPostAuthorizeUser(): postAuthorizeProtectedService.user() failed as anonymous");
		}

		impersonateUser();
		this.postAuthorizeProtectedService.user();
		System.out.println("testPostAuthorizeUser(): postAuthorizeProtectedService.user() worked as user");
	}

	private void testSecuredUser() {
		impersonateAnonymous();
		try {
			this.securedProtectedService.user();
			throw new IllegalStateException("testSecuredUser(): securedProtectedService.user() worked as anonymous");
		}
		catch (AuthenticationException ex) {
			System.out.println("testSecuredUser(): securedProtectedService.user() failed as anonymous");
		}

		impersonateUser();
		this.securedProtectedService.user();
		System.out.println("testSecuredUser(): securedProtectedService.user() worked as user");
	}

	private void testJsr250User() {
		impersonateAnonymous();
		try {
			this.jsr250ProtectedService.user();
			throw new IllegalStateException("testJsr250User(): jsr250ProtectedService.user() worked as anonymous");
		}
		catch (AuthenticationException ex) {
			System.out.println("testJsr250User(): jsr250ProtectedService.user() failed as anonymous");
		}

		impersonateUser();
		this.jsr250ProtectedService.user();
		System.out.println("testJsr250User(): jsr250ProtectedService.user() worked as user");
	}

	private void testAdmin() {
		impersonateAnonymous();
		try {
			this.preAuthorizeProtectedService.admin();
			throw new IllegalStateException("testAdmin(): preAuthorizeProtectedService.admin() worked as anonymous");
		}
		catch (AuthenticationException ex) {
			System.out.println("testAdmin(): preAuthorizeProtectedService.admin() failed as anonymous");
		}

		impersonateUser();
		try {
			this.preAuthorizeProtectedService.admin();
			throw new IllegalStateException("testAdmin(): preAuthorizeProtectedService.admin() worked as user");
		}
		catch (AccessDeniedException ex) {
			System.out.println("testAdmin(): preAuthorizeProtectedService.admin() failed as user");
		}

		impersonateAdmin();
		this.preAuthorizeProtectedService.admin();
		System.out.println("testAdmin(): preAuthorizeProtectedService.admin() worked as admin");
	}

	private void testPostAuthorizeAdmin() {
		impersonateAnonymous();
		try {
			this.postAuthorizeProtectedService.admin();
			throw new IllegalStateException(
					"testPostAuthorizeAdmin(): postAuthorizeProtectedService.admin() worked as anonymous");
		}
		catch (IllegalArgumentException ex) {
			System.out.println("testPostAuthorizeAdmin(): postAuthorizeProtectedService.admin() failed as anonymous");
		}

		impersonateUser();
		try {
			this.postAuthorizeProtectedService.admin();
			throw new IllegalStateException(
					"testPostAuthorizeAdmin(): postAuthorizeProtectedService.admin() worked as user");
		}
		catch (AccessDeniedException ex) {
			System.out.println("testPostAuthorizeAdmin(): postAuthorizeProtectedService.admin() failed as user");
		}

		impersonateAdmin();
		this.postAuthorizeProtectedService.admin();
		System.out.println("testPostAuthorizeAdmin(): postAuthorizeProtectedService.admin() worked as admin");
	}

	private void testSecuredAdmin() {
		impersonateAnonymous();
		try {
			this.securedProtectedService.admin();
			throw new IllegalStateException("testSecuredAdmin(): securedProtectedService.admin() worked as anonymous");
		}
		catch (AuthenticationException ex) {
			System.out.println("testSecuredAdmin(): securedProtectedService.admin() failed as anonymous");
		}

		impersonateUser();
		try {
			this.securedProtectedService.admin();
			throw new IllegalStateException("testSecuredAdmin(): securedProtectedService.admin() worked as user");
		}
		catch (AccessDeniedException ex) {
			System.out.println("testSecuredAdmin(): securedProtectedService.admin() failed as user");
		}

		impersonateAdmin();
		this.securedProtectedService.admin();
		System.out.println("testSecuredAdmin(): securedProtectedService.admin() worked as admin");
	}

	private void testJsr250Admin() {
		impersonateAnonymous();
		try {
			this.jsr250ProtectedService.admin();
			throw new IllegalStateException("testJsr250Admin(): jsr250ProtectedService.admin() worked as anonymous");
		}
		catch (AuthenticationException ex) {
			System.out.println("testJsr250Admin(): jsr250ProtectedService.admin() failed as anonymous");
		}

		impersonateUser();
		try {
			this.jsr250ProtectedService.admin();
			throw new IllegalStateException("testJsr250Admin(): jsr250ProtectedService.admin() worked as user");
		}
		catch (AccessDeniedException ex) {
			System.out.println("testJsr250Admin(): jsr250ProtectedService.admin() failed as user");
		}

		impersonateAdmin();
		this.jsr250ProtectedService.admin();
		System.out.println("testJsr250Admin(): jsr250ProtectedService.admin() worked as admin");
	}

	private void testJsr250DenyAll() {
		impersonateAnonymous();
		try {
			this.jsr250ProtectedService.denyAll();
			throw new IllegalStateException(
					"testJsr250DenyAll(): jsr250ProtectedService.denyAll() worked as anonymous");
		}
		catch (AccessDeniedException ex) {
			System.out.println("testJsr250DenyAll(): jsr250ProtectedService.denyAll() failed as anonymous");
		}

		impersonateUser();
		try {
			this.jsr250ProtectedService.denyAll();
			throw new IllegalStateException("testJsr250DenyAll(): jsr250ProtectedService.denyAll() worked as user");
		}
		catch (AccessDeniedException ex) {
			System.out.println("testJsr250DenyAll(): jsr250ProtectedService.denyAll() failed as user");
		}

		impersonateAdmin();
		try {
			this.jsr250ProtectedService.denyAll();
			System.out.println("testJsr250DenyAll(): jsr250ProtectedService.denyAll() worked as admin");
		}
		catch (AccessDeniedException ex) {
			System.out.println("testJsr250DenyAll(): jsr250ProtectedService.denyAll() failed as admin");
		}
	}

	private void testJsr250PermitAll() {
		impersonateAnonymous();
		this.jsr250ProtectedService.permitAll();
		System.out.println("testJsr250PermitAll(): jsr250ProtectedService.permitAll() worked as anonymous");

		impersonateUser();
		this.jsr250ProtectedService.permitAll();
		System.out.println("testJsr250PermitAll(): jsr250ProtectedService.permitAll() worked as user");

		impersonateAdmin();
		this.jsr250ProtectedService.permitAll();
		System.out.println("testJsr250PermitAll(): jsr250ProtectedService.permitAll() worked as admin");
	}

	private void impersonateAnonymous() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	private void impersonateUser() {
		UserDetails user = this.userDetailsManager.loadUserByUsername("user");
		SecurityContextHolder.getContext()
			.setAuthentication(
					new TestingAuthenticationToken(user, user.getPassword(), List.copyOf(user.getAuthorities())));
	}

	private void impersonateAdmin() {
		UserDetails user = this.userDetailsManager.loadUserByUsername("admin");
		SecurityContextHolder.getContext()
			.setAuthentication(
					new TestingAuthenticationToken(user, user.getPassword(), List.copyOf(user.getAuthorities())));
	}

}
