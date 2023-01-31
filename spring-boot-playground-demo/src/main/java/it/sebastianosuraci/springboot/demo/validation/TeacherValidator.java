package it.sebastianosuraci.springboot.demo.validation;

import jakarta.validation.ConstraintValidator;

import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;

public interface TeacherValidator extends ConstraintValidator<TeacherValid, TeacherDTO> {
    
}
