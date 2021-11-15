package it.sebastianosuraci.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
