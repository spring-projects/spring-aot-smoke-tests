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

package example.ldap.odm;

import java.util.Objects;

import javax.naming.Name;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

@Entry(objectClasses = { "inetOrgPerson", "organizationalPerson", "person", "top" })
public final class Person {

	@Id
	@JsonIgnore
	private Name dn;

	@Attribute(name = "cn")
	@DnAttribute(value = "cn")
	private String fullName;

	@Attribute(name = "sn")
	private String lastName;

	@Attribute(name = "description")
	private String description;

	@Transient
	@DnAttribute(value = "c")
	private String country;

	@Transient
	@DnAttribute(value = "ou")
	private String company;

	@Attribute(name = "telephoneNumber")
	private String phone;

	public Name getDn() {
		return this.dn;
	}

	public void setDn(Name dn) {
		this.dn = dn;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Person person = (Person) o;
		return Objects.equals(this.dn, person.dn) && Objects.equals(this.fullName, person.fullName)
				&& Objects.equals(this.lastName, person.lastName)
				&& Objects.equals(this.description, person.description) && Objects.equals(this.country, person.country)
				&& Objects.equals(this.company, person.company) && Objects.equals(this.phone, person.phone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dn, this.fullName, this.lastName, this.description, this.country, this.company,
				this.phone);
	}

	@Override
	public String toString() {
		return "Person{" + "dn=" + this.dn + ", fullName='" + this.fullName + '\'' + ", lastName='" + this.lastName
				+ '\'' + ", description='" + this.description + '\'' + ", country='" + this.country + '\''
				+ ", company='" + this.company + '\'' + ", phone='" + this.phone + '\'' + '}';
	}

}
