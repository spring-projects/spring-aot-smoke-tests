package com.example.data.jpa.model;

import java.util.Objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Recipient {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Address address;

	public Recipient() {
	}

	public Recipient(Long id, Address address) {
		this.id = id;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Recipient recipient = (Recipient) o;
		return Objects.equals(id, recipient.id) && Objects.equals(address, recipient.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, address);
	}

	@Override
	public String toString() {
		return "Recipient{" + "id=" + id + ", address=" + address + '}';
	}

}
