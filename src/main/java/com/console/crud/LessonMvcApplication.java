package com.console.crud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LessonMvcApplication {

    public static void main(String[] args) {
       SpringApplication.run(LessonMvcApplication.class, args);
    }
    @Bean
    CommandLineRunner run(Controller controller) {
        return args -> controller.mainLoop();
    }
}
