package it.sebastianosuraci.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.core.controller.BaseController;
import it.sebastianosuraci.springboot.demo.domain.Subject;
import it.sebastianosuraci.springboot.demo.dto.SubjectDTO;
import it.sebastianosuraci.springboot.demo.mapper.SubjectMapper;
import it.sebastianosuraci.springboot.demo.service.SubjectService;


@RestController
@RequestMapping("api/demo/subject")
public class SubjectController extends BaseController<Subject, SubjectDTO, Integer> {

    @Autowired
    public SubjectController(SubjectService SubjectService, SubjectMapper SubjectMapper) {
        super(SubjectService, SubjectMapper);
    } 
    
}
