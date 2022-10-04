package me.sup2is.kotlinreactiveboard.batch.config

import me.sup2is.kotlinreactiveboard.domain.model.Board
import me.sup2is.kotlinreactiveboard.domain.model.BoardSnapshot
import me.sup2is.kotlinreactiveboard.domain.repository.BlockedBoardRepository
import me.sup2is.kotlinreactiveboard.domain.repository.BoardSnapshotRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class BoardSnapshotBatchConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val blockedBoardRepository: BlockedBoardRepository,
    val boardSnapshotRepository: BoardSnapshotRepository
) {

    @Bean
    fun boardSnapshotStep(): TaskletStep {
        return stepBuilderFactory.get("boardSnapshotStep")
            .chunk<List<Board>, List<BoardSnapshot>>(5)
            .reader {
                val currentDate = LocalDateTime.now().toLocalDate()
                val fromDate = currentDate.withDayOfMonth(1).atStartOfDay().plusDays(1)
                val toDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth()).atStartOfDay().plusDays(1)

                blockedBoardRepository.findByCreateAtBetween(fromDate, toDate)
            }.processor(boardSnapshotProcessor())
            .writer {
                it.forEach {
                    boardSnapshotRepository.saveAll(it)
                }
            }
            .build()
    }

    @Bean
    fun boardSnapshotJob(): Job {
        return jobBuilderFactory.get("boardSnapshotJob")
            .start(boardSnapshotStep())
            .build()
    }

    fun boardSnapshotProcessor(): ItemProcessor<List<Board>, List<BoardSnapshot>> {
        return ItemProcessor<List<Board>, List<BoardSnapshot>> {
            mutableListOf<BoardSnapshot>().apply {
                it.forEach {
                    this.add(BoardSnapshot(it))
                }
            }
        }
    }
}