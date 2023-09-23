package it.sebastianosuraci.springboot.demo.integration;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TeacherIntegrationTestH2 {

	@Autowired
	TeacherService teacherService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	CourseRepository courseRepository;


	@Test
	@Sql("/test-data/init-data.sql")
	void appTeacher02Test() throws AppException {
		Optional<School> schoolOpt = schoolService.findById(1001, FetchOptions.builder().userPermFilter(false).pageModel(new PageModel()).build());
		assertTrue(schoolOpt.isPresent());
		Teacher t = new Teacher();
		t.setFirstName("Michele");
		t.setLastName("Bianchi");
		t.setSchool(schoolOpt.get());
		teacherService.insert(t);
		assertEquals(3, teacherService.getList(FetchOptions.builder().userPermFilter(false).pageModel(new PageModel()).build()).size());
	}

	@Test
	@Sql({"/test-data/init-data.sql", "/test-data/course-data.sql"})
	void appTeacher03Test() throws AppException {
		// spring-data
		assertEquals(1, teacherRepository.findByLastName("Rossi").size());
		// JPQL
		assertEquals(1, teacherRepository.qFindByLastName("Rossi").size());
		// native query
		assertEquals(1, teacherRepository.nqFindByLastName("Rossi").size());
		// queryDSL
		assertEquals(1, teacherRepository.qdslFindByLastName("Rossi").size());
		
		assertEquals(0, teacherRepository.findByLastName("Rossixx").size());
		assertEquals(0, teacherRepository.qFindByLastName("Rossixx").size());
		assertEquals(0, teacherRepository.nqFindByLastName("Rossixx").size());
		assertEquals(0, teacherRepository.qdslFindByLastName("Rossixx").size());

		// explicit query with EntityManager
		assertEquals(1, courseRepository.qrFindByTitle("Corso Spring").size());

		// EntityGraph static	
		Optional <Teacher> teacherOpt = teacherRepository.findOneById(1001);
		assertTrue(teacherOpt.isPresent());
		assertEquals(2, teacherOpt.get().getCourseList().size());

		// EntityGraph dynamic	
//		teacherOpt = teacherRepository.findById(1, DynamicEntityGraph. .fromAttributePaths("courseList"));
//		assertTrue(teacherOpt.isPresent());
//		assertEquals(2, teacherOpt.get().getCourseList().size());
	}

}
