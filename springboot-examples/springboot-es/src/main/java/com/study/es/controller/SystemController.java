package com.study.es.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    @GetMapping(value = "/cache")
    public ResponseEntity<String> cache(){

        return null;
    }
}
