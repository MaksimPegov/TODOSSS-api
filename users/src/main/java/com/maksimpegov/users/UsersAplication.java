package com.maksimpegov.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class UsersAplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersAplication.class, args);
    }
}
