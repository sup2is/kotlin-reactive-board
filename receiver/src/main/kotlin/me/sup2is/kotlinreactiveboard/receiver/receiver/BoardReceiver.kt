package me.sup2is.kotlinreactiveboard.receiver.receiver

import me.sup2is.kotlinreactiveboard.receiver.converter.BoardMessageConverter
import me.sup2is.kotlinreactiveboard.receiver.service.BoardService
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import reactor.kafka.receiver.KafkaReceiver
import reactor.kotlin.core.publisher.toMono

@Component
class BoardReceiver(
    private val kafkaReceiver: KafkaReceiver<String, String>,
    private val boardService: BoardService,
    private val boardMessageConverter: BoardMessageConverter
) {

    private val logger = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    fun runReceiver() {
        kafkaReceiver.receive()
            .flatMap {
                boardService.create(boardMessageConverter.convert(it))
                    .onErrorStop().then(it.toMono())
            }
            .onErrorContinue { e, record ->
                logger.error(e) { "error!! record: $record" }
            }
            .subscribe {
                it.receiverOffset().acknowledge()
            }
    }


}