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

import javax.naming.Name;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.LdapName;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.ldap.control.ControlExchangeDirContextProcessor;
import org.springframework.ldap.control.PagedResultsControlExchangeDirContextProcessor;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.control.SortControlDirContextProcessor;
import org.springframework.ldap.control.SortControlExchange;
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
			processor.addDirContextProcessor(new PagedResultsControlExchangeDirContextProcessor(Integer.MAX_VALUE));
		}
		if (sorted) {
			processor.addDirContextProcessor(new ControlExchangeDirContextProcessor<>(new SortControlExchange("sn")));
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
