package fr.formation.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.app.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

	List<Car> findByPersonId(int id);
}
