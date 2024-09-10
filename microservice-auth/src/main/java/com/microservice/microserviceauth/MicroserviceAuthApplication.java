package com.microservice.microserviceauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAuthApplication.class, args);
    }

}
