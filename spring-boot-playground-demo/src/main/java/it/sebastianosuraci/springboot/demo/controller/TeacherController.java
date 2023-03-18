package it.sebastianosuraci.springboot.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.core.controller.BaseController;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;
import it.sebastianosuraci.springboot.demo.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import it.sebastianosuraci.springboot.demo.mapper.TeacherMapper;


@RestController
@RequestMapping("api/demo/teacher")
public class TeacherController extends BaseController<Teacher, TeacherDTO, Integer> {

    @Autowired
    public TeacherController(TeacherService TeacherService, TeacherMapper TeacherMapper) {
        super(TeacherService, TeacherMapper);
    }

    @Override
    public List<TeacherDTO> getList(PageModel pageModel, HttpServletRequest request, HttpServletResponse response) {
        return super.getList(pageModel, request, response);
    } 
    
    
}
