package com.dongpop.urin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class UrinApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrinApplication.class, args);
	}
}
