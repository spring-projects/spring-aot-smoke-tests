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
