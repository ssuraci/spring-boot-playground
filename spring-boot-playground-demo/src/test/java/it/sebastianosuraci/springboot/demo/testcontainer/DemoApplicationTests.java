package it.sebastianosuraci.springboot.demo.testcontainer;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.repository.CourseRepository;
import it.sebastianosuraci.springboot.demo.repository.TeacherRepository;
import it.sebastianosuraci.springboot.demo.service.SchoolService;
import it.sebastianosuraci.springboot.demo.service.TeacherService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	TeacherService teacherService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	CourseRepository courseRepository;


	@Test
	// @Disabled
	void appTeacher02Test() throws AppException {
		Optional<School> schoolOpt = schoolService.findById(1, FetchOptions.builder().userPermFilter(false).pageModel(new PageModel()).build());
		Assert.assertTrue(schoolOpt.isPresent());
		Teacher t = new Teacher();
		t.setFirstName("Michele");
		t.setLastName("Bianchi");
		t.setSchool(schoolOpt.get());
		teacherService.insert(t);
		Assert.assertEquals(3, teacherService.getList(FetchOptions.builder().userPermFilter(false).pageModel(new PageModel()).build()).size());
	}

	@Test
	// @Disabled
	void appTeacher03Test() throws AppException {
		// spring-data
		Assert.assertEquals(1, teacherRepository.findByLastName("Rossi").size());
		// JPQL
		Assert.assertEquals(1, teacherRepository.qFindByLastName("Rossi").size());
		// native query
		Assert.assertEquals(1, teacherRepository.nqFindByLastName("Rossi").size());
		// queryDSL
		Assert.assertEquals(1, teacherRepository.qdslFindByLastName("Rossi").size());
		
		Assert.assertEquals(0, teacherRepository.findByLastName("Rossixx").size());
		Assert.assertEquals(0, teacherRepository.qFindByLastName("Rossixx").size());
		Assert.assertEquals(0, teacherRepository.nqFindByLastName("Rossixx").size());
		Assert.assertEquals(0, teacherRepository.qdslFindByLastName("Rossixx").size());

		// explicit query with EntityManager
		Assert.assertEquals(1, courseRepository.qrFindByTitle("Corso test title").size());

		// EntityGraph static	
		Optional <Teacher> teacherOpt = teacherRepository.findOneById(1);
		Assert.assertTrue(teacherOpt.isPresent());
		Assert.assertEquals(2, teacherOpt.get().getCourseList().size());

		// EntityGraph dynamic	
//		teacherOpt = teacherRepository.findById(1, DynamicEntityGraph. .fromAttributePaths("courseList"));
//		Assert.assertTrue(teacherOpt.isPresent());
//		Assert.assertEquals(2, teacherOpt.get().getCourseList().size());
	}

}
