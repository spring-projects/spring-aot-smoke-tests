package com.example.integration;

import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.aot.smoketest.thirdpartyhints.NettyRuntimeHints;
import org.springframework.aot.smoketest.thirdpartyhints.ReactorNettyHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.integration.config.EnableMessageHistory;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.config.IntegrationConverter;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.integration.http.config.EnableIntegrationGraphController;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.H2ChannelMessageStoreQueryProvider;
import org.springframework.integration.redis.store.RedisChannelMessageStore;
import org.springframework.integration.support.json.JacksonJsonUtils;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.messaging.MessageHandler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

@SpringBootApplication(proxyBeanMethods = false)
@EnableMessageHistory("dateChannel")
@EnableIntegrationManagement
@EnableIntegrationGraphController("/integration-graph")
@ImportRuntimeHints({ NettyRuntimeHints.class, ReactorNettyHints.class, InterimRuntimeHints.class })
public class IntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationApplication.class, args);
	}

	@Bean
	MeterRegistry simpleMeterRegistry() {
		return new SimpleMeterRegistry();
	}

	@Bean
	JdbcChannelMessageStore jdbcChannelMessageStore(DataSource dataSource) {
		JdbcChannelMessageStore jdbcChannelMessageStore = new JdbcChannelMessageStore(dataSource);
		jdbcChannelMessageStore.setChannelMessageStoreQueryProvider(new H2ChannelMessageStoreQueryProvider());
		return jdbcChannelMessageStore;
	}

	@Bean
	RedisChannelMessageStore redisChannelMessageStore(RedisConnectionFactory connectionFactory) {
		RedisChannelMessageStore redisChannelMessageStore = new RedisChannelMessageStore(connectionFactory);
		redisChannelMessageStore
				.setValueSerializer(new GenericJackson2JsonRedisSerializer(JacksonJsonUtils.messagingAwareMapper()));
		return redisChannelMessageStore;
	}

	@Bean
	IntegrationFlow printFormattedSecondsFlow(JdbcChannelMessageStore jdbcChannelMessageStore,
			RedisChannelMessageStore redisChannelMessageStore) {

		return IntegrationFlow
				.fromSupplier(Date::new, e -> e.id("dateSourceEndpoint").poller(p -> p.fixedDelay(1000, 1000)))
				.channel(c -> c.queue("dateChannel", jdbcChannelMessageStore, "dateChannelGroup"))
				.gateway(subflow -> subflow.convert(Integer.class, e -> e.advice(new RequestHandlerRetryAdvice())))
				.channel(c -> c.queue(redisChannelMessageStore, "secondsChannelGroup"))
				.handle(m -> System.out.println("Current seconds: " + m.getPayload())).get();
	}

	@Bean
	@GlobalChannelInterceptor(patterns = "dateChannel")
	WireTap loggingWireTap() {
		return new WireTap("loggingChannel");
	}

	@Bean
	@ServiceActivator(inputChannel = "loggingChannel")
	MessageHandler loggingHandler() {
		LoggingHandler loggingHandler = new LoggingHandler(LoggingHandler.Level.TRACE);
		loggingHandler.setLoggerName("tracing.data");
		return loggingHandler;
	}

	@Bean
	@IntegrationConverter
	Converter<Date, Integer> currentSeconds() {
		return new Converter<Date, Integer>() { // Not lambda for generic info presence

			@Override
			public Integer convert(Date date) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				return calendar.get(Calendar.SECOND);
			}

		};
	}

	@Bean
	public IntegrationFlow controlBus() {
		return IntegrationFlowDefinition::controlBus;
	}

	@Bean
	public IntegrationFlow controlBusControllerFlow(ControlBusGateway controlBusGateway) {
		return IntegrationFlow
				.from(WebFlux.inboundChannelAdapter("/control-bus/{endpointId}")
						.payloadExpression("#pathVariables.endpointId")
						.requestMapping(mapping -> mapping.methods(HttpMethod.GET)))
				.wireTap(subflow -> subflow.handle(m -> System.out.println("Starting endpoint: " + m.getPayload())))
				.handle(controlBusGateway, "startEndpoint").get();
	}

	@MessagingGateway(defaultRequestChannel = "controlBus.input")
	public interface ControlBusGateway {

		@Gateway(payloadExpression = "'@' + args[0] + '.start()'")
		void startEndpoint(String id);

	}

}
