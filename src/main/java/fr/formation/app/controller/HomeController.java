package fr.formation.app.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.formation.app.model.Person;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
// @RequestMapping(value = "/hello")
public class HomeController {
	
	// Parametres de requetes
	
	// http://localhost:8080/home
	// http://localhost:8080/home?firstName=Steve
	// http://localhost:8080/home?firstName=Steve&lastName=Rogers
	// http://localhost:8080/home?firstName=Steve&lastName=Rogers&age=100
	// @RequestMapping(value = "/home", method = RequestMethod.GET)
	@GetMapping(value = "/home")
	public ModelAndView Home(
			@RequestParam(value = "firstName", required = false, defaultValue = "Bruce") String firstName,
			@RequestParam(value = "lastName", required = false, defaultValue = "Banner") String lastName,
			@RequestParam(value = "age", required = false, defaultValue = "50") int age,
			ModelAndView mv) {
		mv.addObject("person", new Person(firstName, lastName, age));
		mv.setViewName("home");
		return mv;
	}
	
	// Variables de chemin
	
	// http://localhost:8080/sayHello
	// http://localhost:8080/sayHello/Bob
	@GetMapping(value = { "/sayHello/{prenom}", "/sayHello" })
	public ModelAndView sayHello(
			@PathVariable Optional<String> prenom,
			ModelAndView mv) {
		if (prenom.isPresent()) {
			mv.addObject("prenom", prenom.get());
		} else {
			mv.addObject("prenom", "Gandalf");
		}
		mv.setViewName("home");
		return mv;
	}

}
