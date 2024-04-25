package fr.formation.app.service;

import java.util.List;

import fr.formation.app.dto.PersonDto;

public interface IPersonService extends IService<PersonDto> {

	List<PersonDto> getByFirstNameAndLastName(String firstName, String lastName);
}
