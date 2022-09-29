package me.sup2is.kotlinreactiveboard.receiver.receiver

import me.sup2is.kotlinreactiveboard.receiver.config.KafkaProperties
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.test.context.EmbeddedKafka
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.CountDownLatch




@SpringBootTest
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9093", "port=9093"]
)
@Import(TestProducerConfig::class)
class BoardReceiverTest {

    @Autowired
    lateinit var kafkaSender: KafkaSender<String, String>

    @Autowired
    lateinit var kafkaProperties: KafkaProperties

    @Test
    fun `kafka receiver test` () {

        // given
        val latch = CountDownLatch(1)

        val record = SenderRecord.create(
            kafkaProperties.topic,
            1,
            LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            "1",
            """
                {"title": "title", "contents": "contents", "author": "author"}
            """.trimIndent(),
            ""
        ).toMono()

        kafkaSender.send(record)
            .doOnNext {
                println("success to send. message = $it")
            }.doOnError {
                println("fail to send. message = $it")
            }.subscribe {
                latch.countDown()
            }

        latch.await();

        // when


    }
}