package com.microservice.microservicepedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "com.microservice")
public class MicroservicePedidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicePedidoApplication.class, args);
    }

}
