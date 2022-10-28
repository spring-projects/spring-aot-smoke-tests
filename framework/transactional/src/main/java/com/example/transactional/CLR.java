package com.example.transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class CLR implements CommandLineRunner {

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.printf("Transaction active: %b%n", TransactionSynchronizationManager.isActualTransactionActive());
	}

}
