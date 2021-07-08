package it.sebastianosuraci.springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sebastianosuraci.springboot.demo.domain.AppUser;
import it.sebastianosuraci.springboot.demo.repository.AppUserRepository;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.core.service.BaseService;

@Service
public class AppUserService extends BaseService<AppUser, Integer> {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    protected BaseRepository<AppUser, Integer> getBaseRepository() {
        return appUserRepository;
    }
    
}
