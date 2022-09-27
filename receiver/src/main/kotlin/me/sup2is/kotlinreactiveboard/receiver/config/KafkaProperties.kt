package me.sup2is.kotlinreactiveboard.receiver.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("kafka")
class KafkaProperties {
    lateinit var bootstrapServers: List<String>
    lateinit var topic: String
}