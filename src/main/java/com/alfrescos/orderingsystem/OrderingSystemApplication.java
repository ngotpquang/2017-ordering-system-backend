/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class OrderingSystemApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        String environment = System.getenv("ENV");
        return application.sources(OrderingSystemApplication.class).properties("spring.config.name:" + environment);
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderingSystemApplication.class, args);
    }
}
