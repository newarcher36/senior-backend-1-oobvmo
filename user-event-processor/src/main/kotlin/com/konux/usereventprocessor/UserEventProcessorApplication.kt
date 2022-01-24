package com.konux.usereventprocessor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserEventProcessorApplication

fun main(args: Array<String>) {
	runApplication<UserEventProcessorApplication>(*args)
}
