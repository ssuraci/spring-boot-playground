package it.sebastianosuraci.springboot.demo.mapper;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.core.service.IBaseService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import it.sebastianosuraci.springboot.core.dto.DropdownDTO;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.mapper.BaseMapper;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.demo.domain.Subject;
import it.sebastianosuraci.springboot.demo.dto.SubjectDTO;

@Mapper(componentModel = "spring")
public abstract class SubjectMapper extends BaseMapper implements IBaseMapper<Subject, SubjectDTO, Integer> {
	@Autowired
	IBaseService<Subject, Integer> subjectService;

	@Override
    @Mapping(target = "value", source="id")
    @Mapping(target = "label", source="description")
	public abstract DropdownDTO entityToDropdownDto(Subject entity);
    
    public Subject fromId(Integer id) throws AppException {
		return id != null ? subjectService.findById(id, FetchOptions.builder().userPermFilter(true).build()).orElseThrow(() -> new AppException(ErrCode.NOT_FOUND, "subject")) : null;
	}


	public Subject from(Integer id) throws AppException {
		return id != null ? subjectService.findById( id, FetchOptions.builder().userPermFilter( true).build()).orElseThrow(() -> new AppException( AppException.ErrCode.NOT_FOUND )) : null;
	}
}

