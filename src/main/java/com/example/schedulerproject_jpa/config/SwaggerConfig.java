package com.example.schedulerproject_jpa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info().title("스케줄러 프로젝트 API 명세서").description("Spring boot 3.2.5 + Swagger 2.2 기반 API 문서").version("v1.0.0"));
    }
}
