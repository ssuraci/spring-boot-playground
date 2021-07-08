package it.sebastianosuraci.springboot.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.core.controller.BaseController;
import it.sebastianosuraci.springboot.core.service.BaseService;
import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;
import it.sebastianosuraci.springboot.demo.service.SchoolService;
import it.sebastianosuraci.springboot.demo.mapper.CrudMapper;


@RestController
@RequestMapping("api/demo/school")
public class SchoolController extends BaseController<School, SchoolDTO, Integer> {

    @Autowired
    SchoolService schoolService;
    
    @Autowired
    CrudMapper crudMapper;

    @Override
    protected BaseService<School, Integer> getService() {
        return schoolService;
    }
    @Override
    protected SchoolDTO entityToDto(School entity) {
        return crudMapper.schoolToSchoolDto(entity);
    }

    @Override
    protected List<SchoolDTO> entityToDtoList(List<School> entity) {
        return crudMapper.schoolToSchoolDtos(entity);
    }

    @Override
    protected School dtoToEntity(SchoolDTO dto) {
       return crudMapper.schoolDtoSchool(dto);
    }
    
}
