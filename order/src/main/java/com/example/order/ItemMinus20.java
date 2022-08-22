package com.example.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-20)
class ItemMinus20 implements Item {

	@Override
	public String describe() {
		return "-20";
	}

}
