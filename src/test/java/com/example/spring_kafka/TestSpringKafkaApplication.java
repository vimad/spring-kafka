package com.example.spring_kafka;

import org.springframework.boot.SpringApplication;

public class TestSpringKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringKafkaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
