package com.example.servlet.componentscan;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class TestListener implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		System.out.println("TestListener.requestInitialized");
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		System.out.println("TestListener.requestDestroyed");
	}

}
