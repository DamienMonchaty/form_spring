package fr.formation.app.service;

import java.util.List;

import fr.formation.app.model.Car;

public interface ICarService {

	List<Car> getCarsByPersonId(int id);
}
