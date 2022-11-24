package com.example.webflux.jetty.dto;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {

	private String message;

	public Response() {
	}

	public Response(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
