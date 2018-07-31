package com.bosch.bics.dashboard.bicsdashboardservices;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	@RequestMapping("/status")
	public String sayHello() {
		return "Hello,World!";
	}
}