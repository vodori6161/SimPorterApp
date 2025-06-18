package com.example.SimPorter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.example.SimPorter.model")
public class SimPorterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimPorterApplication.class, args);
	}

}
