package com.microservice.estudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceEstudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEstudentApplication.class, args);
	}

}
