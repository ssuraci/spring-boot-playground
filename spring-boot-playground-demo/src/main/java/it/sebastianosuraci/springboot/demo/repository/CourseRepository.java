package it.sebastianosuraci.springboot.demo.repository;

import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.Course;


public interface CourseRepository extends BaseRepository<Course, Integer>, CourseRepositoryCustom {


}
