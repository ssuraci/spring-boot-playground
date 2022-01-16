package it.sebastianosuraci.springboot.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.sebastianosuraci.springboot.core.dto.DropdownDTO;
import it.sebastianosuraci.springboot.core.mapper.BaseMapper;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.demo.domain.Course;
import it.sebastianosuraci.springboot.demo.dto.CourseDTO;

@Mapper(componentModel = "spring")
public abstract class CourseMapper extends BaseMapper implements IBaseMapper<Course, CourseDTO, Integer> {
   
	@Override
	@Mapping(target = "teacherId", source="teacher.id")
	@Mapping(target = "teacherName", source="teacher.name")
	@Mapping(target = "schoolId", source="teacher.school.id")
	@Mapping(target = "schoolName", source="teacher.school.name")
	public abstract CourseDTO entityToDto(Course course);

	@Override
    @Mapping(target = "value", source="id")
    @Mapping(target = "label", source="title")
	public abstract DropdownDTO entityToDropdownDto(Course entity);
}
