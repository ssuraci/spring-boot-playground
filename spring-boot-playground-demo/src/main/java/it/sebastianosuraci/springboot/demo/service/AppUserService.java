package it.sebastianosuraci.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sebastianosuraci.springboot.demo.domain.AppUser;
import it.sebastianosuraci.springboot.demo.repository.AppUserRepository;
import it.sebastianosuraci.springboot.core.service.BaseService;
import it.sebastianosuraci.springboot.core.service.IBaseService;

@Service
public class AppUserService extends BaseService<AppUser, Integer> implements IBaseService<AppUser, Integer> {

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        super(appUserRepository);
    } 
}
