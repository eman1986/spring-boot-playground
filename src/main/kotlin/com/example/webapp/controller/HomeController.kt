package com.example.webapp.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    public fun index(): ResponseEntity<String>
    {
        return ResponseEntity.ok("API is running!")
    }
}
