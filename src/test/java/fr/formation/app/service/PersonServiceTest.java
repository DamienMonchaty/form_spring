package fr.formation.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import fr.formation.app.dto.PersonDto;
import fr.formation.app.model.Person;
import fr.formation.app.repository.PersonRepository;
import fr.formation.app.service.impl.PersonService;

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
    void testGetAll() {
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

        // Vérifier que la méthode findAll() de PersonRepository a été appelée exactement une fois
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

}
