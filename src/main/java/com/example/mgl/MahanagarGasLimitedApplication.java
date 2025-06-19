package com.example.mgl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MahanagarGasLimitedApplication {

	public static void main(String[] args) {
		SpringApplication.run(MahanagarGasLimitedApplication.class, args);
	}

}
   
    
