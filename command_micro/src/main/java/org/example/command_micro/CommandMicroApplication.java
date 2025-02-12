package org.example.command_micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CommandMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandMicroApplication.class, args);
	}

}
