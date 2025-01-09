package fcu.example.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloworldcontroller {


    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }


    @GetMapping("/hello/{name}")
    public String helloWorld(@PathVariable String name) {
        return "<h1>HI "+name+"</h1>";
    }


}
