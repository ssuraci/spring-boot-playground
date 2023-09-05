package it.sebastianosuraci.springboot.demo.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig {
	
	protected static final String API_CONTEXT="/api/demo/**";


	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins( Arrays.asList( "*"));
		configuration.setAllowedMethods(Arrays.asList(
				HttpMethod.GET.name(),
				HttpMethod.HEAD.name(),
				HttpMethod.POST.name(),
				HttpMethod.PATCH.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()));
		configuration.setAllowCredentials(false);
		configuration.addAllowedHeader("*");
		configuration.setExposedHeaders( List.of( "X-Total-Count" ) );
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(API_CONTEXT, configuration);
		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz
				.anyRequest().permitAll()
		);
		http.csrf( AbstractHttpConfigurer::disable );
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
		return http.build();
	}
}