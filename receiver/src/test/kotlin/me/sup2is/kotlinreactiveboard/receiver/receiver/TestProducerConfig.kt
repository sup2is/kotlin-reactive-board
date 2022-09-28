package me.sup2is.kotlinreactiveboard.receiver.receiver

import me.sup2is.kotlinreactiveboard.receiver.config.KafkaProperties
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import java.util.*

@TestConfiguration
class TestProducerConfig {

    @Bean
    fun producer(kafkaProperties: KafkaProperties): KafkaReceiver<String, String>? {
        return KafkaReceiver.create(getProducerOptions(kafkaProperties))
    }

    private fun getProducerOptions(kafkaProperties: KafkaProperties): ReceiverOptions<String, String> {
        val properties = Properties()

        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

        return ReceiverOptions.create<String?, String?>(properties)
            .subscription(listOf(kafkaProperties.topic))
    }

}