package com.cws.sfm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	// region -- Methods --

	@Bean
	public Docket api() {
		// http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	// end
}