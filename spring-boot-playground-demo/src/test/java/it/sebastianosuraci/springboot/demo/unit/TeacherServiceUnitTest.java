package it.sebastianosuraci.springboot.demo.unit;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.repository.TeacherRepository;
import it.sebastianosuraci.springboot.demo.service.TeacherService;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// https://www.baeldung.com/mockito-spy

@ExtendWith(MockitoExtension.class)
public class TeacherServiceUnitTest {
	@Mock
	TeacherRepository teacherRepository;

	@InjectMocks
	TeacherService teacherService;

	@Test
	public void validateTeacherService() {
		Teacher teacher1 = new Teacher();
		teacher1.setId( 1 );
		teacher1.setFirstName( "Mario" );
		teacher1.setLastName( "Rossi" );

		Teacher teacher2 = new Teacher();
		teacher2.setId( 2 );
		teacher2.setFirstName( "Luigi" );
		teacher2.setLastName( "Rossi" );

		given( teacherRepository.getList( any() ) )
				.willReturn( List.of( teacher1, teacher2 ) );

		FetchOptions fo1 = FetchOptions.builder().userPermFilter( true ).build();
		FetchOptions fo2 = FetchOptions.builder().userPermFilter( false ).build();

		assertThat( teacherService.getList( fo1 ).size()).isEqualTo( 2 );

		verify( teacherRepository, times( 1 ) ).getList( fo1 );

		ArgumentCaptor<FetchOptions> argument = ArgumentCaptor.forClass( FetchOptions.class);
		verify( teacherRepository, times( 1 ) ).getList( argument.capture());
		assertThat( argument.getValue().isUserPermFilter() ).isTrue();

	}
}