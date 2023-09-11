package it.sebastianosuraci.springboot.demo.unit;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;
import it.sebastianosuraci.springboot.demo.repository.TeacherRepository;
import it.sebastianosuraci.springboot.demo.validation.TeacherValidator;
import it.sebastianosuraci.springboot.demo.validation.TeacherValidatorImpl;
import jakarta.validation.ClockProvider;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherValidatorUnitTest {
	@Mock
	TeacherRepository teacherRepository;

	@Mock
	ConstraintValidatorContext constraintValidatorContext;

	@Mock
	ConstraintValidatorContext.ConstraintViolationBuilder constraintValidatorContextBuilder;

	@Mock
	ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

	@InjectMocks
	TeacherValidatorImpl teacherValidator;

	// https://www.baeldung.com/mockito-verify
	// https://www.baeldung.com/mockito-behavior

	@DisplayName("JUnit test for svalidate teacher withoud duplicated email")
	@Test
	public void validateTeacher() {

		TeacherDTO teacherDto1 = TeacherDTO.builder().firstName( "Mario" ).lastName( "Rossi" ).email( "teacher1@example.com" ).build();
		TeacherDTO teacherDto2 = TeacherDTO.builder().firstName( "Luigi" ).lastName( "Bianchi" ).email( "teacher2@example.com" ).build();
		TeacherDTO teacherDto3 = TeacherDTO.builder().firstName( "Michele" ).lastName( "Verdi" ).email( "teacher2@example.com" ).build();
		teacherDto1.setId( 1 );
		teacherDto2.setId( 2 );
		teacherDto3.setId( 3 );

		Teacher teacher2 = new Teacher();
		teacher2.setId( 2 );
		teacher2.setFirstName( "Luigi" );
		teacher2.setLastName( "Rossi" );

		given(teacherRepository.findOneByEmail("teacher1@example.com"))
				.willReturn(Optional.empty());
		given(teacherRepository.findOneByEmail("teacher2@example.com"))
				.willReturn(Optional.of(teacher2));
		given(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).willReturn(constraintValidatorContextBuilder);

		given(constraintValidatorContextBuilder.addPropertyNode(anyString())).willReturn( nodeBuilderCustomizableContext );

		assertThat(teacherValidator.isValid( teacherDto1, constraintValidatorContext)).isTrue();
		verifyNoInteractions(constraintValidatorContext);

		assertThat(teacherValidator.isValid( teacherDto3, constraintValidatorContext)).isFalse();
		verify(constraintValidatorContext, times(1)).disableDefaultConstraintViolation();
		clearInvocations(  constraintValidatorContext);

		assertThat(teacherValidator.isValid( teacherDto2, constraintValidatorContext)).isTrue();
		verifyNoInteractions(constraintValidatorContext);
	}
}
