package com.example.Mafia.configuration;

import com.datastax.oss.driver.shaded.guava.common.collect.Lists;
import com.example.Mafia.controller.UserController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import org.springframework.data.repository.query.Parameter;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;

import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;



@Configuration
@EnableSwagger2
/*@ComponentScan(basePackageClasses = {
        UserController.class
})*/
public class SwaggerConfig {


    private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.Mafia.controller"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .directModelSubstitute(SecurityProperties.User.class, java.util.Optional.class)
                .apiInfo(metaData());
    }
    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("Mafia userService")
                .description("Beautiful requests from userService")
                .termsOfServiceUrl("https://mafias-user-service-app.herokuapp.com/")
                .licenseUrl("mafia@gmail.com").version("1.0").build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

}
