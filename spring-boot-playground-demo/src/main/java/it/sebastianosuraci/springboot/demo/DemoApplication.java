package it.sebastianosuraci.springboot.demo;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;

import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.demo.controller.TeacherController;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;
import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;
import lombok.SneakyThrows;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@ComponentScan(basePackages = {"it.sebastianosuraci.springboot.demo"})
@ImportRuntimeHints(DemoApplication.DemoApplicationRuntimeHints.class)
public class DemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	static class DemoApplicationRuntimeHints implements RuntimeHintsRegistrar {

        @SneakyThrows
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection()
                    .registerConstructor(PageModel.class.getDeclaredConstructor(), ExecutableMode.INVOKE)
					;
        }
    }

}
