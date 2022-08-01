package com.example.mail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	private final MailSender mailSender;

	public CLR(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void run(String... args) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("alice@localhost");
		message.setTo("bob@localhost");
		message.setText("Dear Bob, hello world! Alice.");
		this.mailSender.send(message);
		System.out.printf("Sent message %s%n", message);
	}

}
