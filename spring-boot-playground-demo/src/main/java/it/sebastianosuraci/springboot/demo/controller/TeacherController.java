package it.sebastianosuraci.springboot.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.core.controller.BaseController;
import it.sebastianosuraci.springboot.core.service.BaseService;
import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;
import it.sebastianosuraci.springboot.demo.service.TeacherService;
import it.sebastianosuraci.springboot.demo.mapper.CrudMapper;


@RestController
@RequestMapping("api/demo/teacher")
public class TeacherController extends BaseController<Teacher, TeacherDTO, Integer> {

    @Autowired
    TeacherService teacherService;
    
    @Autowired
    CrudMapper crudMapper;

    @Override
    protected BaseService<Teacher, Integer> getService() {
        return teacherService;
    }

    @Override
    protected TeacherDTO entityToDto(Teacher entity) {
        return crudMapper.teacherToTeacherDto(entity);
    }

    @Override
    protected List<TeacherDTO> entityToDtoList(List<Teacher> entity) {
        return crudMapper.teacherToTeacherDtos(entity);
    }

    @Override
    protected Teacher dtoToEntity(TeacherDTO dto) {
       return crudMapper.teacherDtoTeacher(dto);
    }
    
}
