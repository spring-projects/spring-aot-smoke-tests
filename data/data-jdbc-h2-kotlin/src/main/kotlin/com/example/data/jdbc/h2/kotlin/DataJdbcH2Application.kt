package com.example.data.jdbc.h2.kotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DataJdbcH2Application

fun main(args: Array<String>) {
	SpringApplication.run(DataJdbcH2Application::class.java, *args)
}
