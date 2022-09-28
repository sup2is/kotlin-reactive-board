package me.sup2is.kotlinreactiveboard.receiver.converter

import com.fasterxml.jackson.databind.ObjectMapper
import me.sup2is.kotlinreactiveboard.domain.model.Board
import org.springframework.stereotype.Component
import reactor.kafka.receiver.ReceiverRecord

@Component
class BoardMessageConverter(
    private val objectMapper: ObjectMapper
) : MessageConverter<Board> {

    override fun convert(receiverRecord: ReceiverRecord<String, String>): Board {
        return objectMapper.readValue(receiverRecord.value(), Board::class.java)
    }
}