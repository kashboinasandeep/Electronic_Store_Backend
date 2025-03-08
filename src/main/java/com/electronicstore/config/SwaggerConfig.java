package com.electronicstore.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Arrays.asList(getSecurityContext()))
                .securitySchemes(Arrays.asList(getSecuritySchemes()));

        ApiSelectorBuilder select = docket.select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any());

        return select.build();
    }

    
    private SecurityContext getSecurityContext() {
    	SecurityContext context = SecurityContext
    			.builder()
    			.securityReferences(getSecurityReferences())
    			.build();
    			return context;
    }
    
    
    private List<SecurityReference> getSecurityReferences() {
    	
       AuthorizationScope[] scopes = {new AuthorizationScope("Global", "Acess Every Thing")  };
     
       return Arrays.asList(new SecurityReference("JWT", scopes));
    }
    
    

    private ApiKey getSecuritySchemes() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Electronic Store Backend API",
                "This is an Electronic Store project using Java Spring Boot and React.",
                "1.0.0",
                "https://www.code_learners.com",
                new Contact("Nagaraju", "https://www.instagram.com/abcd", "nagaraju1819@gmail.com"),
                "LICENSES OF API",
                "https://www.learncodes.com/about",
                Collections.emptyList()
        );
    }
}
