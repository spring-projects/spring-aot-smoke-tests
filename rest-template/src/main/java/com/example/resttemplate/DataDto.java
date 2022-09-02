package com.example.resttemplate;

public class DataDto {

	private String url;

	private String method;

	public DataDto() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "DataDto{" + "url='" + url + '\'' + ", method='" + method + '\'' + '}';
	}

}
