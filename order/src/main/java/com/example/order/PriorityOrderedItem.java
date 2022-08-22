package com.example.order;

import org.springframework.core.PriorityOrdered;

class PriorityOrderedItem implements Item, PriorityOrdered {

	private final int order;

	public PriorityOrderedItem(int order) {
		this.order = order;
	}

	@Override
	public String describe() {
		return "priority" + this.order;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

}
