package fr.formation.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fr.formation.app.model.Person;

@DataJpaTest
public class PersonRepositoryTest {

	@Autowired
	private PersonRepository personRepository;
	
	private Person person1;
	private Person person2;
	
	@BeforeEach
	void setup(){
		person1 = new Person("admin", "admin", 10);
		person2 = new Person("admin2", "admin2", 20);
	}

	@Test
	void testFindAll() {
		personRepository.save(person1);
		personRepository.save(person2);

		List<Person> personnesFromDb = personRepository.findAll();
		assertThat(personnesFromDb).contains(person1, person2);
	}

	@Test
	void testSave() {
		personRepository.save(person1);
		assertThat(person1.getId()).isNotNull();
	}

	@Test
	void testFindById() {
		personRepository.save(person1);

		Optional<Person> foundPersonOptional = personRepository.findById(person1.getId());
		assertThat(foundPersonOptional).isPresent();

		Person foundPerson = foundPersonOptional.get();
		assertThat(foundPerson).isEqualTo(person1);
	}

	@Test
	void testDelete() {
		personRepository.save(person1);
		
		personRepository.delete(person1);

		Optional<Person> deletedPersonOptional = personRepository.findById(person1.getId());
		assertThat(deletedPersonOptional).isNotPresent();
	}
	
	@AfterEach
	void leave(){
		person1 = null;
		person2 = null;
	}
}
