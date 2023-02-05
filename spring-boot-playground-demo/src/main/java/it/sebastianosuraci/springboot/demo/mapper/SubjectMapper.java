package it.sebastianosuraci.springboot.demo.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.sebastianosuraci.springboot.core.dto.DropdownDTO;
import it.sebastianosuraci.springboot.core.mapper.BaseMapper;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.demo.domain.Subject;
import it.sebastianosuraci.springboot.demo.dto.SubjectDTO;

@Mapper(componentModel = "spring")
public abstract class SubjectMapper extends BaseMapper implements IBaseMapper<Subject, SubjectDTO, Integer> {
    
    @Override
    @Mapping(target = "value", source="id")
    @Mapping(target = "label", source="description")
	public abstract DropdownDTO entityToDropdownDto(Subject entity);

}

