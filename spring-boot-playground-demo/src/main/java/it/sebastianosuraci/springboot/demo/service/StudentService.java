package it.sebastianosuraci.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sebastianosuraci.springboot.demo.domain.Student;
import it.sebastianosuraci.springboot.core.service.BaseService;
import it.sebastianosuraci.springboot.demo.repository.StudentRepository;

@Service
public class StudentService extends BaseService<Student, Integer> {

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        super(studentRepository);
    } 
    
}
