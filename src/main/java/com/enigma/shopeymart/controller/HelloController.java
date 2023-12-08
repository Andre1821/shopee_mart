package com.enigma.shopeymart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello(){
        return "<h1> Hello World </h1>";
    }


    @GetMapping(value = "/hello/v1")
    public String[] getHobies(){
        return new String[]{"makan", "tidur", "ngoding", "error", "pusing"};
    }

    @GetMapping(value = "hello/search{key}")
    public String getRequestParam(@RequestParam String key){
        return  "<body style='background-color:pink'><h1 style='color:white;font-size:300%;text-align:center;'>"+key+" wibu </h1></body>";
    }

    @GetMapping(value = "/data/{id}/{word}")
    public String getDataById(@PathVariable String id, @PathVariable String word){
        return "<body style='background-color:black'><h1 style='color:red;font-size:300%;text-align:center;'> CAPRES "+id+", "+word+"</h1></body>";
    }

    @GetMapping(value = "/data/{id}/search")
    public String getDataBy(@PathVariable("id") String id, @RequestParam("key") String key){
        return "<body style='background-color:blue'><h1 style='color:white;font-size:300%;text-align:center;'> CAPRES "+id+", "+key+"</h1></body>";
    }
}
