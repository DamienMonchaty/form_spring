package fr.formation.app.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.app.model.Calcul;

@RestController
@RequestMapping("/api/v1/calculate")
public class CalculRestController {

	@PostMapping
	public ResponseEntity<Double> calculate(@RequestBody Calcul calcul) {
		if (isValidNumber(calcul.getA()) && isValidNumber(calcul.getB())) {
			double a = Double.parseDouble(calcul.getA());
			double b = Double.parseDouble(calcul.getB());

			// Check if 'b' is not zero to avoid division by zero
			if (b != 0) {
				double result = Math.sqrt(a) / b;
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				// Handle division by zero scenario
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			// Handle invalid number inputs
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	private boolean isValidNumber(String value) {
		// Check if the value is a valid number
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
