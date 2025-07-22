package com.example.webapp.controller

import com.example.webapp.response.RestfulResponse
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

    @GetMapping("/json")
    public fun json(): RestfulResponse<String>
    {
        return RestfulResponse(true, "API is live!")
    }
}
