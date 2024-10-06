package com.microservice.reservas_inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients  // Habilita Feign Client
public class MicroservicioReservaInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioReservaInventarioApplication.class, args);
	}

}
