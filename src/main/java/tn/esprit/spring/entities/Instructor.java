package tn.esprit.spring.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
public class Instructor implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long numInstructor;

	String firstName;
	String lastName;
	LocalDate dateOfHire;

	// Use Set<Course> to avoid duplicate fields
	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
	Set<Course> courses;
}
