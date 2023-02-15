package example.ldap.odm;

import java.util.List;

public interface PersonDao {

	void create(Person person);

	void delete(Person person);

	List<Person> findAll();

	List<Person> findAll(boolean paged, boolean sorted);

	Person findByPrimaryKey(String country, String company, String fullname);

}
