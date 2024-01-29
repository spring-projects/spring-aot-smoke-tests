package com.example.data.jpa.model;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.EmbeddableInstantiator;
import org.hibernate.metamodel.spi.ValueAccess;

public class AddressInstantiator implements EmbeddableInstantiator {

	@Override
	public Object instantiate(ValueAccess valueAccess, SessionFactoryImplementor sessionFactory) {
		final Object[] values = valueAccess.getValues();
		final String city = (String) values[0];
		final String postalCode = (String) values[1];
		final String street = (String) values[2];
		return new Address(street, city, postalCode);
	}

	@Override
	public boolean isInstance(Object object, SessionFactoryImplementor sessionFactory) {
		return object instanceof Address;
	}

	@Override
	public boolean isSameClass(Object object, SessionFactoryImplementor sessionFactory) {
		return object.getClass().equals(Address.class);
	}

}
