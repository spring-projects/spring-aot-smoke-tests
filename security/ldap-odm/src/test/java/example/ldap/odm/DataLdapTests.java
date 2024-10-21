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

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataLdapTest
@Import(OdmPersonDaoImpl.class)
class DataLdapTests {

	@Autowired
	private PersonDao personDao;

	@Test
	void saveAndLoad() throws InvalidNameException {
		Person person = getPerson();
		this.personDao.create(person);

		List<Person> persons = this.personDao.findAll();
		this.personDao.delete(person);
		assertThat(persons).contains(person);
	}

	@Test
	void saveAndLoadPagedAndSorted() throws InvalidNameException {
		Person person = getPerson();
		this.personDao.create(person);

		List<Person> persons = this.personDao.findAll(true, true);
		this.personDao.delete(person);
		assertThat(persons).contains(person);
	}

	private Person getPerson() throws InvalidNameException {
		Person person = new Person();
		person.setDn(new LdapName("cn=John Doe,ou=company1,c=Sweden,dc=spring,dc=io"));
		person.setFullName("John Doe");
		person.setLastName("Doe");
		person.setCompany("company1");
		person.setCountry("Sweden");
		person.setDescription("Test user");
		person.setPhone("+46 555-123123");
		return person;
	}

}
