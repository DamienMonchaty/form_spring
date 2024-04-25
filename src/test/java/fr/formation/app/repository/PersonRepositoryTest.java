package fr.formation.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fr.formation.app.model.Person;

@DataJpaTest
public class PersonRepositoryTest {

	/** The personne repository. */
	@Autowired
	private PersonRepository personRepository;

	/**
	 * Test get personnes.
	 */
	@Test
	public void testfindAll() {
		Person person1 = new Person("admin", "admin", 10);
		Person person2 = new Person("admin2", "admin2", 20);

		personRepository.save(person1);
		personRepository.save(person2);

		List<Person> personnesFromDb = personRepository.findAll();
        assertThat(personnesFromDb).contains(person1, person2);
	}

	

}
