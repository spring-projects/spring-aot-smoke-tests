package com.example.disposable;

public abstract class AbstractDisposableBean implements DisposableInterface {

	@Override
	public void shutdown() {
		System.out.println("Invoking AbstractDisposableBean#shutdown");
	}

}
