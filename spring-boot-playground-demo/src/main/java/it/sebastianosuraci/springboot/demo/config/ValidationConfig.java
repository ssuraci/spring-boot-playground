package it.sebastianosuraci.springboot.demo.config;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.sebastianosuraci.springboot.core.factory.ConstraintValidatorFactoryEx;

@Configuration
public class ValidationConfig {

    @Bean
    ConstraintValidatorFactory constraintValidatorFactoryEx() {
        return new ConstraintValidatorFactoryEx();
    }

    @Bean
    public ValidatorFactory validatorFactory() {
        return Validation.byDefaultProvider()
                .configure().constraintValidatorFactory(constraintValidatorFactoryEx()).buildValidatorFactory();

    }

    @Bean
    public Validator validator() {
        return validatorFactory().getValidator();
    }

}