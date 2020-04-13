package com.icloud.dominik;

import backend.transactions.AssetsManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.Connection;

import static backend.Mapping.getConnection;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(/*exclude = ErrorMvcAutoConfiguration.class*/)
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
