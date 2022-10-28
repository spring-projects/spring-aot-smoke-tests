package com.example.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
class Item10 implements Item {

	@Override
	public String describe() {
		return "10";
	}

}
