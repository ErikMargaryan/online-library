package com.library.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI baseOpenApi() {
        return new OpenAPI().info(new Info().title("Spring Doc").version("1.0.0").description("Spring doc"));
    }
}
