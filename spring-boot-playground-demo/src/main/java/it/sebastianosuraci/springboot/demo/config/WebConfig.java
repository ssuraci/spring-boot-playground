package it.sebastianosuraci.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  {
    /* 
    @Bean
    public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("Expires", "Cache-control", "Pragma", "Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "X-Total-Count")
                .exposedHeaders("Expires", "Cache-control", "Pragma", "Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "X-Total-Count")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");
			}
		};
	}
  */
}

