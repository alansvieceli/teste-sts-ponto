package com.vieceli.pontointeligente.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TestePontoInteligenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestePontoInteligenteApplication.class, args);
	}
}
