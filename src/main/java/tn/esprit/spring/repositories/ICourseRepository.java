package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course, Long> {


    List<Course> findByLevel(int level);
    List<Course> findByTypeCourse(TypeCourse typeCourse);
    @Query("SELECT c FROM Course c WHERE c.level = :level AND c.typeCourse = :typeCourse")
    List<Course> findByLevelAndTypeCourse(@Param("level") int level, @Param("typeCourse") TypeCourse typeCourse);

}
