package dev.patika.hrmsManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"dev.patika"})
@EntityScan(basePackages = {"dev.patika"})
@EnableJpaRepositories(basePackages = {"dev.patika"})
@EnableScheduling
@SpringBootApplication
public class HrmsManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmsManagerApplication.class, args);
	}

}
