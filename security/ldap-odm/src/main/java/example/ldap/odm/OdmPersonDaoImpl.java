package example.ldap.odm;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.LdapName;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.control.SortControlDirContextProcessor;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AggregateDirContextProcessor;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Repository;

@Repository
@RegisterReflectionForBinding(Person.class)
public class OdmPersonDaoImpl implements PersonDao {

	private final LdapTemplate ldapTemplate;

	private final ContextMapper<Person> odm;

	private final SearchControls controls;

	public OdmPersonDaoImpl(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
		this.odm = (Object ctx) -> this.ldapTemplate.getObjectDirectoryMapper()
			.mapFromLdapDataEntry((DirContextOperations) ctx, Person.class);
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningObjFlag(true);
		this.controls = controls;
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
	public List<Person> findAll(boolean paged, boolean sorted) {
		Name base = LdapUtils.emptyLdapName();
		Filter filter = this.ldapTemplate.getObjectDirectoryMapper().filterFor(Person.class, null);
		AggregateDirContextProcessor processor = new AggregateDirContextProcessor();
		if (paged) {
			processor.addDirContextProcessor(new PagedResultsDirContextProcessor(Integer.MAX_VALUE));
		}
		if (sorted) {
			processor.addDirContextProcessor(new SortControlDirContextProcessor("sn"));
		}
		return this.ldapTemplate.search(base, filter.encode(), this.controls, this.odm, processor);
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
		return LdapNameBuilder.newInstance()
			.add("dc", "io")
			.add("dc", "spring")
			.add("c", country)
			.add("ou", company)
			.add("cn", fullname)
			.build();
	}

}
