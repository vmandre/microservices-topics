package com.vmandre.microservices.limitsservice.controller;

import com.vmandre.microservices.limitsservice.bean.Limits;
import com.vmandre.microservices.limitsservice.configuration.Configuration;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LimitsController {
    
    private Configuration configuration;
    
    @GetMapping("/limits")
    public Limits retrieveLimits() {
//        return new Limits(1, 1000);
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
    }
}
