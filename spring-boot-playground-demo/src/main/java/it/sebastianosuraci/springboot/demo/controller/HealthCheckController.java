package it.sebastianosuraci.springboot.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("healthcheck")
public class HealthCheckController {

    @GetMapping
    public String healthCheck() {
        return "OK";
    } 
    
}
