package fr.formation.app.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
	
	private Integer id;
	@NotEmpty()
	@Size(min = 2, message = "Nom doit contenir minimum 2 caractères")
	private String firstName;
	@NotEmpty()
	@Size(min = 2, message = "Prenom doit contenir minimum 2 caractères")
	private String lastName;
	@Digits(integer = 3, fraction = 0, message = "age doit contenir au maximum 3 entiers")
	private Integer age;
}
