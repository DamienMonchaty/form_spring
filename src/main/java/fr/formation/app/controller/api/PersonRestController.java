package fr.formation.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.formation.app.dto.PersonDto;
import fr.formation.app.model.Car;
import fr.formation.app.service.ICarService;
import fr.formation.app.service.IPersonService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/persons")
@CrossOrigin
public class PersonRestController {

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private ICarService carService;

	// GET http://localhost:8080/api/v1/persons
	@GetMapping
	public ResponseEntity<List<PersonDto>> getPersons() {
		return new ResponseEntity<>(personService.getAll(), HttpStatus.OK);
	}

	// GET http://localhost:8080/api/v1/persons/1
	@GetMapping("/{id}")
	public ResponseEntity<PersonDto> getPerson(@PathVariable("id") int id) {
		PersonDto person = personService.getById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + id));
		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	// POST http://localhost:8080/api/v1/persons
	@PostMapping
	public ResponseEntity<PersonDto> addPerson(@RequestBody @Valid PersonDto personDto) {
		return new ResponseEntity<>(personService.saveOrUpdate(personDto), HttpStatus.CREATED);
	}

	// PUT http://localhost:8080/api/v1/persons/1
	@PutMapping(value = "/{id}")
	public ResponseEntity<PersonDto> editPerson(@RequestBody @Valid PersonDto personDto,
			@PathVariable("id") int id) {
		personService.getById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + id));
		personDto.setId(id);
		return new ResponseEntity<>(personService.saveOrUpdate(personDto), HttpStatus.OK);
	}

	// DELETE http://localhost:8080/api/v1/persons/1
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable("id") int id) {
		PersonDto personToDelete = personService.getById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + id));
		personService.deleteById(personToDelete.getId());
		return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
	}
	
	// GET http://localhost:8080/api/v1/persons/1/cars
	@GetMapping("/{id}/cars")
	public ResponseEntity<List<Car>> getCarsByPersonId(@PathVariable("id") int id) {
		PersonDto person = personService.getById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + id));
		List<Car> cars = carService.getCarsByPersonId(person.getId());
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}

}
