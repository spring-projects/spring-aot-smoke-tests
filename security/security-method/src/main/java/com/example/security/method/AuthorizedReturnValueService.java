package com.example.security.method;

import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Service;

@Service
public class AuthorizedReturnValueService {

	@AuthorizeReturnObject
	public AuthorizableObject getAuthorizedObject() {
		return new AuthorizableObject();
	}

	public AuthorizableObject getObject() {
		return new AuthorizableObject();
	}

}
