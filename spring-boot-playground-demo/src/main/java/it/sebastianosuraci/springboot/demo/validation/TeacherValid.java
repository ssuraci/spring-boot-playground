package it.sebastianosuraci.springboot.demo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE}) 
@Constraint(validatedBy = TeacherValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface TeacherValid {
	
	String message() default "msg";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
