package it.sebastianosuraci.springboot.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.sebastianosuraci.springboot.core.mapper.BaseMapper;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.demo.domain.AppUser;
import it.sebastianosuraci.springboot.demo.dto.AppUserDTO;

@Mapper(componentModel = "spring")
public abstract class AppUserMapper extends BaseMapper implements IBaseMapper<AppUser, AppUserDTO, Integer> {
   
    @Override
    @Mapping(target = "passwd", ignore = true)
	public abstract AppUser dtoToEntity(AppUserDTO user);
}
