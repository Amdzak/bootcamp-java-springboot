package com.example.bootcamp_day_2.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI springOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Api Documentation Bootcamp")
                        .description("Api Documentation Bootcamp Day2")
                        .version("v1.0.0"));
    }

    @Bean
    public GroupedOpenApi apiGroupA() {
        return GroupedOpenApi.builder()
                .group("API A - Product")
                .pathsToMatch("/**") // Hanya menampilkan API yang path-nya diawali ini
                .packagesToScan("com.example.bootcamp_day_2.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiB() {
        return GroupedOpenApi.builder()
                .group("API B - Management")
                .pathsToMatch("/management/**")
                .packagesToScan("com.example.bootcamp_day_2.controller")
                .build();
    }
}
