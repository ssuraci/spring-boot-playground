package it.sebastianosuraci.springboot.demo.unit;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.demo.domain.School;
import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.repository.TeacherRepository;
import it.sebastianosuraci.springboot.demo.service.SchoolService;
import it.sebastianosuraci.springboot.demo.service.TeacherService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
public class TeacherRepositorySpyTest {

	@Autowired
	@SpyBean
	TeacherRepository teacherRepository;

	@Autowired
	TeacherService teacherService;

	@Autowired
	SchoolService schoolService;


	@Test
	@Sql("/test-data/init-data.sql")
	void appTeacherSpyTest() throws AppException {
		Optional<School> schoolOpt = schoolService.findById( 1001, FetchOptions.builder().userPermFilter( false).pageModel( new PageModel()).build());
		assertTrue(schoolOpt.isPresent());

		verifyNoInteractions( teacherRepository);

		Teacher t = new Teacher();
		t.setFirstName("Michele");
		t.setLastName("Bianchi");
		t.setSchool(schoolOpt.get());
		teacherService.insert(t);
		assertEquals(3, teacherService.getList(FetchOptions.builder().userPermFilter(false).pageModel(new PageModel()).build()).size());

		verify( teacherRepository, times( 1 ) ).save( any() );

		ArgumentCaptor<FetchOptions> argument = ArgumentCaptor.forClass( FetchOptions.class);
		verify( teacherRepository, times( 1 ) ).getList( argument.capture());
		assertThat( argument.getValue().isUserPermFilter() ).isFalse();

	}
}
