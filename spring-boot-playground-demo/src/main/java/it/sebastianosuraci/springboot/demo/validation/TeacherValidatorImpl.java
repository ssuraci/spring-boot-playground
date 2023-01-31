package it.sebastianosuraci.springboot.demo.validation;

import java.util.Optional;

import jakarta.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.sebastianosuraci.springboot.demo.domain.Teacher;
import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;
import it.sebastianosuraci.springboot.demo.repository.TeacherRepository;

@Component
public class TeacherValidatorImpl implements TeacherValidator {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TeacherRepository teacherRepository;

	@Override
	public void initialize(TeacherValid arg0) {
		// empty initialization
	}

	@Override
	public boolean isValid(TeacherDTO teacherDto, ConstraintValidatorContext constraintValidatorContext) {

		boolean valid = true;
		try {
			Optional<Teacher> entityCheck = teacherRepository.findOneByEmail(teacherDto.getEmail());
			if (entityCheck.isPresent() && !entityCheck.get().getId().equals(teacherDto.getId())) {
				constraintValidatorContext.disableDefaultConstraintViolation();
				constraintValidatorContext.buildConstraintViolationWithTemplate("Email already present")
						.addPropertyNode("email").addConstraintViolation();
				valid = false;
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			constraintValidatorContext.buildConstraintViolationWithTemplate("Error in validation")
					.addConstraintViolation();
			return false;
		}
		return valid;
	}
}