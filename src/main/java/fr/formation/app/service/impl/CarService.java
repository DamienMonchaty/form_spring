package fr.formation.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.app.model.Car;
import fr.formation.app.repository.CarRepository;
import fr.formation.app.service.ICarService;

@Service("carService")
public class CarService implements ICarService {

	@Autowired
	private CarRepository carRepository;
	
	@Override
	public List<Car> getCarsByPersonId(int id) {
		return carRepository.findByPersonId(id);
	}

}
