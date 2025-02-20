package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Long numCourse;

	int level;

	@Enumerated(EnumType.STRING)
	TypeCourse typeCourse;

	@Enumerated(EnumType.STRING)
	Support support;

	Float price;
	int timeSlot;

	@JsonIgnore
	@OneToMany(mappedBy= "course")
	Set<Registration> registrations;

	@ManyToOne
	@JoinColumn(name = "instructor_id")  // Optional, improves readability in the database
	Instructor instructor;
}
