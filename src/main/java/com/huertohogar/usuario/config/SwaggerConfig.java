package com.huertohogar.usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customerOpenAPI(){
        return new OpenAPI()
        .info(new Info()
        .title("API huerto hogar Usuario Service")
        .version("1.0.0")
        .description("API documentacion para huerto hogar usuario service")
        );
    }
}

