package it.sebastianosuraci.springboot.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.demo.domain.AppUser;
import it.sebastianosuraci.springboot.demo.dto.AppUserDTO;
import it.sebastianosuraci.springboot.demo.mapper.CrudMapper;
import it.sebastianosuraci.springboot.demo.service.AppUserService;
import it.sebastianosuraci.springboot.core.controller.BaseController;
import it.sebastianosuraci.springboot.core.service.BaseService;

@RestController
@RequestMapping("api/demo/app-user")
public class AppUserController extends BaseController<AppUser, AppUserDTO, Integer> {

    @Autowired
    AppUserService appUserService;

    @Autowired
    CrudMapper crudMapper;

    @Override
    protected BaseService<AppUser, Integer> getService() {
        return appUserService;
    }

    @Override
    protected AppUserDTO entityToDto(AppUser entity) {
        return crudMapper.appUserToAppUserDto(entity);
    }

    @Override
    protected List<AppUserDTO> entityToDtoList(List<AppUser> entity) {
        return crudMapper.appUserToAppUserDtos(entity);
    }

    @Override
    protected AppUser dtoToEntity(AppUserDTO dto) {
       return crudMapper.appUserDtoToUser(dto);
    }
    
}
