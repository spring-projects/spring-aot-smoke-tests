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
