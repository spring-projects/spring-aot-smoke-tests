package com.example.servlet.tomcat;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class TestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String[] names = req.getParameterValues("name");
		String name;
		if (names == null || names.length == 0) {
			name = "world";
		}
		else {
			name = names[0];
		}
		resp.getWriter().printf("Hello %s", name);
	}

}
