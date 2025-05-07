package com.smartjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class UserProjectApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(UserProjectApplication.class, args);
    }

} 