package com.maksimpegov.todos;

import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableEurekaClient
public class TodosApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodosApplication.class, args);
    }
}
