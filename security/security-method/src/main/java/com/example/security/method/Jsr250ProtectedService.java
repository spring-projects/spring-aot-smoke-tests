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
