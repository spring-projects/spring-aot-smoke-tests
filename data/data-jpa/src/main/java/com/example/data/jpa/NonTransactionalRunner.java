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
