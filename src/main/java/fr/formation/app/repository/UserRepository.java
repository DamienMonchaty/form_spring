package fr.formation.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.app.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String username);
}
