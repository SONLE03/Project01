package com.clothing.warrantyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WarrantyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarrantyServiceApplication.class, args);
	}

}
