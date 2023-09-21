package com.example.data.jpa;

import java.util.List;

import com.example.data.jpa.model.Voucher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NonTransactionalRunner implements CommandLineRunner {

	private final VoucherRepository voucherRepository;

	public NonTransactionalRunner(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	@Override
	public void run(String... args) {
		insertVouchers();
		listVouchers();
	}

	private void insertVouchers() {
		this.voucherRepository.save(new Voucher("0810000000", 0));
		this.voucherRepository.save(new Voucher("0810000000", 1));
	}

	private void listVouchers() {
		List<Voucher> vouchers = this.voucherRepository.findByMsisdn("0810000000");
		vouchers.forEach(voucher -> System.out.printf("listVouchers(): voucher = %s%n", voucher));
	}

}
