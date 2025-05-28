package com.console.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LessonMvcApplication {

    public static void main(String[] args) {
       ConfigurableApplicationContext context = SpringApplication.run(LessonMvcApplication.class, args);

        Controller controller = context.getBean(Controller.class);
        controller.mainLoop();
        context.close();
    }

}
