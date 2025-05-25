package com.gutierrez.miguel.challenge.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gaming Challenge API")
                        .version("1.0")
                        .description("API documentation for the Gaming system challenge")
                        .contact(new Contact()
                                .name("Miguel Gutiérrez")
                                .email("migueangel97@hotmail.com"))
                        .license(new License().name("MIT")));
    }
}
