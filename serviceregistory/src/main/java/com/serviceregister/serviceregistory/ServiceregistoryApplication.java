package com.serviceregister.serviceregistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer

public class ServiceregistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceregistoryApplication.class, args);
	}

}
