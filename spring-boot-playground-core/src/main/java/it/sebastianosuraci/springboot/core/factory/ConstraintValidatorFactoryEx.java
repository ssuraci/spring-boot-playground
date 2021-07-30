package it.sebastianosuraci.springboot.core.factory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public class ConstraintValidatorFactoryEx implements ConstraintValidatorFactory {

            // private final Logger logger = LoggerFactory.getLogger(ConstraintValidatorFactoryEx.class);

            @Autowired
            private AutowireCapableBeanFactory beanFactory;
        
            @Override
            public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
                T bean = null;
        
                try {
                    // logger.info("Trying to find a validator bean of class " + key.getSimpleName());
                    bean = this.beanFactory.getBean(key);
                } catch (BeansException exc) {
                    // logger.info("Failed to find a bean of class " + key.getSimpleName());
                }
        
                if (bean == null) {
                    try {
                        // logger.info("Creating a new validator bean of class " + key.getSimpleName());
                        bean = this.beanFactory.createBean(key);
                    } catch (BeansException exc) {
                        // logger.info("Failed to create a validator of class " + key.getSimpleName());
                    }
                }
        
                if (bean == null) {
                    // logger.warn("Failed to get validator of class " + key.getSimpleName());
                }
        
                return bean;
            }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        
    }
}