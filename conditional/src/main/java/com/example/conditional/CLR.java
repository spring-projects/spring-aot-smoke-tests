package com.example.conditional;

import com.example.conditional.ConditionalConfig.SomeBean;

import org.springframework.boot.CommandLineRunner;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private final SomeBean someBean;

	public CLR(@Nullable SomeBean someBean) {
		this.someBean = someBean;
	}

	@Override
	public void run(String... args) throws Exception {
		// someBean is only there if TrickyCondition matched
		System.out.printf("TrickyCondition matched: %b", this.someBean != null);
	}

}
