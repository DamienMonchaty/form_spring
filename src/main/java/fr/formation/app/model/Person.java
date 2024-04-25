package fr.formation.app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "cars")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "PERSON")
public class Person {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@NonNull
	@Column(name = "FIRSTNAME")
	private String firstName;
	@NonNull
	@Column(name = "LASTNAME")
	private String lastName;
	@NonNull
	@Column(name = "AGE")
	private Integer age;
	@Transient
	private String secret;

	@JsonIgnore
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "person")
	private List<Car> cars = new ArrayList<>();

}
