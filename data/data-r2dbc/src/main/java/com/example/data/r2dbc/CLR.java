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

package com.example.data.r2dbc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	private final ReservationRepository reservationRepository;

	CLR(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	public void run(String... args) {
		this.reservationRepository.findAll().subscribe((reservation) -> {
			System.out.printf("findAll(): %s%n", reservation);
		}, throwable -> throwable.printStackTrace(System.out));

		Reservation reservation = new Reservation(null, "reservation-3");
		this.reservationRepository.save(reservation).subscribe((inserted) -> {
			System.out.printf("save(): %s%n", inserted);
		}, throwable -> throwable.printStackTrace(System.out));
	}

}
