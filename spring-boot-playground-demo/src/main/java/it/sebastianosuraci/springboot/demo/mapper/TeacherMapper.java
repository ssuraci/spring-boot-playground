package it.sebastianosuraci.springboot.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import it.sebastianosuraci.springboot.core.dto.DropdownDTO;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.mapper.BaseMapper;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.core.service.IBaseService;
import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public abstract class TeacherMapper extends BaseMapper implements IBaseMapper<Teacher, TeacherDTO, Integer> {
   
    @Autowired
    IBaseService<School, Integer> schoolService;
    public School integerToSchool(Integer id) throws AppException {
		return schoolService.findById(id, FetchOptions.builder().userPermFilter(true).build()).orElse(null);
	}

    @Override
    @Mapping(target = "school", source="schoolId")
	@Mapping(target = "courseList", ignore = true)
	public abstract Teacher dtoToEntity(TeacherDTO user);


    @Override
	@Mapping(target = "schoolName", source="school.name")
	@Mapping(target = "schoolId", source="school.id") 
	@Mapping(target = "subjectDescription", source="subject.description")
	@Mapping(target = "subjectId", source="subject.id") 
	public abstract TeacherDTO entityToDto(Teacher teacher);


	@Override
    @Mapping(target = "value", source="id")
    @Mapping(target = "label", source="name")
	public abstract DropdownDTO entityToDropdownDto(Teacher entity);
}
