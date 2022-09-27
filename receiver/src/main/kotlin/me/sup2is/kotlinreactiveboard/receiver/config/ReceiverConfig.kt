package me.sup2is.kotlinreactiveboard.receiver.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import java.util.Properties

@Configuration
class ReceiverConfig {

    private val groupId: String = "group-1"

    @Bean
    fun receiver(kafkaProperties: KafkaProperties): KafkaReceiver<String, String>? {
        return KafkaReceiver.create(getReceiverOptions(kafkaProperties))
    }

    private fun getReceiverOptions(kafkaProperties: KafkaProperties): ReceiverOptions<String, String> {
        val properties = Properties()

        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        properties[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

        return ReceiverOptions.create<String?, String?>(properties)
            .subscription(listOf(kafkaProperties.topic))
    }

}