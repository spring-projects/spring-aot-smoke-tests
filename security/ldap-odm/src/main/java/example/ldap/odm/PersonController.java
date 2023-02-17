package example.ldap.odm;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

	private final PersonDao personDao;

	public PersonController(PersonDao personDao) {
		this.personDao = personDao;
	}

	@GetMapping
	public List<Person> findAll() {
		return this.personDao.findAll();
	}

	@GetMapping("/list")
	public List<Person> findAll(@RequestParam(value = "paged", defaultValue = "false") boolean paged,
			@RequestParam(value = "sorted", defaultValue = "false") boolean sorted) {
		return this.personDao.findAll(paged, sorted);
	}

	@GetMapping(params = { "country", "company", "fullName" })
	public Person showPerson(@RequestParam String country, @RequestParam String company,
			@RequestParam String fullName) {
		return this.personDao.findByPrimaryKey(country, company, fullName);
	}

	@PostMapping
	public Person addPerson() throws InvalidNameException {
		Person person = getPerson();
		this.personDao.create(person);
		return person;
	}

	@DeleteMapping(params = { "country", "company", "fullName" })
	public void removePerson(@RequestParam String country, @RequestParam String company,
			@RequestParam String fullName) {
		Person person = this.personDao.findByPrimaryKey(country, company, fullName);
		this.personDao.delete(person);
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
