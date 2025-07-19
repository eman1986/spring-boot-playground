package com.example.webapp

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<WebappApplication>().with(TestcontainersConfiguration::class).run(*args)
}
