package me.sup2is.kotlinreactiveboard.batch.config

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

@TestConfiguration
@EnableBatchProcessing
class BatchTestConfig {

    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .url("jdbc:h2:~/test")
            .driverClassName("org.h2.Driver")
            .username("sa")
            .build()
    }
}
