package it.sebastianosuraci.springboot.demo.config;

import jakarta.validation.ConstraintValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

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