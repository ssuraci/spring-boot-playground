package it.sebastianosuraci.springboot.demo.repository;

import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.AppUser;
import it.sebastianosuraci.springboot.demo.domain.QAppUser;

public interface AppUserRepository extends BaseRepository<AppUser, Integer> {
    @Override
    default QBaseEntity getQBaseEntity() {
        return QAppUser.appUser._super._super;    
    }
}
