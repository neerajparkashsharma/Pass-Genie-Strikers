package com.project.passgenie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class PassgenieApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassgenieApplication.class, args);
    }

}
