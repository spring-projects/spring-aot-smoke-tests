package com.example.webmvc.jetty.dto;

public class Response {

	private String message;

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
