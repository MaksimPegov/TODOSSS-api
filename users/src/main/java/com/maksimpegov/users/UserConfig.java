package com.maksimpegov.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class UserConfig {
    @Value("${spring.constraints.users.path}")
    String servicePath;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant(servicePath + "/**"))
                .apis(RequestHandlerSelectors.basePackage("com.maksimpegov.users"))
                .build()
                .apiInfo(new ApiInfo("Users API",
                        "API for managing users",
                        "1.0",
                        null,
                        null,
                        null,
                        null));
    }
}
