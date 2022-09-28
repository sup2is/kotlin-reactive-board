package me.sup2is.kotlinreactiveboard.receiver.converter

import reactor.kafka.receiver.ReceiverRecord

interface MessageConverter<T> {
    fun convert(receiverRecord: ReceiverRecord<String, String>): T
}