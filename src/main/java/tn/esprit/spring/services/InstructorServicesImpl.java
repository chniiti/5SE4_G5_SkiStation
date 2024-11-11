package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices{

    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;

    @Override
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        return instructorRepository.findById(numInstructor).orElse(null);
    }

    @Override
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        Course course = courseRepository.findById(numCourse).orElse(null);
        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        instructor.setCourses(courseSet);
        return instructorRepository.save(instructor);
    }

    public void removeInstructor(Long numInstructor) {
        instructorRepository.deleteById(numInstructor);
    }

    public Instructor assignInstructorToMultipleCourses(Long numInstructor, Set<Long> courseIds) {
        Instructor instructor = instructorRepository.findById(numInstructor).orElse(null);
        if (instructor != null) {
            Set<Course> courses = new HashSet<>();
            for (Long courseId : courseIds) {
                courseRepository.findById(courseId).ifPresent(courses::add);
            }
            instructor.setCourses(courses);
            return instructorRepository.save(instructor);
        }
        return null;
    }

    public List<Instructor> findInstructorsByCourseLevel(int level) {
        List<Course> courses = courseRepository.findByLevel(level);
        Set<Long> instructorIds = new HashSet<>();
        for (Course course : courses) {
            if (course.getInstructor() != null) {
                instructorIds.add(course.getInstructor().getNumInstructor());
            }
        }
        return instructorRepository.findAllById(instructorIds);
    }

}
