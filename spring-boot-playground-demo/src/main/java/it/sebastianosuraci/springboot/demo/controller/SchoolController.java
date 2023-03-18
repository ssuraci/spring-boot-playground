package it.sebastianosuraci.springboot.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.core.controller.BaseController;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;
import it.sebastianosuraci.springboot.demo.mapper.SchoolMapper;
import it.sebastianosuraci.springboot.demo.service.SchoolService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api/demo/school")
public class SchoolController extends BaseController<School, SchoolDTO, Integer> {

    @Autowired
    public SchoolController(SchoolService SchoolService, SchoolMapper SchoolMapper) {
        super(SchoolService, SchoolMapper);
    }

    @Override
    public List<SchoolDTO> getList(PageModel pageModel, HttpServletRequest request, HttpServletResponse response) {
        return super.getList(pageModel, request, response);
    } 
    
}
