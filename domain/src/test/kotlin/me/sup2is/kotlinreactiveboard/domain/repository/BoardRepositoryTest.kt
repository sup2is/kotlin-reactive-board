package me.sup2is.kotlinreactiveboard.domain.repository

import me.sup2is.kotlinreactiveboard.domain.model.Board
import me.sup2is.kotlinreactiveboard.domain.service.SequenceGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import reactor.kotlin.test.test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

@DataMongoTest
@Import(SequenceGenerator::class)
class BoardRepositoryTest {

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var reactiveMongoTemplate: ReactiveMongoTemplate

    @Autowired
    lateinit var sequenceGenerator: SequenceGenerator

    @Test
    fun `findByCreateAtBetween`() {
        // given
        val year = 1990
        val board1 = makeBoard(LocalDateTime.of(year, Month.APRIL, 5, 12, 30))
        val board2 = makeBoard(LocalDateTime.of(year, Month.MAY, 5, 12, 30))

        val boards = listOf(board1, board2)

        reactiveMongoTemplate.insertAll(boards)
            .subscribe()

        val targetDate = LocalDate.of(year, Month.MAY, 1)
        val fromDate = targetDate.withDayOfMonth(1).atStartOfDay().plusDays(1)
        val toDate = targetDate.withDayOfMonth(targetDate.lengthOfMonth()).atStartOfDay().plusDays(1)

        // when & then
        boardRepository.findByCreateAtBetween(fromDate, toDate)
            .test()
            .assertNext {
                assertThat(it.createAt).isBetween(fromDate, toDate)
            }.verifyComplete()

        // teardown
        boardRepository.deleteAll(boards)
            .subscribe { println(it) }

    }

    private fun makeBoard(createAt: LocalDateTime): Board {
        return Board().apply {
            this.id = sequenceGenerator.generateSequence(Board.SEQUENCE_NAME)
            this.title = ""
            this.contents = ""
            this.author = ""
            this.createAt = createAt
        }
    }
}
