package fr.formation.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@ToString(exclude = "person")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "CAR")
public class Car {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@NonNull
	@Column(name = "TYPE")
	private String type;
	@NonNull
	@Column(name = "BRAND")
	private String brand;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PERSON_ID", referencedColumnName = "id")
	private Person person;

}
