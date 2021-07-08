package it.sebastianosuraci.springboot.demo.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.demo.domain.AppUser;
import it.sebastianosuraci.springboot.demo.domain.Course;
import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.dto.AppUserDTO;
import it.sebastianosuraci.springboot.demo.dto.CourseDTO;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;
import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;
import it.sebastianosuraci.springboot.demo.service.SchoolService;

@Mapper(componentModel = "spring")
public abstract class CrudMapper {

	@Autowired
	SchoolService schoolService;

	@BeforeMapping
	<T> Set<T> fixLazyLoadingSet(Collection<?> c, @TargetType Class<?> targetType) {
		if (!MapperUtil.wasInitialized(c)) {
			return Collections.emptySet();
		}
		return null;
	}

	@BeforeMapping
	<T> List<T> fixLazyLoadingList(Collection<?> c, @TargetType Class<?> targetType) {
		if (!MapperUtil.wasInitialized(c)) {
			return Collections.emptyList();
		}
		return null;
	}

	
	public School integerToSchool(Integer id) throws AppException {
		return schoolService.findById(id).orElse(null);
	}
	
	@Mapping(target = "passwd", ignore = true)
	public abstract AppUser appUserDtoToUser(AppUserDTO user);

	public abstract AppUserDTO appUserToAppUserDto(AppUser user);

	public abstract List<AppUserDTO> appUserToAppUserDtos(Collection<AppUser> userList);

	public abstract School schoolDtoSchool(SchoolDTO school);

	public abstract SchoolDTO schoolToSchoolDto(School school);

	public abstract List<SchoolDTO> schoolToSchoolDtos(Collection<School> schoolList);

	@Mapping(target = "teacherId", source="teacher.id")
	@Mapping(target = "teacherName", source="teacher.name")
	public abstract CourseDTO courseToCourseDto(Course course);
	
	public abstract List<CourseDTO> courseToCourseDtos(Collection<Course> courseList);
	
	@Mapping(target = "school", source="schoolId")
	@Mapping(target = "courseList", ignore = true)
	public abstract Teacher teacherDtoTeacher(TeacherDTO teacher);

	@Mapping(target = "schoolName", source="school.name")
	@Mapping(target = "schoolId", source="school.id") 
	public abstract TeacherDTO teacherToTeacherDto(Teacher teacher);

	public abstract List<TeacherDTO> teacherToTeacherDtos(Collection<Teacher> teacherList);

}
