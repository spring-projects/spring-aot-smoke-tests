package com.example.rsocket.dto;

public class Message {

	private String origin;

	private String message;

	public Message() {
	}

	public Message(String origin, String message) {
		this.origin = origin;
		this.message = message;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message{" + "origin='" + origin + '\'' + ", message='" + message + '\'' + '}';
	}

}
