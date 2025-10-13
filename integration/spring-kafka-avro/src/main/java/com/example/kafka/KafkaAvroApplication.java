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

package com.example.kafka;

import java.util.List;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.BatchMessageListener;
import org.springframework.kafka.listener.CommonContainerStoppingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class KafkaAvroApplication {

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.class.path"));
		SpringApplication.run(KafkaAvroApplication.class, args);
	}

	@Bean
	public KafkaAdmin.NewTopics topics() {
		return new KafkaAdmin.NewTopics(TopicBuilder.name("graal1").partitions(1).replicas(1).build(),
				TopicBuilder.name("graal2").partitions(1).replicas(1).build(),
				TopicBuilder.name("graal3").partitions(1).replicas(1).build(),
				TopicBuilder.name("graal4").partitions(1).replicas(1).build(),
				TopicBuilder.name("graal5").partitions(1).replicas(1).build(),
				TopicBuilder.name("graal6").partitions(1).replicas(1).build(),
				TopicBuilder.name("graal7").partitions(1).replicas(1).build(),
				TopicBuilder.name("graal8").partitions(1).replicas(1).build());
	}

	@Bean
	public ConcurrentMessageListenerContainer<Object, Object> container3(NotAComponentMessageListener listener,
			ConsumerFactory<Object, Object> cf, ProducerFactory<Object, SpecificRecord> pf,
			ConcurrentKafkaListenerContainerFactory<Object, Object> factory) {

		((DefaultKafkaConsumerFactory<Object, Object>) cf).setValueDeserializer(new ThingAvroSerde());
		((DefaultKafkaProducerFactory<Object, SpecificRecord>) pf).setValueSerializer(new ThingAvroSerde());
		factory.setCommonErrorHandler(new CommonContainerStoppingErrorHandler());
		ConcurrentMessageListenerContainer<Object, Object> container = factory.createContainer("graal3");
		container.getContainerProperties().setGroupId("graal3");
		container.getContainerProperties().setMessageListener(listener);
		return container;
	}

	@Bean
	public ConcurrentMessageListenerContainer<Object, Object> container6(BML6 listener,
			ConsumerFactory<Object, Object> cf, ProducerFactory<Object, Object> pf,
			ConcurrentKafkaListenerContainerFactory<Object, Object> factory) {

		factory.setCommonErrorHandler(new CommonContainerStoppingErrorHandler());
		ConcurrentMessageListenerContainer<Object, Object> container = factory.createContainer("graal6");
		container.getContainerProperties().setGroupId("graal6");
		container.getContainerProperties().setMessageListener(listener);
		return container;
	}

	@Bean
	public ConcurrentMessageListenerContainer<Object, Object> container7(BML7 listener,
			ConsumerFactory<Object, Object> cf, ProducerFactory<Object, Object> pf,
			ConcurrentKafkaListenerContainerFactory<Object, Object> factory) {

		factory.setCommonErrorHandler(new CommonContainerStoppingErrorHandler());
		ConcurrentMessageListenerContainer<Object, Object> container = factory.createContainer("graal7");
		container.getContainerProperties().setGroupId("graal7");
		container.getContainerProperties().setMessageListener(listener);
		return container;
	}

	@Bean
	NotAComponentMessageListener otherListner() {
		return new NotAComponentMessageListener();
	}

	@Bean
	public ApplicationRunner runner(KafkaTemplate<Object, Object> template) {
		return args -> {
			Thing1 thing1 = Thing1.newBuilder().setStringField("thing1Value").setIntField(42).build();
			template.send("graal1", thing1);
			Thing2 thing2 = Thing2.newBuilder().setStringField("thing2Value").setIntField(42).build();
			template.send("graal2", thing2);
			Thing3 thing3 = Thing3.newBuilder().setStringField("thing3Value").setIntField(42).build();
			template.send("graal3", thing3);
			Thing4 thing4 = Thing4.newBuilder().setStringField("thing4Value").setIntField(42).build();
			template.send("graal4", thing4);
			Thing5 thing5 = Thing5.newBuilder().setStringField("thing5Value").setIntField(42).build();
			template.send("graal5", thing5);
			Thing6 thing6 = Thing6.newBuilder().setStringField("thing5Value").setIntField(42).build();
			template.send("graal6", thing6);
			Thing7 thing7 = Thing7.newBuilder().setStringField("thing5Value").setIntField(42).build();
			template.send("graal7", thing7);
			Thing8 thing8 = Thing8.newBuilder().setStringField("thing5Value").setIntField(42).build();
			template.send("graal8", thing8);
			System.out.println("++++++Sent:" + thing1 + thing2 + thing3 + thing4 + thing5 + thing6 + thing7 + thing8);
		};
	}

}

@Component
class RecordListener {

	@KafkaListener(id = "graal", topics = "graal1")
	void listen1(Thing1 thing1) {
		System.out.println("++++++1:Received " + thing1.getClass().getSimpleName() + ":" + thing1);
	}

	@KafkaListener(id = "graal2", topics = "graal2")
	void listen2(ConsumerRecord<String, Thing2> record) {
		System.out.println("++++++2:Received " + record.value().getClass().getSimpleName() + ":" + record);
	}

	@KafkaListener(id = "graal4", topics = "graal4", batch = "true")
	void listen4(List<Thing4> records) {
		System.out.println("++++++4:Received " + records.get(0).getClass().getSimpleName() + ":" + records.get(0));
	}

	@KafkaListener(id = "graal8", topics = "graal8", batch = "true")
	void listen8(List<ConsumerRecord<String, Thing8>> records) {
		ConsumerRecord<String, Thing8> record = records.iterator().next();
		System.out.println("++++++8:Received " + record.value().getClass().getSimpleName() + ":" + record.value());
	}

}

class NotAComponentMessageListener implements MessageListener<String, Thing3> {

	@Override
	public void onMessage(ConsumerRecord<String, Thing3> record) {
		System.out.println("++++++3:Received " + record.value().getClass().getSimpleName() + ":" + record);
	}

}

@Component
class BML6 implements BatchMessageListener<String, Thing6> {

	@Override
	public void onMessage(List<ConsumerRecord<String, Thing6>> records) {
		System.out
			.println("++++++6:Received " + records.get(0).value().getClass().getSimpleName() + ":" + records.get(0));
	}

}

@Component
class BML7 implements BatchMessageListener<String, Thing7> {

	@Override
	public void onMessage(List<ConsumerRecord<String, Thing7>> data) {
	}

	@Override
	public void onMessage(ConsumerRecords<String, Thing7> records, Acknowledgment acknowledgment,
			Consumer<String, Thing7> consumer) {
		ConsumerRecord<String, Thing7> record = records.iterator().next();
		System.out.println("++++++7:Received " + record.value().getClass().getSimpleName() + ":" + record);
	}

	@Override
	public boolean wantsPollResult() {
		return true;
	}

}

@Component
@KafkaListener(id = "graal5", topics = "graal5")
class Thing5Listener {

	@KafkaHandler
	public void listen(Thing5 thing5) {
		System.out.println("++++++5:Received " + thing5.getClass().getSimpleName() + ":" + thing5);
	}

}
