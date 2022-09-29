package me.sup2is.kotlinreactiveboard.receiver.receiver

import me.sup2is.kotlinreactiveboard.receiver.config.KafkaProperties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions
import java.util.*

@TestConfiguration
class TestProducerConfig {

    @Bean
    fun producer(kafkaProperties: KafkaProperties): KafkaSender<String, String>? {
        return KafkaSender.create(getProducerOptions(kafkaProperties))
    }

    private fun getProducerOptions(kafkaProperties: KafkaProperties): SenderOptions<String, String> {
        val properties = Properties()

        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

        return SenderOptions.create(properties)
    }

}