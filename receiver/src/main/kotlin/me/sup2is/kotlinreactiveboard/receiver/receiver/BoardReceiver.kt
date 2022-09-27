package me.sup2is.kotlinreactiveboard.receiver.receiver

import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import reactor.kafka.receiver.KafkaReceiver

@Component
class BoardReceiver(
    private val kafkaReceiver: KafkaReceiver<String, String>
) {

    private val logger = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    fun runReceiver() {
        kafkaReceiver.receive()
            .map {
                logger.info {"key: ${it.key()}"}
                logger.info {"value: ${it.value()}"}
                it
            }
            .doOnError {
                logger.error(it) { " error "}
            }
            .subscribe {
                it.receiverOffset().acknowledge()
            }
    }


}