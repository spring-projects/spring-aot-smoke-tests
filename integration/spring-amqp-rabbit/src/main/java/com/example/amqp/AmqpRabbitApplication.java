package com.example.amqp;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
public class AmqpRabbitApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AmqpRabbitApplication.class, args).close();
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Autowired
	CachingConnectionFactory ccf;

	@RabbitListener(id = "graal1", queues = "graal1")
	@SendTo
	public String upperCaseIt(String in) {
		try {
			String two = sendWithConfirms();
			return in.toUpperCase() + two;
		}
		catch (Exception e) {
			throw new AmqpRejectAndDontRequeueException("fail");
		}
	}

	@RabbitListener(id = "graal2", queues = "graal2")
	@SendTo
	public String lowerCaseIt(String in) {
		return in.toLowerCase();
	}

	private String sendWithConfirms() throws Exception {
		CachingConnectionFactory ccf = new CachingConnectionFactory(this.ccf.getRabbitConnectionFactory());
		ccf.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
		RabbitTemplate template = new RabbitTemplate(ccf);
		CorrelationData data = new CorrelationData();
		String result = (String) template.convertSendAndReceive("", "graal2", "TWO", data);
		data.getFuture().get(10, TimeUnit.SECONDS);
		ccf.resetConnection();
		return result;
	}

	@Bean
	public Queue queue1() {
		return new Queue("graal1");
	}

	@Bean
	public Queue queue2() {
		return new Queue("graal2");
	}

	@Bean
	public ApplicationRunner runner(RabbitTemplate template) {
		return args -> {
			System.out.println("++++++ Received: " + template.convertSendAndReceive("", "graal1", "one"));
		};
	}

}
