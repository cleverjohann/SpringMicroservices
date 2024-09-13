package com.microservice.producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "com.microservice")
public class MicroserviceProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProductoApplication.class, args);
	}

}
