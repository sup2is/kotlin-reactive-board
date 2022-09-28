package me.sup2is.kotlinreactiveboard.receiver.receiver

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.test.context.EmbeddedKafka
import reactor.kafka.sender.KafkaSender

@SpringBootTest
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9093", "port=9093"]
)
@Import(TestProducerConfig::class)
class BoardReceiverTest {

    @Autowired
    lateinit var kafkaSender: KafkaSender<String, String>

    @Test
    fun `kafka receiver test` () {
        println()
    }
}