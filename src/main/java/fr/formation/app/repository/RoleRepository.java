package fr.formation.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.app.model.Role;
import fr.formation.app.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findByTitle(RoleName roleName);

}
