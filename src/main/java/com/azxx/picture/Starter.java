package com.azxx.picture;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @SpringBootApplication 包含了三个特点：
 *
 * @EnableAutoConfiguration: enable Spring Boot’s auto-configuration mechanism
 * @ComponentScan: enable @Component scan on the package where the application is located (see the best practices)
 * @Configuration: allow to register extra beans in the context or import additional configuration classes
 *
 */
//@SpringBootApplication(exclude = {Starter.class,HelloWorld.class}) // 等同于 @EnableAutoConfiguration @ComponentScan @Configuration
@SpringBootApplication // 等同于 @EnableAutoConfiguration @ComponentScan @Configuration
@ComponentScan(basePackages = {
        "com.azxx.picture.configuration",
        "com.azxx.picture.controller",
        "com.azxx.picture.service",
        "com.azxx.picture.schedule"})
@EnableScheduling
@EnableWebMvc
public class Starter {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Starter.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
//        SpringApplication.run(Starter.class,args);
    }
}
