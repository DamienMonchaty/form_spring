package fr.formation.app.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import fr.formation.app.dto.PersonDto;
import fr.formation.app.model.Person;
import fr.formation.app.service.IPersonService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@SessionAttributes("person") // Ajoutera cette variable en session
public class PersonController {

	@Autowired
	private IPersonService personService;

	@GetMapping("/showAll")
	public ModelAndView showAll(ModelAndView mv) {
		mv.addObject("persons", personService.getAll());
		mv.setViewName("persons");
		return mv;
	}

	// Exercice :
	// Ecrire les requetes qui afficheront une liste de personnes en utlisant les
	// methodes
	// les methodes custom de PersonRepository

	// http://localhost:8080/showMany?firstName=PRENOM3&lastName=NOM3
	@GetMapping(value = "/showMany")
	public ModelAndView showMany(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName, ModelAndView mv) {
		mv.addObject("persons", personService.getByFirstNameAndLastName(firstName, lastName));
		mv.setViewName("persons");
		return mv;
	}

	@GetMapping("/login")
	public String personForm(Model model) {
		model.addAttribute("person", new PersonDto());
		return "personForm";
	}

	@PostMapping("/login")
	public String personForm(
			@ModelAttribute("person") @Valid PersonDto personDto, 
			BindingResult bindingResult,
			WebRequest request) {

		if (bindingResult.hasErrors()) {
			return "personForm";
		}
		
		personService.saveOrUpdate(personDto);

		request.setAttribute("connected", true, WebRequest.SCOPE_SESSION);

		return "redirect:/showAll";
	}

	@GetMapping("/logout")
	public String leave(WebRequest request) {
		request.setAttribute("connected", false, WebRequest.SCOPE_SESSION);
		request.removeAttribute("person", WebRequest.SCOPE_SESSION);
		return "redirect:/login";
	}

	@GetMapping("/deletePerson/{id}")
	public String deletePerson(@PathVariable("id") int id) {
		personService.deleteById(id);
		return "redirect:/showAll";
	}

	@GetMapping("/editPerson/{id}")
	public String editPerson(
			@PathVariable("id") int id, 
			Model model) {
		PersonDto pToEdit = personService.getById(id).get();
		model.addAttribute("person", pToEdit);
		return "editPerson";
	}

	@PostMapping("/editPerson")
	public String editPerson(
			@ModelAttribute("person") @Valid PersonDto personDto,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "personForm";
		}	
		personService.saveOrUpdate(personDto);
		return "redirect:/showAll";
	}

}
