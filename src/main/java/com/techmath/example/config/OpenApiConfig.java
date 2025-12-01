package com.techmath.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Allcrud Example API")
                        .version("1.0.0")
                        .description("Comprehensive demonstration of the Allcrud library showcasing UUIDs, Composite Keys, and Business Rules.")
                        .contact(new Contact()
                                .name("Matheus Maia")
                                .email("mathmferreira@gmail.com")
                                .url("https://github.com/astro-techmath/allcrud"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }

}
