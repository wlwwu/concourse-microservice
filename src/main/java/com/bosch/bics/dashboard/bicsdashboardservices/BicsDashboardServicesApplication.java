package com.bosch.bics.dashboard.bicsdashboardservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@RestController
public class BicsDashboardServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BicsDashboardServicesApplication.class, args);
	}

}
