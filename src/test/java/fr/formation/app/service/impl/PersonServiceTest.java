package fr.formation.app.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import fr.formation.app.controller.exception.TechnicalException;
import fr.formation.app.dto.PersonDto;
import fr.formation.app.model.Person;
import fr.formation.app.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private PersonService personService;

	private Person person1;
	private Person person2;

	@BeforeEach
	void setUp() {
		// Initialiser les données de test
		person1 = new Person();
		person1.setId(1);
		person1.setFirstName("John");
		person1.setLastName("Doe");
		person1.setAge(25);

		person2 = new Person();
		person2.setId(2);
		person2.setFirstName("Jane");
		person2.setLastName("Smith");
		person2.setAge(30);
	}

	@Test
	void testGetAll_Success() {
		// Mocking de la méthode findAll() de PersonRepository
		when(personRepository.findAll()).thenReturn(List.of(person1, person2));
		// Mocking de la méthode map de ModelMapper
		when(modelMapper.map(any(), eq(PersonDto.class))).thenAnswer(invocation -> {
			Person person = invocation.getArgument(0);
			PersonDto personDto = new PersonDto();
			personDto.setId(person.getId());
			personDto.setFirstName(person.getFirstName());
			personDto.setLastName(person.getLastName());
			personDto.setAge(person.getAge());
			return personDto;
		});

		List<PersonDto> result = personService.getAll();

		// Vérifier que la méthode findAll() de PersonRepository a été appelée
		// exactement une fois
		verify(personRepository, times(1)).findAll();
		// Vérifier le nombre d'éléments dans la liste résultante
		assertEquals(2, result.size());
		// Vérifier les détails du premier élément dans la liste résultante
		assertEquals(person1.getId(), result.get(0).getId());
		assertEquals(person1.getFirstName(), result.get(0).getFirstName());
		assertEquals(person1.getLastName(), result.get(0).getLastName());
		assertEquals(person1.getAge(), result.get(0).getAge());
		// Vérifier les détails du deuxième élément dans la liste résultante
		assertEquals(person2.getId(), result.get(1).getId());
		assertEquals(person2.getFirstName(), result.get(1).getFirstName());
		assertEquals(person2.getLastName(), result.get(1).getLastName());
		assertEquals(person2.getAge(), result.get(1).getAge());
	}

	@Test
	void testSaveOrUpdate_Success() {
		// Créer un objet PersonDto de test
		PersonDto personDto = new PersonDto();
		personDto.setId(1);
		personDto.setFirstName("John");
		personDto.setLastName("Doe");
		personDto.setAge(25);

		// Créer un objet Person correspondant
		Person person = new Person();
		person.setId(1);
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAge(25);

		// Mocking du mapping de PersonDto à Person
		when(modelMapper.map(personDto, Person.class)).thenReturn(person);
		// Mocking de la sauvegarde dans le repository
		when(personRepository.save(person)).thenReturn(person);
		// Mocking du mapping de Person à PersonDto
		when(modelMapper.map(person, PersonDto.class)).thenReturn(personDto);

		// Appeler la méthode à tester
		PersonDto result = personService.saveOrUpdate(personDto);

		// Vérifier que la méthode save() de PersonRepository a été appelée exactement
		// une fois avec le bon objet Person
		verify(personRepository, times(1)).save(person);

		// Vérifier que le résultat retourné est le bon objet PersonDto
		assertEquals(personDto, result);
	}

	@Test
	void testGetById_Success() {
		// Créer un objet Person avec un ID de test
		int testId = 1;
		Person testPerson = new Person();
		testPerson.setId(testId);
		testPerson.setFirstName("John");
		testPerson.setLastName("Doe");
		testPerson.setAge(25);

		// Créer un objet PersonDto correspondant
		PersonDto expectedDto = new PersonDto();
		expectedDto.setId(testId);
		expectedDto.setFirstName("John");
		expectedDto.setLastName("Doe");
		expectedDto.setAge(25);

		// Mocking de la méthode findById() de PersonRepository
		when(personRepository.findById(testId)).thenReturn(Optional.of(testPerson));
		// Mocking du mapping de Person à PersonDto
		when(modelMapper.map(testPerson, PersonDto.class)).thenReturn(expectedDto);

		// Appeler la méthode à tester
		Optional<PersonDto> resultOptional = personService.getById(testId);

		// Vérifier que la méthode findById() de PersonRepository a été appelée avec le
		// bon ID
		verify(personRepository, times(1)).findById(testId);
		// Vérifier que le résultat retourné contient le bon objet PersonDto
		assertTrue(resultOptional.isPresent());
		assertEquals(expectedDto, resultOptional.get());
	}

	@Test
	public void testDeleteById_Success() {
		int testId = 1;
		Person testPerson = new Person();
		testPerson.setId(testId);
		testPerson.setFirstName("John");
		testPerson.setLastName("Doe");
		testPerson.setAge(25);
		
		when(personRepository.findById(testId)).thenReturn(Optional.of(testPerson));

		// Mock de la méthode deleteById du repository
		doNothing().when(personRepository).deleteById(anyInt());

		// Appel de la méthode deleteById du service
		boolean result = personService.deleteById(1);

		// Vérification que la méthode deleteById du repository a été appelée avec le
		// bon argument
		verify(personRepository, times(1)).deleteById(1);

		// Vérification que la méthode renvoie true
		assertTrue(result);
	}
	
	@Test
    public void testDeleteById_Failure() {
        // Simuler un Optional vide lorsque findById est appelé avec l'ID spécifié
        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Appeler la méthode deleteById du contrôleur
        assertThrows(TechnicalException.class, () -> personService.deleteById(anyInt()));

        // Vérifier que deleteById n'a pas été appelé car l'Optional était vide
        verify(personRepository, never()).deleteById(anyInt());
    }

	@Test
	public void testGetByFirstNameAndLastName() {
		String firstName = "John";
		String lastName = "Doe";
		
		List<Person> persons = new ArrayList<>();
		persons.add(person1);

		when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(persons);

		List<PersonDto> result = personService.getByFirstNameAndLastName(firstName, lastName);

		assertThat(result.size()).isEqualTo(1);
	}

}
