package it.sebastianosuraci.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sebastianosuraci.springboot.demo.domain.Course;
import it.sebastianosuraci.springboot.core.service.BaseService;
import it.sebastianosuraci.springboot.demo.repository.CourseRepository;

@Service
public class CourseService extends BaseService<Course, Integer> {

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        super(courseRepository);
    } 
    
}
