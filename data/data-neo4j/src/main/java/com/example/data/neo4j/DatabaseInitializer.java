/*
 * Copyright 2023 the original author or authors.
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
package com.example.data.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements SmartLifecycle {

	private final Driver driver;

	private boolean running;

	public DatabaseInitializer(Driver driver) {
		this.driver = driver;
	}

	@Override
	public void start() {
		if (this.running) {
			return;
		}

		try (Session session = this.driver.session(); Transaction tx = session.beginTransaction()) {
			tx.run("MATCH (n:ParentLabel:ChildLabel) DETACH DELETE n");
			tx.run("CREATE (:ParentLabel:ChildLabel {id: 1, name: 'BothLabelsMustBeManagedTypes'})");
			tx.run("CREATE (:Movie {id: 1, version: 0, title: 'A movie', updatedBy: 'A person', updatedAt: localdatetime()}) <-[:ACTED_IN]-(:Person {name: 'An Actor'})");
			tx.commit();
			this.running = true;
		}
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

}
