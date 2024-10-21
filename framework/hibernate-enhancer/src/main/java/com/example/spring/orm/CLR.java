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

package com.example.spring.orm;

import com.example.spring.orm.model.Category;
import com.example.spring.orm.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CLR implements CommandLineRunner {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void run(String... args) {
		var categoryId = insertTestData();
		lazyLoad(categoryId);
	}

	public void lazyLoad(Long id) {
		clearCache();

		Category result = entityManager.find(Category.class, id);
		System.out.println("lazy - category.product: " + result.getProduct().getName());
	}

	public Long insertTestData() {
		Product product = new Product();
		product.setName("lazy-loading-works :)");
		entityManager.persist(product);

		Category category = new Category(product);
		entityManager.persist(category);
		Long categoryId = category.getId();

		entityManager.flush();

		return categoryId;
	}

	void clearCache() {
		Session s = (Session) entityManager.getDelegate();
		SessionFactory sf = s.getSessionFactory();
		sf.getCache().evictEntityData();
		s.clear();
	}

}
