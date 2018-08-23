package com.bosch.bics.dashboard.bicsdashboardservices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/status")
    public String sayHello() {
        return "Hello,World!";
    }
}