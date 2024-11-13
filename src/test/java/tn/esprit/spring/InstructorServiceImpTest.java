package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServiceImplTest {

    @InjectMocks
    InstructorServicesImpl instructorService;

    @Mock
    IInstructorRepository instructorRepository;

    @Mock
    ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor result = instructorService.addInstructor(instructor);
        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        when(instructorRepository.findAll()).thenReturn(instructors);
        List<Instructor> result = instructorService.retrieveAllInstructors();
        assertEquals(instructors, result);
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor result = instructorService.updateInstructor(instructor);
        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        Instructor result = instructorService.retrieveInstructor(1L);
        assertNotNull(result);
        assertEquals(1L, result.getNumInstructor());
    }

    @Test
    void testRemoveInstructor() {
        Long instructorId = 1L;
        instructorService.removeInstructor(instructorId);
        verify(instructorRepository, times(1)).deleteById(instructorId);
    }

    @Test
    void testAssignInstructorToMultipleCourses() {
        Long instructorId = 1L;
        Set<Long> courseIds = new HashSet<>(Arrays.asList(2L, 3L));
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(instructorId);
        Course course1 = new Course();
        Course course2 = new Course();
        course1.setNumCourse(2L);
        course2.setNumCourse(3L);

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course1));
        when(courseRepository.findById(3L)).thenReturn(Optional.of(course2));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorService.assignInstructorToMultipleCourses(instructorId, courseIds);
        assertNotNull(result);
        assertEquals(2, result.getCourses().size());
        verify(instructorRepository, times(1)).findById(instructorId);
        verify(courseRepository, times(1)).findById(2L);
        verify(courseRepository, times(1)).findById(3L);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testFindInstructorsByCourseLevel() {
        int level = 3;
        Course course1 = new Course();
        Course course2 = new Course();
        course1.setLevel(level);
        course2.setLevel(level);

        Instructor instructor1 = new Instructor();
        Instructor instructor2 = new Instructor();

        course1.setInstructor(instructor1);
        course2.setInstructor(instructor2);

        when(courseRepository.findByLevel(level)).thenReturn(Arrays.asList(course1, course2));
        when(instructorRepository.findAllById(any())).thenReturn(Arrays.asList(instructor1, instructor2));

        List<Instructor> result = instructorService.findInstructorsByCourseLevel(level);
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findByLevel(level);
        verify(instructorRepository, times(1)).findAllById(any());
    }
}
