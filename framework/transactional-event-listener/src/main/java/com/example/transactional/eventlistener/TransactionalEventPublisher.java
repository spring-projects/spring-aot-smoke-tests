/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.transactional.eventlistener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public TransactionalEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Transactional
	public void transactionSuccessful() {
		System.out.println("TransactionalEventPublisher: Publishing TransactionalEvent (transaction successful)");
		this.applicationEventPublisher.publishEvent(new TransactionalEvent("TX successful"));
	}

	@Transactional
	public void transactionFailed() {
		System.out.println("TransactionalEventPublisher: Publishing TransactionalEvent (transaction failed)");
		this.applicationEventPublisher.publishEvent(new TransactionalEvent("TX failed"));
		// This causes the transaction to abort
		throw new RuntimeException("Boom");
	}

}
