package me.sup2is.kotlinreactiveboard.domain.repository

import me.sup2is.kotlinreactiveboard.domain.model.Board
import me.sup2is.kotlinreactiveboard.domain.service.SequenceGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

@DataMongoTest
@Import(SequenceGenerator::class)
class BlockedBoardRepositoryTest {

    @Autowired
    lateinit var blockedBoardRepository: BlockedBoardRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Autowired
    lateinit var sequenceGenerator: SequenceGenerator

    @Test
    fun `findByCreateAtBetween`() {
        // given
        val year = 1990
        val board1 = makeBoard(LocalDateTime.of(year, Month.APRIL, 5, 12, 30))
        val board2 = makeBoard(LocalDateTime.of(year, Month.MAY, 5, 12, 30))

        val boards = listOf(board1, board2)

        blockedBoardRepository.saveAll(boards)

        val targetDate = LocalDate.of(year, Month.MAY, 1)
        val fromDate = targetDate.withDayOfMonth(1).atStartOfDay().plusDays(1)
        val toDate = targetDate.withDayOfMonth(targetDate.lengthOfMonth()).atStartOfDay().plusDays(1)

        // when
        val findBoards = blockedBoardRepository.findByCreateAtBetween(fromDate, toDate)

        // then
        assertThat(findBoards.size).isEqualTo(1)

        // teardown
        blockedBoardRepository.deleteAll(boards)


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
