package fr.formation.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import fr.formation.app.controller.exception.TechnicalException;
import fr.formation.app.dto.PersonDto;
import fr.formation.app.model.Person;
import fr.formation.app.repository.PersonRepository;
import fr.formation.app.service.IPersonService;

@Service("personService")
public class PersonService implements IPersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
    @Autowired
    private ModelMapper modelMapper;

	@Override
	public List<PersonDto> getAll() {
		return personRepository.findAll().stream()
                .filter(p -> p.getAge() >= 18)
                .map(this::convertToDto)
                .collect(Collectors.toList());
	}

	@Override
	public PersonDto saveOrUpdate(PersonDto o) {
        Person person = convertToEntity(o);
        Person savedPerson = personRepository.save(person);
        return convertToDto(savedPerson);
	}

	@Override
	public Optional<PersonDto> getById(int id) {
		Optional<Person> personOptional = personRepository.findById(id);
        return personOptional.map(this::convertToDto);
	}

	@Override
	public boolean deleteById(int id) {	
		personRepository.deleteById(id);
		return true;
	}

	@Override
	public List<PersonDto> getByFirstNameAndLastName(String firstName, String lastName) {
		 return personRepository.findByFirstNameAndLastName(firstName, lastName).stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	}
	
	private PersonDto convertToDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    private Person convertToEntity(PersonDto personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

	@Override
	public void doSomething() {
		throw new TechnicalException("Technical error");	
	}

}
