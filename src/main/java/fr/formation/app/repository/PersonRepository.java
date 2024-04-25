package fr.formation.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.formation.app.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	
	// findBy : SELECT * FROM Person WHERE ...
	List<Person> findByFirstNameAndLastName(String firstName, String lastName);
	
	// HQL : on requete l'entite et non la table
	@Query("select p from Person p where p.firstName = ?1 and p.lastName = ?2")
	List<Person> findByPrenomAndNom(String firstName, String lastName);
}
