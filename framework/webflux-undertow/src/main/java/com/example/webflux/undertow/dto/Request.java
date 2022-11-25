package com.example.webflux.undertow.dto;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Request {

	private String message;

	public Request() {
	}

	public Request(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
