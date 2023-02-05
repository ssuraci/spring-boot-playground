package it.sebastianosuraci.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sebastianosuraci.springboot.core.service.BaseService;
import it.sebastianosuraci.springboot.demo.domain.Subject;
import it.sebastianosuraci.springboot.demo.repository.SubjectRepository;

@Service
public class SubjectService extends BaseService<Subject, Integer>  {

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        super(subjectRepository);
    } 
}
