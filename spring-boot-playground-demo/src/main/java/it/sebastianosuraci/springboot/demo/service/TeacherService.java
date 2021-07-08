package it.sebastianosuraci.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.core.service.BaseService;
import it.sebastianosuraci.springboot.demo.repository.TeacherRepository;

@Service
public class TeacherService extends BaseService<Teacher, Integer> {

    @Autowired
    TeacherRepository schoolRepository;

    @Override
    protected BaseRepository<Teacher, Integer> getBaseRepository() {
        return schoolRepository;
    }
    
}
