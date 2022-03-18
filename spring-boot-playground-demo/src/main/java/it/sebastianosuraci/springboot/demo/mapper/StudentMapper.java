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
import it.sebastianosuraci.springboot.demo.domain.Student;
import it.sebastianosuraci.springboot.demo.dto.StudentDTO;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public abstract class StudentMapper extends BaseMapper implements IBaseMapper<Student, StudentDTO, Integer> {
   
    @Autowired
    IBaseService<School, Integer> schoolService;
    public School integerToSchool(Integer id) throws AppException {
		return schoolService.findById(id, FetchOptions.builder().userPermFilter(true).build()).orElse(null);
	}

    @Mapping(target = "enrolledCourses", ignore = true)
	@Mapping(target = "skills", ignore = true)
	@Override
    @Mapping(target = "school", source="schoolId")
	public abstract Student dtoToEntity(StudentDTO user);


    @Override
	@Mapping(target = "schoolName", source="school.name")
	@Mapping(target = "schoolId", source="school.id") 
	public abstract StudentDTO entityToDto(Student student);


	@Override
    @Mapping(target = "value", source="id")
    @Mapping(target = "label", source="name")
	public abstract DropdownDTO entityToDropdownDto(Student entity);

}
