package com.example.data.jpa;

import java.util.List;

import com.example.data.jpa.model.Address;
import com.example.data.jpa.model.Recipient;
import com.example.data.jpa.model.Voucher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NonTransactionalRunner implements CommandLineRunner {

	private final VoucherRepository voucherRepository;

	private final RecipientRepository recipientRepository;

	public NonTransactionalRunner(VoucherRepository voucherRepository, RecipientRepository recipientRepository) {
		this.voucherRepository = voucherRepository;
		this.recipientRepository = recipientRepository;
	}

	@Override
	public void run(String... args) {
		insertVouchers();
		insertRecipients();
		listVouchers();
		listRecipients();
	}

	private void insertVouchers() {
		this.voucherRepository.save(new Voucher("0810000000", 0));
		this.voucherRepository.save(new Voucher("0810000000", 1));
	}

	private void listVouchers() {
		List<Voucher> vouchers = this.voucherRepository.findByMsisdn("0810000000");
		vouchers.forEach(voucher -> System.out.printf("listVouchers(): voucher = %s%n", voucher));
	}

	private void insertRecipients() {
		this.recipientRepository.save(new Recipient(1L, new Address("Paul Bert", "Lyon", "69003")));
	}

	private void listRecipients() {
		List<Recipient> recipients = this.recipientRepository.findAll();
		recipients.forEach(recipient -> System.out.printf("listRecipients(): recipient = %s%n", recipient));
	}

}
