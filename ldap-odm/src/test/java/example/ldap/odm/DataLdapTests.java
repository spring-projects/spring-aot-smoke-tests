package example.ldap.odm;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;
import org.springframework.ldap.core.LdapTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataLdapTest
class DataLdapTests {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Test
	void saveAndLoad() throws InvalidNameException {
		Person person = getPerson();
		this.ldapTemplate.create(person);

		List<Person> persons = this.ldapTemplate.findAll(Person.class);
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
