package it.sebastianosuraci.springboot.demo.repository;

import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.AppUser;

public interface AppUserRepository extends BaseRepository<AppUser, Integer> {
    
}
