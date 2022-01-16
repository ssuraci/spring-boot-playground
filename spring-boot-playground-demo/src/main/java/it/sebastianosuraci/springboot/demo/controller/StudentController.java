package it.sebastianosuraci.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.core.controller.BaseController;
import it.sebastianosuraci.springboot.demo.domain.Student;
import it.sebastianosuraci.springboot.demo.dto.StudentDTO;
import it.sebastianosuraci.springboot.demo.service.StudentService;
import it.sebastianosuraci.springboot.demo.mapper.StudentMapper;


@RestController
@RequestMapping("api/demo/student")
public class StudentController extends BaseController<Student, StudentDTO, Integer> {

    @Autowired
    public StudentController(StudentService StudentService, StudentMapper StudentMapper) {
        super(StudentService, StudentMapper);
    } 
    
}
