package it.sebastianosuraci.springboot.demo.repository;

import java.util.List;

import it.sebastianosuraci.springboot.demo.domain.Course;

public interface CourseRepositoryCustom {
    List<Course> qrFindByTitle(String title);
}
