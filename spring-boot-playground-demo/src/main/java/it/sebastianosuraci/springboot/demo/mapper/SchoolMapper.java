package it.sebastianosuraci.springboot.demo.mapper;
import org.mapstruct.Mapper;

import it.sebastianosuraci.springboot.core.mapper.BaseMapper;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class})
public abstract class SchoolMapper extends BaseMapper implements IBaseMapper<School, SchoolDTO, Integer> {
   
}

