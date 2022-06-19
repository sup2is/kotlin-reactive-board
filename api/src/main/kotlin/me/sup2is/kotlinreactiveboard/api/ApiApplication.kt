package me.sup2is.kotlinreactiveboard.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinReactiveBoardApplication

fun main(args: Array<String>) {
    runApplication<KotlinReactiveBoardApplication>(*args)
}
