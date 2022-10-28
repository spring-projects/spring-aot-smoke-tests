package example.ldap.odm;

import java.util.List;

import javax.naming.ldap.LdapName;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;

@Repository
@RegisterReflectionForBinding(Person.class)
public class OdmPersonDaoImpl implements PersonDao {

	private final LdapTemplate ldapTemplate;

	public OdmPersonDaoImpl(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	@Override
	public void create(Person person) {
		this.ldapTemplate.create(person);
	}

	@Override
	public void delete(Person person) {
		this.ldapTemplate.delete(person);
	}

	@Override
	public List<Person> findAll() {
		return this.ldapTemplate.findAll(Person.class);
	}

	@Override
	public Person findByPrimaryKey(String country, String company, String fullName) {
		LdapName dn = buildDn(country, company, fullName);
		return this.ldapTemplate.findByDn(dn, Person.class);
	}

	private LdapName buildDn(String country, String company, String fullname) {
		return LdapNameBuilder.newInstance().add("dc", "io").add("dc", "spring").add("c", country).add("ou", company)
				.add("cn", fullname).build();
	}

}
