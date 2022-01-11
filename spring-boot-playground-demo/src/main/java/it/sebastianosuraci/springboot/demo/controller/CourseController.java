package it.sebastianosuraci.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.core.controller.BaseController;
import it.sebastianosuraci.springboot.demo.domain.Course;
import it.sebastianosuraci.springboot.demo.dto.CourseDTO;
import it.sebastianosuraci.springboot.demo.service.CourseService;
import it.sebastianosuraci.springboot.demo.mapper.CourseMapper;


@RestController
@RequestMapping("api/demo/course")
public class CourseController extends BaseController<Course, CourseDTO, Integer> {

    @Autowired
    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        super(courseService, courseMapper);
    } 
    
}
