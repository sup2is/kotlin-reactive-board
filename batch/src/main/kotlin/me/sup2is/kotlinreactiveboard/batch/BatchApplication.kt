package me.sup2is.kotlinreactiveboard.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["me.sup2is.kotlinreactiveboard"])
@EnableBatchProcessing
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}
