package fr.formation.app.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.formation.app.model.Person;
import fr.formation.app.model.Role;
import fr.formation.app.model.RoleName;
import fr.formation.app.repository.PersonRepository;
import fr.formation.app.repository.RoleRepository;

@Component
public class RunnerConfig implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		personRepository.saveAll(Arrays.asList(
				new Person("PRENOM1", "NOM1", 50), 
				new Person("PRENOM2", "NOM2", 50),
				new Person("PRENOM3", "NOM3", 50)
				)
			);
		
		roleRepository.saveAll(Arrays.asList(
				new Role(RoleName.ROLE_USER), 
				new Role(RoleName.ROLE_ADMIN)
				)
			);
	}

}
