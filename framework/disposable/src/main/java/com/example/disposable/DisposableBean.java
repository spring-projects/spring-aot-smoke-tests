package com.example.disposable;

public class DisposableBean implements DisposableInterface {

	@Override
	public void shutdown() {
		System.out.println("Invoking DisposableBean#shutdown");
	}

}
