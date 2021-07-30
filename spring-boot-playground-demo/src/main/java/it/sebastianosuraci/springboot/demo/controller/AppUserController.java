package it.sebastianosuraci.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sebastianosuraci.springboot.demo.domain.AppUser;
import it.sebastianosuraci.springboot.demo.dto.AppUserDTO;
import it.sebastianosuraci.springboot.demo.mapper.AppUserMapper;
import it.sebastianosuraci.springboot.demo.service.AppUserService;
import it.sebastianosuraci.springboot.core.controller.BaseController;

@RestController
@RequestMapping("api/demo/app-user")
public class AppUserController extends BaseController<AppUser, AppUserDTO, Integer> {

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserMapper appUserMapper) {
        super(appUserService, appUserMapper);
    } 
    
}
