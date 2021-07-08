package it.sebastianosuraci.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.core.service.BaseService;
import it.sebastianosuraci.springboot.demo.repository.SchoolRepository;

@Service
public class SchoolService extends BaseService<School, Integer> {

    @Autowired
    SchoolRepository schoolRepository;

    @Override
    protected BaseRepository<School, Integer> getBaseRepository() {
        return schoolRepository;
    }
    
}
