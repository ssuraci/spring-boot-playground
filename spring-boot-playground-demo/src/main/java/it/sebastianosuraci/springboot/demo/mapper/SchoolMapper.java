package it.sebastianosuraci.springboot.demo.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import it.sebastianosuraci.springboot.core.dto.DropdownDTO;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.mapper.BaseMapper;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.core.service.IBaseService;
import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;

@Mapper(componentModel = "spring")
public abstract class SchoolMapper extends BaseMapper implements IBaseMapper<School, SchoolDTO, Integer> {
    
    @Autowired
    IBaseService<School, Integer> schoolService;

	
    @Override
    @Mapping(target = "value", source="id")
    @Mapping(target = "label", source="name")
	public abstract DropdownDTO entityToDropdownDto(School entity);

    public School fromId(Integer id) throws AppException {
		return id != null ? schoolService.findById(id, FetchOptions.builder().userPermFilter(true).build()).orElseThrow(() -> new AppException(ErrCode.NOT_FOUND, "school")) : null;
	}

}

